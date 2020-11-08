package ru.fylmr.diploma.crypto.primitives.curve25519

data class FieldElement(
    val bytes: IntArray = IntArray(10) { 0 },
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FieldElement

        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }

    // ===================================================
    // Сложение
    // ===================================================

    operator fun plus(other: FieldElement): FieldElement {
        val f0 = bytes[0]
        val f1 = bytes[1]
        val f2 = bytes[2]
        val f3 = bytes[3]
        val f4 = bytes[4]
        val f5 = bytes[5]
        val f6 = bytes[6]
        val f7 = bytes[7]
        val f8 = bytes[8]
        val f9 = bytes[9]
        val g0 = other.bytes[0]
        val g1 = other.bytes[1]
        val g2 = other.bytes[2]
        val g3 = other.bytes[3]
        val g4 = other.bytes[4]
        val g5 = other.bytes[5]
        val g6 = other.bytes[6]
        val g7 = other.bytes[7]
        val g8 = other.bytes[8]
        val g9 = other.bytes[9]

        val h0 = f0 + g0
        val h1 = f1 + g1
        val h2 = f2 + g2
        val h3 = f3 + g3
        val h4 = f4 + g4
        val h5 = f5 + g5
        val h6 = f6 + g6
        val h7 = f7 + g7
        val h8 = f8 + g8
        val h9 = f9 + g9

        val result = FieldElement()
        result.bytes[0] = h0
        result.bytes[1] = h1
        result.bytes[2] = h2
        result.bytes[3] = h3
        result.bytes[4] = h4
        result.bytes[5] = h5
        result.bytes[6] = h6
        result.bytes[7] = h7
        result.bytes[8] = h8
        result.bytes[9] = h9

        return result
    }
}

