package ru.fylmr.diploma.crypto.primitives.prf;


import ru.fylmr.diploma.crypto.primitives.Digest;
import ru.fylmr.diploma.crypto.primitives.hmac.HMAC;
import ru.fylmr.diploma.crypto.primitives.util.ByteStrings;

public class PRF {

    private Digest digest;

    public PRF(Digest digest) {
        this.digest = digest;
    }

    public byte[] calculate(byte[] secret, String label, byte[] seed, int length) {

        HMAC hmac = new HMAC(secret, digest);

        // PRF(secret: bytes, label: string, seed: bytes) = P_HASH(secret, bytes(label) + seed);
        // P_HASH(secret, seed) = HASH(secret, A(1) + seed) + HASH(secret, A(2) + seed) + HASH(secret, A(3) + seed) + ...
        //    where A():
        //    A(0) = seed
        //    A(i) = HMAC_HASH(secret, A(i-1))
        byte[] rSeed = ByteStrings.merge(label.getBytes(), seed);
        byte[] res = new byte[length];
        byte[] A = rSeed;
        byte[] tHash = new byte[digest.getDigestSize()];
        int offset = 0;
        while (offset * 32 < length) {

            // Update A
            hmac.reset();
            hmac.update(A, 0, A.length);
            hmac.doFinal(tHash, 0);
            A = new byte[digest.getDigestSize()];
            ByteStrings.write(A, 0, tHash, 0, A.length);

            // Writing digest
            digest.reset();
            digest.update(secret, 0, secret.length);
            digest.update(A, 0, A.length);
            digest.update(rSeed, 0, rSeed.length);
            digest.doFinal(tHash, 0);

            ByteStrings.write(res, offset * digest.getDigestSize(), tHash, 0, Math.min(tHash.length, res.length - offset * digest.getDigestSize()));
            offset++;
        }
        return res;
    }
}
