package ru.fylmr.diploma.crypto

import ru.fylmr.diploma.crypto.primitives.curve25519.Sha512
import ru.fylmr.diploma.crypto.primitives.curve25519.curve_sigs
import ru.fylmr.diploma.crypto.primitives.curve25519.scalarmult
import ru.fylmr.diploma.crypto.primitives.digest.SHA256
import ru.fylmr.diploma.crypto.primitives.digest.SHA512
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
    fun keyGenPublic(privateKey: ByteArray?): ByteArray {
        val publicKey = ByteArray(32)
        curve_sigs.curve25519_keygen(publicKey, privateKey)
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
    fun calculateAgreement(ourPrivate: ByteArray?, theirPublic: ByteArray?): ByteArray {
        val agreement = ByteArray(32)
        scalarmult.crypto_scalarmult(agreement, ourPrivate, theirPublic)
        return agreement
    }

    /**
     * Calculating signature
     *
     * @param random     random seed for signature
     * @param privateKey private key for signature
     * @param message    message to sign
     * @return signature
     */
    @JvmStatic
    fun calculateSignature(
        random: ByteArray?,
        privateKey: ByteArray?,
        message: ByteArray,
    ): ByteArray {
        val result = ByteArray(64)
        require(curve_sigs.curve25519_sign(SHA512Provider,
            result,
            privateKey,
            message,
            message.size,
            random) == 0) { "Message exceeds max length!" }
        return result
    }

    /**
     * Verification of signature
     *
     * @param publicKey public key of signature
     * @param message   message
     * @param signature signature of a message
     * @return true if signature correct
     */
    @JvmStatic
    fun verifySignature(publicKey: ByteArray?, message: ByteArray, signature: ByteArray?): Boolean {
        return curve_sigs.curve25519_verify(SHA512Provider,
            signature,
            publicKey,
            message,
            message.size) == 0
    }

    @JvmStatic
    private val SHA512Provider = Sha512 { out, `in`, length ->
        val sha512 = SHA512()
        sha512.update(`in`, 0, length.toInt())
        sha512.doFinal(out, 0)
    }
}
