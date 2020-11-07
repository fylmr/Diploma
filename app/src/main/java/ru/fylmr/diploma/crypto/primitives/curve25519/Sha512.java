package ru.fylmr.diploma.crypto.primitives.curve25519;

public interface Sha512 {

    void calculateDigest(byte[] out, byte[] in, long length);

}
