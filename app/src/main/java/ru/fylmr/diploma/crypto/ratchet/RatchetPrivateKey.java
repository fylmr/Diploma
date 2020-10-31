package ru.fylmr.diploma.crypto.ratchet;

import ru.fylmr.diploma.crypto.Curve25519;

public class RatchetPrivateKey extends RatchetPublicKey {

    private final byte[] privateKey;

    public RatchetPrivateKey(byte[] privateKey) {
        super(Curve25519.keyGenPublic(privateKey));

        this.privateKey = privateKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }
}
