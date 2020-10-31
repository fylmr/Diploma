package ru.fylmr.diploma.crypto.primitives.digest;


import ru.fylmr.diploma.crypto.primitives.Digest;

public class MD5 implements Digest {

    private MD5Digest digest = new MD5Digest();

    @Override
    public void reset() {
        digest.reset();
    }

    @Override
    public void update(byte[] src, int offset, int length) {
        digest.update(src, offset, length);
    }

    @Override
    public void doFinal(byte[] dest, int destOffset) {
        digest.doFinal(dest, destOffset);
    }

    @Override
    public int getDigestSize() {
        return 16;
    }
}
