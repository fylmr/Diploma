package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * fe_sq
 *
 * h = f * f
 */
@Suppress("LocalVariableName")
fun feSquare(h: IntArray, f: IntArray) {
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
    h[0] = h0.toInt()
    h[1] = h1.toInt()
    h[2] = h2.toInt()
    h[3] = h3.toInt()
    h[4] = h4.toInt()
    h[5] = h5.toInt()
    h[6] = h6.toInt()
    h[7] = h7.toInt()
    h[8] = h8.toInt()
    h[9] = h9.toInt()
}
