package ru.fylmr.diploma.crypto

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import ru.fylmr.diploma.crypto.Curve25519.calculateAgreement
import ru.fylmr.diploma.crypto.Curve25519.keyGen
import java.security.SecureRandom

class Curve25519Test {

    private val secureRandom: SecureRandom = SecureRandom()

    @Test
    fun test() {
        val curve = Curve25519
    }

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
}
