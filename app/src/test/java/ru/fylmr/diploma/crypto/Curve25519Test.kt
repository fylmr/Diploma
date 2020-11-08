package ru.fylmr.diploma.crypto

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import ru.fylmr.diploma.crypto.Curve25519.calculateAgreement
import ru.fylmr.diploma.crypto.Curve25519.keyGen
import ru.fylmr.diploma.crypto.primitives.digest.SHA512
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class Curve25519Test {

    private val secureRandom: SecureRandom = SecureRandom()

    @Test
    fun pre() {
        val randomBytes = ByteArray(32)
        secureRandom.nextBytes(randomBytes)
        val aliceKey = keyGen(randomBytes)
        secureRandom.nextBytes(randomBytes)
        val bobKey = keyGen(randomBytes)

        val aliceShared = calculateAgreement(aliceKey.privateKey, bobKey.publicKey)
        val bobShared = calculateAgreement(bobKey.privateKey, aliceKey.publicKey)

        assertArrayEquals(aliceShared, bobShared)
    }

    // ===================================================
    //
    // ===================================================

    @Test
    fun ecies() {
        val alice = getAlicePair()
        val bob = getBobPair(alice.publicKey)

        val shared = calculateAgreement(alice.privateKey, bob.publicKey)

        val ar = byteArrayOf(0, 1, 2, 3)
        val c = encrypt(shared, ar)
        val d = decrypt(shared, c)

        assertArrayEquals(d, ar)
    }

    private fun encrypt(agreement: ByteArray, msg: ByteArray): EncryptionResult {
        val hash = sha512(agreement)
        val encryptionKey = hash.slice(IntRange(0, 31)).toByteArray()
        val macKey = hash.slice(IntRange(32, hash.size - 1)).toByteArray()
        val ciphertext = aes(encryptionKey, msg)
        val mac = hmac512(ciphertext, macKey)
        return EncryptionResult(
            ciphertext = ciphertext,
            mac = mac
        )
    }

    private fun decrypt(agreement: ByteArray, cipher: EncryptionResult): ByteArray? {
        val hash = sha512(agreement)
        val encryptionKey = hash.slice(IntRange(0, 31)).toByteArray()
        val macKey = hash.slice(IntRange(32, hash.size - 1)).toByteArray()
        val msg = aesDecrypt(encryptionKey, cipher.ciphertext)
        val mac = hmac512(cipher.ciphertext, macKey)

        if (!mac.contentEquals(cipher.mac)) {
            throw IllegalStateException("Неравные имитовставки")
        }

        return msg
    }

    private fun sha512(agreement: ByteArray): ByteArray {
        val sha = SHA512()
        sha.update(agreement, 0, agreement.size)
        val result = ByteArray(64)
        sha.doFinal(result, 0)
        return result
    }

    private fun aes(encryptionKey: ByteArray, msg: ByteArray): ByteArray? {
        val encoder = Base64.getEncoder()
        val encodedKey: ByteArray = encoder.encode(encryptionKey)
        val originalKey: SecretKey = SecretKeySpec(encodedKey, 0, 32, "AES")

        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, originalKey)
        return cipher.doFinal(msg)
    }

    private fun aesDecrypt(encryptionKey: ByteArray, msg: ByteArray?): ByteArray? {
        val encoder = Base64.getEncoder()
        val encodedKey: ByteArray = encoder.encode(encryptionKey)
        val originalKey: SecretKey = SecretKeySpec(encodedKey, 0, 32, "AES")

        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, originalKey)
        return cipher.doFinal(msg)
    }

    private fun hmac512(message: ByteArray?, key: ByteArray?): ByteArray? {
        return try {
            val hashingAlgorithm = "HmacSHA512"
            hmac(hashingAlgorithm, key, message)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun hmac(algorithm: String?, key: ByteArray?, message: ByteArray?): ByteArray {
        val mac: Mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(key, algorithm))
        return mac.doFinal(message)
    }

    data class EncryptionResult(
        val ciphertext: ByteArray?,
        val mac: ByteArray?,
    )

    data class KDF(
        val encryptionKey: String,
        val macKey: String,
    )

    // ===================================================
    //
    // ===================================================

    /** Функция, описывающая обмен ключами */
    private fun keyExchange() {
        // Алиса создаёт ключи
        val alicePair = getAlicePair()

        // Передаём публичный ключ Алисы Бобу,
        // он сгенерирует свой ключ, а также вычислит и сохранит общий
        val bobPair = getBobPair(alicePair.publicKey)

        // Передаём публичный ключ Боба Алисе,
        // чтобы она могла высчитать и сохранить общий
        generateAliceAgreement(alicePair, bobPair.publicKey)
    }

    /** Создание пары ключей Алисы */
    private fun getAlicePair(): Curve25519KeyPair {
        val randomBytes = ByteArray(32)
        secureRandom.nextBytes(randomBytes)

        // Пара ключей: закрытый ключ отсюда необходимо будет сохранить
        return keyGen(randomBytes)
    }

    /** Создание пары ключей Боба */
    private fun getBobPair(alicePublicKey: ByteArray): Curve25519KeyPair {
        val randomBytes = ByteArray(32)
        secureRandom.nextBytes(randomBytes)

        // Пара ключей: закрытый ключ отсюда необходимо будет сохранить.
        val bobKeyPair: Curve25519KeyPair = keyGen(randomBytes)

        // Общий ключ: его будем использовать для шифрования
        val agreement = calculateAgreement(bobKeyPair.privateKey, alicePublicKey)

        return bobKeyPair
    }

    /** Создание общего ключа Алисы */
    fun generateAliceAgreement(
        aliceKeyPair: Curve25519KeyPair,
        bobPublicKey: ByteArray,
    ): ByteArray {
        // Общий ключ: его будем использовать для шифрования
        return calculateAgreement(aliceKeyPair.privateKey, bobPublicKey)
    }
}
