package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * fe_tobytes
 */
fun feToBytes(s: ByteArray, h: IntArray) {

    var h0 = h[0]
    var h1 = h[1]
    var h2 = h[2]
    var h3 = h[3]
    var h4 = h[4]
    var h5 = h[5]
    var h6 = h[6]
    var h7 = h[7]
    var h8 = h[8]
    var h9 = h[9]
    var q: Int
    val carry0: Int
    val carry1: Int
    val carry2: Int
    val carry3: Int
    val carry4: Int
    val carry5: Int
    val carry6: Int
    val carry7: Int
    val carry8: Int
    val carry9: Int
    q = 19 * h9 + (1 shl 24) shr 25
    q = h0 + q shr 26
    q = h1 + q shr 25
    q = h2 + q shr 26
    q = h3 + q shr 25
    q = h4 + q shr 26
    q = h5 + q shr 25
    q = h6 + q shr 26
    q = h7 + q shr 25
    q = h8 + q shr 26
    q = h9 + q shr 25

    h0 += 19 * q
    carry0 = h0 shr 26
    h1 += carry0
    h0 -= carry0 shl 26
    carry1 = h1 shr 25
    h2 += carry1
    h1 -= carry1 shl 25
    carry2 = h2 shr 26
    h3 += carry2
    h2 -= carry2 shl 26
    carry3 = h3 shr 25
    h4 += carry3
    h3 -= carry3 shl 25
    carry4 = h4 shr 26
    h5 += carry4
    h4 -= carry4 shl 26
    carry5 = h5 shr 25
    h6 += carry5
    h5 -= carry5 shl 25
    carry6 = h6 shr 26
    h7 += carry6
    h6 -= carry6 shl 26
    carry7 = h7 shr 25
    h8 += carry7
    h7 -= carry7 shl 25
    carry8 = h8 shr 26
    h9 += carry8
    h8 -= carry8 shl 26
    carry9 = h9 shr 25
    h9 -= carry9 shl 25
    /* h10 = carry9 */

    s[0] = (h0 shr 0).toByte()
    s[1] = (h0 shr 8).toByte()
    s[2] = (h0 shr 16).toByte()
    s[3] = (h0 shr 24 or (h1 shl 2)).toByte()
    s[4] = (h1 shr 6).toByte()
    s[5] = (h1 shr 14).toByte()
    s[6] = (h1 shr 22 or (h2 shl 3)).toByte()
    s[7] = (h2 shr 5).toByte()
    s[8] = (h2 shr 13).toByte()
    s[9] = (h2 shr 21 or (h3 shl 5)).toByte()
    s[10] = (h3 shr 3).toByte()
    s[11] = (h3 shr 11).toByte()
    s[12] = (h3 shr 19 or (h4 shl 6)).toByte()
    s[13] = (h4 shr 2).toByte()
    s[14] = (h4 shr 10).toByte()
    s[15] = (h4 shr 18).toByte()
    s[16] = (h5 shr 0).toByte()
    s[17] = (h5 shr 8).toByte()
    s[18] = (h5 shr 16).toByte()
    s[19] = (h5 shr 24 or (h6 shl 1)).toByte()
    s[20] = (h6 shr 7).toByte()
    s[21] = (h6 shr 15).toByte()
    s[22] = (h6 shr 23 or (h7 shl 3)).toByte()
    s[23] = (h7 shr 5).toByte()
    s[24] = (h7 shr 13).toByte()
    s[25] = (h7 shr 21 or (h8 shl 4)).toByte()
    s[26] = (h8 shr 4).toByte()
    s[27] = (h8 shr 12).toByte()
    s[28] = (h8 shr 20 or (h9 shl 6)).toByte()
    s[29] = (h9 shr 2).toByte()
    s[30] = (h9 shr 10).toByte()
    s[31] = (h9 shr 18).toByte()
}
