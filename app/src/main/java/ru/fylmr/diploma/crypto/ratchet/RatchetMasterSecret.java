package ru.fylmr.diploma.crypto.ratchet;

import ru.fylmr.diploma.crypto.Curve25519;
import ru.fylmr.diploma.crypto.primitives.digest.SHA256;
import ru.fylmr.diploma.crypto.primitives.util.ByteStrings;

public class RatchetMasterSecret {
    public static byte[] calculateMasterSecret(
            RatchetPrivateKey ownIdentity,
            RatchetPrivateKey ownEphermal,
            RatchetPublicKey foreignIdentity,
            RatchetPublicKey foreignEphermal) {

        byte[] ecResult;
        if (ownIdentity.isBigger(foreignIdentity.getKey())) {
            ecResult =
                    ByteStrings.merge(
                            Curve25519.calculateAgreement(
                                    ownIdentity.getPrivateKey(),
                                    foreignEphermal.getKey()),
                            Curve25519.calculateAgreement(
                                    ownEphermal.getPrivateKey(),
                                    foreignIdentity.getKey()),
                            Curve25519.calculateAgreement(
                                    ownEphermal.getPrivateKey(),
                                    foreignEphermal.getKey())
                    );
        } else {
            ecResult =
                    ByteStrings.merge(
                            Curve25519.calculateAgreement(
                                    ownEphermal.getPrivateKey(),
                                    foreignIdentity.getKey()),
                            Curve25519.calculateAgreement(
                                    ownIdentity.getPrivateKey(),
                                    foreignEphermal.getKey()),
                            Curve25519.calculateAgreement(
                                    ownEphermal.getPrivateKey(),
                                    foreignEphermal.getKey())
                    );
        }
        SHA256 sha256 = new SHA256();
        sha256.update(ecResult, 0, ecResult.length);
        byte[] res = new byte[32];
        sha256.doFinal(res, 0);
        return res;
    }
}
