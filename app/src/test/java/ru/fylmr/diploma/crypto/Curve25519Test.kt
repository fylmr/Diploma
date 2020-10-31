package ru.fylmr.diploma.crypto

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import ru.fylmr.diploma.crypto.Curve25519.calculateAgreement
import ru.fylmr.diploma.crypto.Curve25519.keyGen
import java.security.SecureRandom

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
    fun generateAliceAgreement(aliceKeyPair: Curve25519KeyPair, bobPublicKey: ByteArray) {
        // Общий ключ: его будем использовать для шифрования
        val agreement = calculateAgreement(aliceKeyPair.privateKey, bobPublicKey)
    }
}
