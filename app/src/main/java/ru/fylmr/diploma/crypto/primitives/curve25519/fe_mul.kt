package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * fe_mul
 *
 * h = f * g
 */
fun fe_mul(h: IntArray, f: IntArray, g: IntArray) {
    val hr = getHr(f, g)
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

    /*
|h0| <= (1.65*1.65*2^52*(1+19+19+19+19)+1.65*1.65*2^50*(38+38+38+38+38))
i.e. |h0| <= 1.4*2^60; narrower ranges for h2, h4, h6, h8
|h1| <= (1.65*1.65*2^51*(1+1+19+19+19+19+19+19+19+19))
i.e. |h1| <= 1.7*2^59; narrower ranges for h3, h5, h7, h9
*/carry0 = hr[0] + (1 shl 25).toLong() shr 26
    hr[1] += carry0
    hr[0] -= carry0 shl 26
    carry4 = hr[4] + (1 shl 25).toLong() shr 26
    hr[5] += carry4
    hr[4] -= carry4 shl 26
    /* |h0| <= 2^25 */
    /* |h4| <= 2^25 */
    /* |h1| <= 1.71*2^59 */
    /* |h5| <= 1.71*2^59 */carry1 = hr[1] + (1 shl 24).toLong() shr 25
    hr[2] += carry1
    hr[1] -= carry1 shl 25
    carry5 = hr[5] + (1 shl 24).toLong() shr 25
    hr[6] += carry5
    hr[5] -= carry5 shl 25
    /* |h1| <= 2^24; from now on fits into int32 */
    /* |h5| <= 2^24; from now on fits into int32 */
    /* |h2| <= 1.41*2^60 */
    /* |h6| <= 1.41*2^60 */carry2 = hr[2] + (1 shl 25).toLong() shr 26
    hr[3] += carry2
    hr[2] -= carry2 shl 26
    carry6 = hr[6] + (1 shl 25).toLong() shr 26
    hr[7] += carry6
    hr[6] -= carry6 shl 26
    /* |h2| <= 2^25; from now on fits into int32 unchanged */
    /* |h6| <= 2^25; from now on fits into int32 unchanged */
    /* |h3| <= 1.71*2^59 */
    /* |h7| <= 1.71*2^59 */carry3 = hr[3] + (1 shl 24).toLong() shr 25
    hr[4] += carry3
    hr[3] -= carry3 shl 25
    carry7 = hr[7] + (1 shl 24).toLong() shr 25
    hr[8] += carry7
    hr[7] -= carry7 shl 25
    /* |h3| <= 2^24; from now on fits into int32 unchanged */
    /* |h7| <= 2^24; from now on fits into int32 unchanged */
    /* |h4| <= 1.72*2^34 */
    /* |h8| <= 1.41*2^60 */carry4 = hr[4] + (1 shl 25).toLong() shr 26
    hr[5] += carry4
    hr[4] -= carry4 shl 26
    carry8 = hr[8] + (1 shl 25).toLong() shr 26
    hr[9] += carry8
    hr[8] -= carry8 shl 26
    /* |h4| <= 2^25; from now on fits into int32 unchanged */
    /* |h8| <= 2^25; from now on fits into int32 unchanged */
    /* |h5| <= 1.01*2^24 */
    /* |h9| <= 1.71*2^59 */carry9 = hr[9] + (1 shl 24).toLong() shr 25
    hr[0] += carry9 * 19
    hr[9] -= carry9 shl 25
    /* |h9| <= 2^24; from now on fits into int32 unchanged */
    /* |h0| <= 1.1*2^39 */carry0 = hr[0] + (1 shl 25).toLong() shr 26
    hr[1] += carry0
    hr[0] -= carry0 shl 26
    /* |h0| <= 2^25; from now on fits into int32 unchanged */
    /* |h1| <= 1.01*2^24 */h[0] = hr[0].toInt()
    h[1] = hr[1].toInt()
    h[2] = hr[2].toInt()
    h[3] = hr[3].toInt()
    h[4] = hr[4].toInt()
    h[5] = hr[5].toInt()
    h[6] = hr[6].toInt()
    h[7] = hr[7].toInt()
    h[8] = hr[8].toInt()
    h[9] = hr[9].toInt()
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
