package ru.fylmr.diploma.crypto.primitives.curve25519

data class FieldElement(
    var bytes: IntArray = IntArray(10) { 0 },
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

    /**
     * fe_add
     */
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

    // ===================================================
    // Вычитание
    // ===================================================

    /** fe_sub */
    operator fun minus(other: FieldElement): FieldElement {
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
        val h0 = f0 - g0
        val h1 = f1 - g1
        val h2 = f2 - g2
        val h3 = f3 - g3
        val h4 = f4 - g4
        val h5 = f5 - g5
        val h6 = f6 - g6
        val h7 = f7 - g7
        val h8 = f8 - g8
        val h9 = f9 - g9

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

    // ===================================================
    // Умножение
    // ===================================================

    /** fe_mul */
    operator fun times(other: FieldElement): FieldElement {
        val hr = getHr(bytes, other.bytes)
        var carry0: Long
        val carry1: Long
        val carry2: Long
        val carry3: Long
        var carry4: Long
        val carry5: Long
        val carry6: Long
        val carry7: Long
        val carry8: Long
        val carry9: Long

        carry0 = hr[0] + (1 shl 25).toLong() shr 26
        hr[1] += carry0
        hr[0] -= carry0 shl 26
        carry4 = hr[4] + (1 shl 25).toLong() shr 26
        hr[5] += carry4
        hr[4] -= carry4 shl 26
        carry1 = hr[1] + (1 shl 24).toLong() shr 25
        hr[2] += carry1
        hr[1] -= carry1 shl 25
        carry5 = hr[5] + (1 shl 24).toLong() shr 25
        hr[6] += carry5
        hr[5] -= carry5 shl 25
        carry2 = hr[2] + (1 shl 25).toLong() shr 26
        hr[3] += carry2
        hr[2] -= carry2 shl 26
        carry6 = hr[6] + (1 shl 25).toLong() shr 26
        hr[7] += carry6
        hr[6] -= carry6 shl 26
        carry3 = hr[3] + (1 shl 24).toLong() shr 25
        hr[4] += carry3
        hr[3] -= carry3 shl 25
        carry7 = hr[7] + (1 shl 24).toLong() shr 25
        hr[8] += carry7
        hr[7] -= carry7 shl 25
        carry4 = hr[4] + (1 shl 25).toLong() shr 26
        hr[5] += carry4
        hr[4] -= carry4 shl 26
        carry8 = hr[8] + (1 shl 25).toLong() shr 26
        hr[9] += carry8
        hr[8] -= carry8 shl 26
        carry9 = hr[9] + (1 shl 24).toLong() shr 25
        hr[0] += carry9 * 19
        hr[9] -= carry9 shl 25
        carry0 = hr[0] + (1 shl 25).toLong() shr 26
        hr[1] += carry0
        hr[0] -= carry0 shl 26

        /* |h0| <= 2^25; from now on fits into int32 unchanged */
        /* |h1| <= 1.01*2^24 */
        val h = FieldElement()
        h.bytes[0] = hr[0].toInt()
        h.bytes[1] = hr[1].toInt()
        h.bytes[2] = hr[2].toInt()
        h.bytes[3] = hr[3].toInt()
        h.bytes[4] = hr[4].toInt()
        h.bytes[5] = hr[5].toInt()
        h.bytes[6] = hr[6].toInt()
        h.bytes[7] = hr[7].toInt()
        h.bytes[8] = hr[8].toInt()
        h.bytes[9] = hr[9].toInt()

        return h
    }

    private fun getHr(f: IntArray, g: IntArray): LongArray {
        val f0 = f[0]
        val f1 = f[1]
        val f2 = f[2]
        val f3 = f[3]
        val f4 = f[4]
        val f5 = f[5]
        val f6 = f[6]
        val f7 = f[7]
        val f8 = f[8]
        val f9 = f[9]
        val g0 = g[0]
        val g1 = g[1]
        val g2 = g[2]
        val g3 = g[3]
        val g4 = g[4]
        val g5 = g[5]
        val g6 = g[6]
        val g7 = g[7]
        val g8 = g[8]
        val g9 = g[9]
        val g1_19 = 19 * g1 /* 1.959375*2^29 */
        val g2_19 = 19 * g2 /* 1.959375*2^30; still ok */
        val g3_19 = 19 * g3
        val g4_19 = 19 * g4
        val g5_19 = 19 * g5
        val g6_19 = 19 * g6
        val g7_19 = 19 * g7
        val g8_19 = 19 * g8
        val g9_19 = 19 * g9
        val f1_2 = 2 * f1
        val f3_2 = 2 * f3
        val f5_2 = 2 * f5
        val f7_2 = 2 * f7
        val f9_2 = 2 * f9
        val f0g0 = f0 * g0.toLong()
        val f0g1 = f0 * g1.toLong()
        val f0g2 = f0 * g2.toLong()
        val f0g3 = f0 * g3.toLong()
        val f0g4 = f0 * g4.toLong()
        val f0g5 = f0 * g5.toLong()
        val f0g6 = f0 * g6.toLong()
        val f0g7 = f0 * g7.toLong()
        val f0g8 = f0 * g8.toLong()
        val f0g9 = f0 * g9.toLong()
        val f1g0 = f1 * g0.toLong()
        val f1g1_2 = f1_2 * g1.toLong()
        val f1g2 = f1 * g2.toLong()
        val f1g3_2 = f1_2 * g3.toLong()
        val f1g4 = f1 * g4.toLong()
        val f1g5_2 = f1_2 * g5.toLong()
        val f1g6 = f1 * g6.toLong()
        val f1g7_2 = f1_2 * g7.toLong()
        val f1g8 = f1 * g8.toLong()
        val f1g9_38 = f1_2 * g9_19.toLong()
        val f2g0 = f2 * g0.toLong()
        val f2g1 = f2 * g1.toLong()
        val f2g2 = f2 * g2.toLong()
        val f2g3 = f2 * g3.toLong()
        val f2g4 = f2 * g4.toLong()
        val f2g5 = f2 * g5.toLong()
        val f2g6 = f2 * g6.toLong()
        val f2g7 = f2 * g7.toLong()
        val f2g8_19 = f2 * g8_19.toLong()
        val f2g9_19 = f2 * g9_19.toLong()
        val f3g0 = f3 * g0.toLong()
        val f3g1_2 = f3_2 * g1.toLong()
        val f3g2 = f3 * g2.toLong()
        val f3g3_2 = f3_2 * g3.toLong()
        val f3g4 = f3 * g4.toLong()
        val f3g5_2 = f3_2 * g5.toLong()
        val f3g6 = f3 * g6.toLong()
        val f3g7_38 = f3_2 * g7_19.toLong()
        val f3g8_19 = f3 * g8_19.toLong()
        val f3g9_38 = f3_2 * g9_19.toLong()
        val f4g0 = f4 * g0.toLong()
        val f4g1 = f4 * g1.toLong()
        val f4g2 = f4 * g2.toLong()
        val f4g3 = f4 * g3.toLong()
        val f4g4 = f4 * g4.toLong()
        val f4g5 = f4 * g5.toLong()
        val f4g6_19 = f4 * g6_19.toLong()
        val f4g7_19 = f4 * g7_19.toLong()
        val f4g8_19 = f4 * g8_19.toLong()
        val f4g9_19 = f4 * g9_19.toLong()
        val f5g0 = f5 * g0.toLong()
        val f5g1_2 = f5_2 * g1.toLong()
        val f5g2 = f5 * g2.toLong()
        val f5g3_2 = f5_2 * g3.toLong()
        val f5g4 = f5 * g4.toLong()
        val f5g5_38 = f5_2 * g5_19.toLong()
        val f5g6_19 = f5 * g6_19.toLong()
        val f5g7_38 = f5_2 * g7_19.toLong()
        val f5g8_19 = f5 * g8_19.toLong()
        val f5g9_38 = f5_2 * g9_19.toLong()
        val f6g0 = f6 * g0.toLong()
        val f6g1 = f6 * g1.toLong()
        val f6g2 = f6 * g2.toLong()
        val f6g3 = f6 * g3.toLong()
        val f6g4_19 = f6 * g4_19.toLong()
        val f6g5_19 = f6 * g5_19.toLong()
        val f6g6_19 = f6 * g6_19.toLong()
        val f6g7_19 = f6 * g7_19.toLong()
        val f6g8_19 = f6 * g8_19.toLong()
        val f6g9_19 = f6 * g9_19.toLong()
        val f7g0 = f7 * g0.toLong()
        val f7g1_2 = f7_2 * g1.toLong()
        val f7g2 = f7 * g2.toLong()
        val f7g3_38 = f7_2 * g3_19.toLong()
        val f7g4_19 = f7 * g4_19.toLong()
        val f7g5_38 = f7_2 * g5_19.toLong()
        val f7g6_19 = f7 * g6_19.toLong()
        val f7g7_38 = f7_2 * g7_19.toLong()
        val f7g8_19 = f7 * g8_19.toLong()
        val f7g9_38 = f7_2 * g9_19.toLong()
        val f8g0 = f8 * g0.toLong()
        val f8g1 = f8 * g1.toLong()
        val f8g2_19 = f8 * g2_19.toLong()
        val f8g3_19 = f8 * g3_19.toLong()
        val f8g4_19 = f8 * g4_19.toLong()
        val f8g5_19 = f8 * g5_19.toLong()
        val f8g6_19 = f8 * g6_19.toLong()
        val f8g7_19 = f8 * g7_19.toLong()
        val f8g8_19 = f8 * g8_19.toLong()
        val f8g9_19 = f8 * g9_19.toLong()
        val f9g0 = f9 * g0.toLong()
        val f9g1_38 = f9_2 * g1_19.toLong()
        val f9g2_19 = f9 * g2_19.toLong()
        val f9g3_38 = f9_2 * g3_19.toLong()
        val f9g4_19 = f9 * g4_19.toLong()
        val f9g5_38 = f9_2 * g5_19.toLong()
        val f9g6_19 = f9 * g6_19.toLong()
        val f9g7_38 = f9_2 * g7_19.toLong()
        val f9g8_19 = f9 * g8_19.toLong()
        val f9g9_38 = f9_2 * g9_19.toLong()
        val h = LongArray(10)
        h[0] =
            f0g0 + f1g9_38 + f2g8_19 + f3g7_38 + f4g6_19 + f5g5_38 + f6g4_19 + f7g3_38 + f8g2_19 + f9g1_38
        h[1] =
            f0g1 + f1g0 + f2g9_19 + f3g8_19 + f4g7_19 + f5g6_19 + f6g5_19 + f7g4_19 + f8g3_19 + f9g2_19
        h[2] =
            f0g2 + f1g1_2 + f2g0 + f3g9_38 + f4g8_19 + f5g7_38 + f6g6_19 + f7g5_38 + f8g4_19 + f9g3_38
        h[3] =
            f0g3 + f1g2 + f2g1 + f3g0 + f4g9_19 + f5g8_19 + f6g7_19 + f7g6_19 + f8g5_19 + f9g4_19
        h[4] =
            f0g4 + f1g3_2 + f2g2 + f3g1_2 + f4g0 + f5g9_38 + f6g8_19 + f7g7_38 + f8g6_19 + f9g5_38
        h[5] = f0g5 + f1g4 + f2g3 + f3g2 + f4g1 + f5g0 + f6g9_19 + f7g8_19 + f8g7_19 + f9g6_19
        h[6] =
            f0g6 + f1g5_2 + f2g4 + f3g3_2 + f4g2 + f5g1_2 + f6g0 + f7g9_38 + f8g8_19 + f9g7_38
        h[7] = f0g7 + f1g6 + f2g5 + f3g4 + f4g3 + f5g2 + f6g1 + f7g0 + f8g9_19 + f9g8_19
        h[8] = f0g8 + f1g7_2 + f2g6 + f3g5_2 + f4g4 + f5g3_2 + f6g2 + f7g1_2 + f8g0 + f9g9_38
        h[9] = f0g9 + f1g8 + f2g7 + f3g6 + f4g5 + f5g4 + f6g3 + f7g2 + f8g1 + f9g0
        return h
    }

    // ===================================================
    // Квадрат
    // ===================================================

    /** fe_sq */
    @Suppress("LocalVariableName")
    fun getSquare(): FieldElement {
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
        val f0_2 = 2 * f0
        val f1_2 = 2 * f1
        val f2_2 = 2 * f2
        val f3_2 = 2 * f3
        val f4_2 = 2 * f4
        val f5_2 = 2 * f5
        val f6_2 = 2 * f6
        val f7_2 = 2 * f7
        val f5_38 = 38 * f5
        val f6_19 = 19 * f6
        val f7_38 = 38 * f7
        val f8_19 = 19 * f8
        val f9_38 = 38 * f9
        val f0f0 = f0 * f0.toLong()
        val f0f1_2 = f0_2 * f1.toLong()
        val f0f2_2 = f0_2 * f2.toLong()
        val f0f3_2 = f0_2 * f3.toLong()
        val f0f4_2 = f0_2 * f4.toLong()
        val f0f5_2 = f0_2 * f5.toLong()
        val f0f6_2 = f0_2 * f6.toLong()
        val f0f7_2 = f0_2 * f7.toLong()
        val f0f8_2 = f0_2 * f8.toLong()
        val f0f9_2 = f0_2 * f9.toLong()
        val f1f1_2 = f1_2 * f1.toLong()
        val f1f2_2 = f1_2 * f2.toLong()
        val f1f3_4 = f1_2 * f3_2.toLong()
        val f1f4_2 = f1_2 * f4.toLong()
        val f1f5_4 = f1_2 * f5_2.toLong()
        val f1f6_2 = f1_2 * f6.toLong()
        val f1f7_4 = f1_2 * f7_2.toLong()
        val f1f8_2 = f1_2 * f8.toLong()
        val f1f9_76 = f1_2 * f9_38.toLong()
        val f2f2 = f2 * f2.toLong()
        val f2f3_2 = f2_2 * f3.toLong()
        val f2f4_2 = f2_2 * f4.toLong()
        val f2f5_2 = f2_2 * f5.toLong()
        val f2f6_2 = f2_2 * f6.toLong()
        val f2f7_2 = f2_2 * f7.toLong()
        val f2f8_38 = f2_2 * f8_19.toLong()
        val f2f9_38 = f2 * f9_38.toLong()
        val f3f3_2 = f3_2 * f3.toLong()
        val f3f4_2 = f3_2 * f4.toLong()
        val f3f5_4 = f3_2 * f5_2.toLong()
        val f3f6_2 = f3_2 * f6.toLong()
        val f3f7_76 = f3_2 * f7_38.toLong()
        val f3f8_38 = f3_2 * f8_19.toLong()
        val f3f9_76 = f3_2 * f9_38.toLong()
        val f4f4 = f4 * f4.toLong()
        val f4f5_2 = f4_2 * f5.toLong()
        val f4f6_38 = f4_2 * f6_19.toLong()
        val f4f7_38 = f4 * f7_38.toLong()
        val f4f8_38 = f4_2 * f8_19.toLong()
        val f4f9_38 = f4 * f9_38.toLong()
        val f5f5_38 = f5 * f5_38.toLong()
        val f5f6_38 = f5_2 * f6_19.toLong()
        val f5f7_76 = f5_2 * f7_38.toLong()
        val f5f8_38 = f5_2 * f8_19.toLong()
        val f5f9_76 = f5_2 * f9_38.toLong()
        val f6f6_19 = f6 * f6_19.toLong()
        val f6f7_38 = f6 * f7_38.toLong()
        val f6f8_38 = f6_2 * f8_19.toLong()
        val f6f9_38 = f6 * f9_38.toLong()
        val f7f7_38 = f7 * f7_38.toLong()
        val f7f8_38 = f7_2 * f8_19.toLong()
        val f7f9_76 = f7_2 * f9_38.toLong()
        val f8f8_19 = f8 * f8_19.toLong()
        val f8f9_38 = f8 * f9_38.toLong()
        val f9f9_38 = f9 * f9_38.toLong()
        var h0 = f0f0 + f1f9_76 + f2f8_38 + f3f7_76 + f4f6_38 + f5f5_38
        var h1 = f0f1_2 + f2f9_38 + f3f8_38 + f4f7_38 + f5f6_38
        var h2 = f0f2_2 + f1f1_2 + f3f9_76 + f4f8_38 + f5f7_76 + f6f6_19
        var h3 = f0f3_2 + f1f2_2 + f4f9_38 + f5f8_38 + f6f7_38
        var h4 = f0f4_2 + f1f3_4 + f2f2 + f5f9_76 + f6f8_38 + f7f7_38
        var h5 = f0f5_2 + f1f4_2 + f2f3_2 + f6f9_38 + f7f8_38
        var h6 = f0f6_2 + f1f5_4 + f2f4_2 + f3f3_2 + f7f9_76 + f8f8_19
        var h7 = f0f7_2 + f1f6_2 + f2f5_2 + f3f4_2 + f8f9_38
        var h8 = f0f8_2 + f1f7_4 + f2f6_2 + f3f5_4 + f4f4 + f9f9_38
        var h9 = f0f9_2 + f1f8_2 + f2f7_2 + f3f6_2 + f4f5_2
        var carry0: Long
        val carry1: Long
        val carry2: Long
        val carry3: Long
        var carry4: Long
        val carry5: Long
        val carry6: Long
        val carry7: Long
        val carry8: Long
        val carry9: Long
        carry0 = h0 + (1 shl 25).toLong() shr 26
        h1 += carry0
        h0 -= carry0 shl 26
        carry4 = h4 + (1 shl 25).toLong() shr 26
        h5 += carry4
        h4 -= carry4 shl 26
        carry1 = h1 + (1 shl 24).toLong() shr 25
        h2 += carry1
        h1 -= carry1 shl 25
        carry5 = h5 + (1 shl 24).toLong() shr 25
        h6 += carry5
        h5 -= carry5 shl 25
        carry2 = h2 + (1 shl 25).toLong() shr 26
        h3 += carry2
        h2 -= carry2 shl 26
        carry6 = h6 + (1 shl 25).toLong() shr 26
        h7 += carry6
        h6 -= carry6 shl 26
        carry3 = h3 + (1 shl 24).toLong() shr 25
        h4 += carry3
        h3 -= carry3 shl 25
        carry7 = h7 + (1 shl 24).toLong() shr 25
        h8 += carry7
        h7 -= carry7 shl 25
        carry4 = h4 + (1 shl 25).toLong() shr 26
        h5 += carry4
        h4 -= carry4 shl 26
        carry8 = h8 + (1 shl 25).toLong() shr 26
        h9 += carry8
        h8 -= carry8 shl 26
        carry9 = h9 + (1 shl 24).toLong() shr 25
        h0 += carry9 * 19
        h9 -= carry9 shl 25
        carry0 = h0 + (1 shl 25).toLong() shr 26
        h1 += carry0
        h0 -= carry0 shl 26

        val result = FieldElement()
        result.bytes[0] = h0.toInt()
        result.bytes[1] = h1.toInt()
        result.bytes[2] = h2.toInt()
        result.bytes[3] = h3.toInt()
        result.bytes[4] = h4.toInt()
        result.bytes[5] = h5.toInt()
        result.bytes[6] = h6.toInt()
        result.bytes[7] = h7.toInt()
        result.bytes[8] = h8.toInt()
        result.bytes[9] = h9.toInt()

        return result
    }

    /** fe_sq2 */
    @Suppress("LocalVariableName")
    fun getDoubledSquare(): FieldElement {
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
        val f0_2 = 2 * f0
        val f1_2 = 2 * f1
        val f2_2 = 2 * f2
        val f3_2 = 2 * f3
        val f4_2 = 2 * f4
        val f5_2 = 2 * f5
        val f6_2 = 2 * f6
        val f7_2 = 2 * f7
        val f5_38 = 38 * f5 /* 1.959375*2^30 */
        val f6_19 = 19 * f6 /* 1.959375*2^30 */
        val f7_38 = 38 * f7 /* 1.959375*2^30 */
        val f8_19 = 19 * f8 /* 1.959375*2^30 */
        val f9_38 = 38 * f9 /* 1.959375*2^30 */
        val f0f0 = f0 * f0.toLong()
        val f0f1_2 = f0_2 * f1.toLong()
        val f0f2_2 = f0_2 * f2.toLong()
        val f0f3_2 = f0_2 * f3.toLong()
        val f0f4_2 = f0_2 * f4.toLong()
        val f0f5_2 = f0_2 * f5.toLong()
        val f0f6_2 = f0_2 * f6.toLong()
        val f0f7_2 = f0_2 * f7.toLong()
        val f0f8_2 = f0_2 * f8.toLong()
        val f0f9_2 = f0_2 * f9.toLong()
        val f1f1_2 = f1_2 * f1.toLong()
        val f1f2_2 = f1_2 * f2.toLong()
        val f1f3_4 = f1_2 * f3_2.toLong()
        val f1f4_2 = f1_2 * f4.toLong()
        val f1f5_4 = f1_2 * f5_2.toLong()
        val f1f6_2 = f1_2 * f6.toLong()
        val f1f7_4 = f1_2 * f7_2.toLong()
        val f1f8_2 = f1_2 * f8.toLong()
        val f1f9_76 = f1_2 * f9_38.toLong()
        val f2f2 = f2 * f2.toLong()
        val f2f3_2 = f2_2 * f3.toLong()
        val f2f4_2 = f2_2 * f4.toLong()
        val f2f5_2 = f2_2 * f5.toLong()
        val f2f6_2 = f2_2 * f6.toLong()
        val f2f7_2 = f2_2 * f7.toLong()
        val f2f8_38 = f2_2 * f8_19.toLong()
        val f2f9_38 = f2 * f9_38.toLong()
        val f3f3_2 = f3_2 * f3.toLong()
        val f3f4_2 = f3_2 * f4.toLong()
        val f3f5_4 = f3_2 * f5_2.toLong()
        val f3f6_2 = f3_2 * f6.toLong()
        val f3f7_76 = f3_2 * f7_38.toLong()
        val f3f8_38 = f3_2 * f8_19.toLong()
        val f3f9_76 = f3_2 * f9_38.toLong()
        val f4f4 = f4 * f4.toLong()
        val f4f5_2 = f4_2 * f5.toLong()
        val f4f6_38 = f4_2 * f6_19.toLong()
        val f4f7_38 = f4 * f7_38.toLong()
        val f4f8_38 = f4_2 * f8_19.toLong()
        val f4f9_38 = f4 * f9_38.toLong()
        val f5f5_38 = f5 * f5_38.toLong()
        val f5f6_38 = f5_2 * f6_19.toLong()
        val f5f7_76 = f5_2 * f7_38.toLong()
        val f5f8_38 = f5_2 * f8_19.toLong()
        val f5f9_76 = f5_2 * f9_38.toLong()
        val f6f6_19 = f6 * f6_19.toLong()
        val f6f7_38 = f6 * f7_38.toLong()
        val f6f8_38 = f6_2 * f8_19.toLong()
        val f6f9_38 = f6 * f9_38.toLong()
        val f7f7_38 = f7 * f7_38.toLong()
        val f7f8_38 = f7_2 * f8_19.toLong()
        val f7f9_76 = f7_2 * f9_38.toLong()
        val f8f8_19 = f8 * f8_19.toLong()
        val f8f9_38 = f8 * f9_38.toLong()
        val f9f9_38 = f9 * f9_38.toLong()
        var h0 = f0f0 + f1f9_76 + f2f8_38 + f3f7_76 + f4f6_38 + f5f5_38
        var h1 = f0f1_2 + f2f9_38 + f3f8_38 + f4f7_38 + f5f6_38
        var h2 = f0f2_2 + f1f1_2 + f3f9_76 + f4f8_38 + f5f7_76 + f6f6_19
        var h3 = f0f3_2 + f1f2_2 + f4f9_38 + f5f8_38 + f6f7_38
        var h4 = f0f4_2 + f1f3_4 + f2f2 + f5f9_76 + f6f8_38 + f7f7_38
        var h5 = f0f5_2 + f1f4_2 + f2f3_2 + f6f9_38 + f7f8_38
        var h6 = f0f6_2 + f1f5_4 + f2f4_2 + f3f3_2 + f7f9_76 + f8f8_19
        var h7 = f0f7_2 + f1f6_2 + f2f5_2 + f3f4_2 + f8f9_38
        var h8 = f0f8_2 + f1f7_4 + f2f6_2 + f3f5_4 + f4f4 + f9f9_38
        var h9 = f0f9_2 + f1f8_2 + f2f7_2 + f3f6_2 + f4f5_2
        var carry0: Long
        val carry1: Long
        val carry2: Long
        val carry3: Long
        var carry4: Long
        val carry5: Long
        val carry6: Long
        val carry7: Long
        val carry8: Long
        val carry9: Long
        h0 += h0
        h1 += h1
        h2 += h2
        h3 += h3
        h4 += h4
        h5 += h5
        h6 += h6
        h7 += h7
        h8 += h8
        h9 += h9
        carry0 = h0 + (1 shl 25).toLong() shr 26
        h1 += carry0
        h0 -= carry0 shl 26
        carry4 = h4 + (1 shl 25).toLong() shr 26
        h5 += carry4
        h4 -= carry4 shl 26
        carry1 = h1 + (1 shl 24).toLong() shr 25
        h2 += carry1
        h1 -= carry1 shl 25
        carry5 = h5 + (1 shl 24).toLong() shr 25
        h6 += carry5
        h5 -= carry5 shl 25
        carry2 = h2 + (1 shl 25).toLong() shr 26
        h3 += carry2
        h2 -= carry2 shl 26
        carry6 = h6 + (1 shl 25).toLong() shr 26
        h7 += carry6
        h6 -= carry6 shl 26
        carry3 = h3 + (1 shl 24).toLong() shr 25
        h4 += carry3
        h3 -= carry3 shl 25
        carry7 = h7 + (1 shl 24).toLong() shr 25
        h8 += carry7
        h7 -= carry7 shl 25
        carry4 = h4 + (1 shl 25).toLong() shr 26
        h5 += carry4
        h4 -= carry4 shl 26
        carry8 = h8 + (1 shl 25).toLong() shr 26
        h9 += carry8
        h8 -= carry8 shl 26
        carry9 = h9 + (1 shl 24).toLong() shr 25
        h0 += carry9 * 19
        h9 -= carry9 shl 25
        carry0 = h0 + (1 shl 25).toLong() shr 26
        h1 += carry0
        h0 -= carry0 shl 26

        val result = FieldElement()
        result.bytes[0] = h0.toInt()
        result.bytes[1] = h1.toInt()
        result.bytes[2] = h2.toInt()
        result.bytes[3] = h3.toInt()
        result.bytes[4] = h4.toInt()
        result.bytes[5] = h5.toInt()
        result.bytes[6] = h6.toInt()
        result.bytes[7] = h7.toInt()
        result.bytes[8] = h8.toInt()
        result.bytes[9] = h9.toInt()

        return result
    }

    // ===================================================
    // Инверсия
    // ===================================================

    /** fe_invert */
    fun getInverted(): FieldElement {

        /* qhasm: z2 = z1^2^1 */
        /* asm 1: fe_sq.fe_sq(>z2=fe#1,<z1=fe#11); for (i = 1;i < 1;++i) fe_sq.fe_sq(>z2=fe#1,>z2=fe#1); */
        /* asm 2: fe_sq.fe_sq(>z2=t0,<z1=z); for (i = 1;i < 1;++i) fe_sq.fe_sq(>z2=t0,>z2=t0); */
        var t0 = getSquare()

        /* qhasm: z8 = z2^2^2 */
        /* asm 1: fe_sq.fe_sq(>z8=fe#2,<z2=fe#1); for (i = 1;i < 2;++i) fe_sq.fe_sq(>z8=fe#2,>z8=fe#2); */
        /* asm 2: fe_sq.fe_sq(>z8=t1,<z2=t0); for (i = 1;i < 2;++i) fe_sq.fe_sq(>z8=t1,>z8=t1); */
        var t1 = t0.getSquare()
        var i = 1
        while (i < 2) {
            t1 = t1.getSquare()
            ++i
        }

        /* qhasm: z9 = z1*z8 */
        /* asm 1: fe_mul(>z9=fe#2,<z1=fe#11,<z8=fe#2); */
        /* asm 2: fe_mul(>z9=t1,<z1=z,<z8=t1); */
        t1 = this * t1

        /* qhasm: z11 = z2*z9 */
        /* asm 1: fe_mul(>z11=fe#1,<z2=fe#1,<z9=fe#2); */
        /* asm 2: fe_mul(>z11=t0,<z2=t0,<z9=t1); */
        t0 *= t1

        /* qhasm: z22 = z11^2^1 */
        /* asm 1: fe_sq.fe_sq(>z22=fe#3,<z11=fe#1); for (i = 1;i < 1;++i) fe_sq.fe_sq(>z22=fe#3,>z22=fe#3); */
        /* asm 2: fe_sq.fe_sq(>z22=t2,<z11=t0); for (i = 1;i < 1;++i) fe_sq.fe_sq(>z22=t2,>z22=t2); */
        var t2 = t0.getSquare()

        /* qhasm: z_5_0 = z9*z22 */
        /* asm 1: fe_mul(>z_5_0=fe#2,<z9=fe#2,<z22=fe#3); */
        /* asm 2: fe_mul(>z_5_0=t1,<z9=t1,<z22=t2); */
        t1 *= t2

        /* qhasm: z_10_5 = z_5_0^2^5 */
        /* asm 1: fe_sq.fe_sq(>z_10_5=fe#3,<z_5_0=fe#2); for (i = 1;i < 5;++i) fe_sq.fe_sq(>z_10_5=fe#3,>z_10_5=fe#3); */
        /* asm 2: fe_sq.fe_sq(>z_10_5=t2,<z_5_0=t1); for (i = 1;i < 5;++i) fe_sq.fe_sq(>z_10_5=t2,>z_10_5=t2); */
        t2 = t1.getSquare()
        i = 1
        while (i < 5) {
            t2 = t2.getSquare()
            ++i
        }

        /* qhasm: z_10_0 = z_10_5*z_5_0 */
        /* asm 1: fe_mul(>z_10_0=fe#2,<z_10_5=fe#3,<z_5_0=fe#2); */
        /* asm 2: fe_mul(>z_10_0=t1,<z_10_5=t2,<z_5_0=t1); */
        t1 = t2 * t1

        /* qhasm: z_20_10 = z_10_0^2^10 */
        /* asm 1: fe_sq.fe_sq(>z_20_10=fe#3,<z_10_0=fe#2); for (i = 1;i < 10;++i) fe_sq.fe_sq(>z_20_10=fe#3,>z_20_10=fe#3); */
        /* asm 2: fe_sq.fe_sq(>z_20_10=t2,<z_10_0=t1); for (i = 1;i < 10;++i) fe_sq.fe_sq(>z_20_10=t2,>z_20_10=t2); */
        t2 = t1.getSquare()
        i = 1
        while (i < 10) {
            t2 = t2.getSquare()
            ++i
        }

        /* qhasm: z_20_0 = z_20_10*z_10_0 */
        /* asm 1: fe_mul(>z_20_0=fe#3,<z_20_10=fe#3,<z_10_0=fe#2); */
        /* asm 2: fe_mul(>z_20_0=t2,<z_20_10=t2,<z_10_0=t1); */
        t2 *= t1

        /* qhasm: z_40_20 = z_20_0^2^20 */
        /* asm 1: fe_sq.fe_sq(>z_40_20=fe#4,<z_20_0=fe#3); for (i = 1;i < 20;++i) fe_sq.fe_sq(>z_40_20=fe#4,>z_40_20=fe#4); */
        /* asm 2: fe_sq.fe_sq(>z_40_20=t3,<z_20_0=t2); for (i = 1;i < 20;++i) fe_sq.fe_sq(>z_40_20=t3,>z_40_20=t3); */
        var t3 = t2.getSquare()
        i = 1
        while (i < 20) {
            t3 = t3.getSquare()
            ++i
        }

        /* qhasm: z_40_0 = z_40_20*z_20_0 */
        /* asm 1: fe_mul(>z_40_0=fe#3,<z_40_20=fe#4,<z_20_0=fe#3); */
        /* asm 2: fe_mul(>z_40_0=t2,<z_40_20=t3,<z_20_0=t2); */
        t2 = t3 * t2

        /* qhasm: z_50_10 = z_40_0^2^10 */
        /* asm 1: fe_sq.fe_sq(>z_50_10=fe#3,<z_40_0=fe#3); for (i = 1;i < 10;++i) fe_sq.fe_sq(>z_50_10=fe#3,>z_50_10=fe#3); */
        /* asm 2: fe_sq.fe_sq(>z_50_10=t2,<z_40_0=t2); for (i = 1;i < 10;++i) fe_sq.fe_sq(>z_50_10=t2,>z_50_10=t2); */
        t2 = t2.getSquare()
        i = 1
        while (i < 10) {
            t2 = t2.getSquare()
            ++i
        }

        /* qhasm: z_50_0 = z_50_10*z_10_0 */
        /* asm 1: fe_mul(>z_50_0=fe#2,<z_50_10=fe#3,<z_10_0=fe#2); */
        /* asm 2: fe_mul(>z_50_0=t1,<z_50_10=t2,<z_10_0=t1); */
        t1 = t2 * t1

        /* qhasm: z_100_50 = z_50_0^2^50 */
        /* asm 1: fe_sq.fe_sq(>z_100_50=fe#3,<z_50_0=fe#2); for (i = 1;i < 50;++i) fe_sq.fe_sq(>z_100_50=fe#3,>z_100_50=fe#3); */
        /* asm 2: fe_sq.fe_sq(>z_100_50=t2,<z_50_0=t1); for (i = 1;i < 50;++i) fe_sq.fe_sq(>z_100_50=t2,>z_100_50=t2); */
        t2 = t1.getSquare()
        i = 1
        while (i < 50) {
            t2 = t2.getSquare()
            ++i
        }

        /* qhasm: z_100_0 = z_100_50*z_50_0 */
        /* asm 1: fe_mul(>z_100_0=fe#3,<z_100_50=fe#3,<z_50_0=fe#2); */
        /* asm 2: fe_mul(>z_100_0=t2,<z_100_50=t2,<z_50_0=t1); */
        t2 *= t1

        /* qhasm: z_200_100 = z_100_0^2^100 */
        /* asm 1: fe_sq.fe_sq(>z_200_100=fe#4,<z_100_0=fe#3); for (i = 1;i < 100;++i) fe_sq.fe_sq(>z_200_100=fe#4,>z_200_100=fe#4); */
        /* asm 2: fe_sq.fe_sq(>z_200_100=t3,<z_100_0=t2); for (i = 1;i < 100;++i) fe_sq.fe_sq(>z_200_100=t3,>z_200_100=t3); */
        t3 = t2.getSquare()
        i = 1
        while (i < 100) {
            t3 = t3.getSquare()
            ++i
        }

        /* qhasm: z_200_0 = z_200_100*z_100_0 */
        /* asm 1: fe_mul(>z_200_0=fe#3,<z_200_100=fe#4,<z_100_0=fe#3); */
        /* asm 2: fe_mul(>z_200_0=t2,<z_200_100=t3,<z_100_0=t2); */
        t2 = t3 * t2

        /* qhasm: z_250_50 = z_200_0^2^50 */
        /* asm 1: fe_sq.fe_sq(>z_250_50=fe#3,<z_200_0=fe#3); for (i = 1;i < 50;++i) fe_sq.fe_sq(>z_250_50=fe#3,>z_250_50=fe#3); */
        /* asm 2: fe_sq.fe_sq(>z_250_50=t2,<z_200_0=t2); for (i = 1;i < 50;++i) fe_sq.fe_sq(>z_250_50=t2,>z_250_50=t2); */
        t2 = t2.getSquare()
        i = 1
        while (i < 50) {
            t2 = t2.getSquare()
            ++i
        }

        /* qhasm: z_250_0 = z_250_50*z_50_0 */
        /* asm 1: fe_mul(>z_250_0=fe#2,<z_250_50=fe#3,<z_50_0=fe#2); */
        /* asm 2: fe_mul(>z_250_0=t1,<z_250_50=t2,<z_50_0=t1); */
        t1 = t2 * t1

        /* qhasm: z_255_5 = z_250_0^2^5 */
        /* asm 1: fe_sq.fe_sq(>z_255_5=fe#2,<z_250_0=fe#2); for (i = 1;i < 5;++i) fe_sq.fe_sq(>z_255_5=fe#2,>z_255_5=fe#2); */
        /* asm 2: fe_sq.fe_sq(>z_255_5=t1,<z_250_0=t1); for (i = 1;i < 5;++i) fe_sq.fe_sq(>z_255_5=t1,>z_255_5=t1); */
        t1 = t1.getSquare()
        i = 1
        while (i < 5) {
            t1 = t1.getSquare()
            ++i
        }

        /* qhasm: z_255_21 = z_255_5*z11 */
        /* asm 1: fe_mul(>z_255_21=fe#12,<z_255_5=fe#2,<z11=fe#1); */
        /* asm 2: fe_mul(>z_255_21=out,<z_255_5=t1,<z11=t0); */
        return t1 * t0
    }

}
