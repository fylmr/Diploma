package ru.fylmr.diploma.crypto

import ru.fylmr.diploma.crypto.primitives.curve25519.curve25519Keygen
import ru.fylmr.diploma.crypto.primitives.curve25519.scalarMultiplication
import ru.fylmr.diploma.crypto.primitives.digest.SHA256
import kotlin.experimental.and
import kotlin.experimental.or

object Curve25519 {
    /**
     * Generating KeyPair
     *
     * @param randomBytes 32 random bytes
     * @return generated key pair
     */
    @JvmStatic
    fun keyGen(randomBytes: ByteArray): Curve25519KeyPair {
        val privateKey = keyGenPrivate(randomBytes)
        val publicKey = keyGenPublic(privateKey)
        return Curve25519KeyPair(publicKey, privateKey)
    }

    /**
     * Generating private key. Source: https://cr.yp.to/ecdh.html
     *
     * @param randomBytes random bytes (32+ bytes)
     * @return generated private key
     */
    @JvmStatic
    fun keyGenPrivate(randomBytes: ByteArray): ByteArray {
        if (randomBytes.size < 32) {
            throw RuntimeException("Random bytes too small")
        }

        // Hashing Random Bytes instead of using random bytes directly
        // Just in case as reference ed255519 implementation do same
        val privateKey = ByteArray(32)
        val sha256 = SHA256()
        sha256.update(randomBytes, 0, randomBytes.size)
        sha256.doFinal(privateKey, 0)

        // Performing bit's flipping
        privateKey[0] = privateKey[0] and 248.toByte()
        privateKey[31] = privateKey[31] and 127.toByte()
        privateKey[31] = privateKey[31] or 64.toByte()
        return privateKey
    }

    /**
     * Building public key with private key
     *
     * @param privateKey private key
     * @return generated public key
     */
    @JvmStatic
    fun keyGenPublic(privateKey: ByteArray): ByteArray {
        val publicKey = ByteArray(32)
        curve25519Keygen(publicKey, privateKey)
        return publicKey
    }

    /**
     * Calculating DH agreement
     *
     * @param ourPrivate  Our Private Key
     * @param theirPublic Theirs Public key
     * @return calculated agreement
     */
    @JvmStatic
    fun calculateAgreement(ourPrivate: ByteArray, theirPublic: ByteArray): ByteArray {
        val agreement = ByteArray(32)
        scalarMultiplication(agreement, ourPrivate, theirPublic)
        return agreement
    }
}
