package ru.fylmr.diploma.crypto.primitives.curve25519

private fun load3(`in`: ByteArray, index: Int): Long {
    var result = `in`[index].toLong() and 0xFF
    result = result or (`in`[index + 1].toLong() shl 8 and 0xFF00)
    result = result or (`in`[index + 2].toLong() shl 16 and 0xFF0000)
    return result
}

private fun load4(`in`: ByteArray, index: Int): Long {
    var result = `in`[index].toLong() and 0xFF
    result = result or (`in`[index + 1].toLong() shl 8 and 0xFF00)
    result = result or (`in`[index + 2].toLong() shl 16 and 0xFF0000)
    result = result or (`in`[index + 3].toLong() shl 24 and 0xFF000000L)
    return result
}

/**
 * fe_frombytes
 */
fun feFromBytes(h: IntArray, s: ByteArray) {
    var h0 = load4(s, 0)
    var h1 = load3(s, 4) shl 6
    var h2 = load3(s, 7) shl 5
    var h3 = load3(s, 10) shl 3
    var h4 = load3(s, 13) shl 2
    var h5 = load4(s, 16)
    var h6 = load3(s, 20) shl 7
    var h7 = load3(s, 23) shl 5
    var h8 = load3(s, 26) shl 4
    var h9 = load3(s, 29) and 8388607 shl 2

    val carry0: Long
    val carry1: Long
    val carry2: Long
    val carry3: Long
    val carry4: Long
    val carry5: Long
    val carry6: Long
    val carry7: Long
    val carry8: Long
    val carry9: Long

    carry9 = h9 + (1 shl 24).toLong() shr 25
    h0 += carry9 * 19
    h9 -= carry9 shl 25
    carry1 = h1 + (1 shl 24).toLong() shr 25
    h2 += carry1
    h1 -= carry1 shl 25
    carry3 = h3 + (1 shl 24).toLong() shr 25
    h4 += carry3
    h3 -= carry3 shl 25
    carry5 = h5 + (1 shl 24).toLong() shr 25
    h6 += carry5
    h5 -= carry5 shl 25
    carry7 = h7 + (1 shl 24).toLong() shr 25
    h8 += carry7
    h7 -= carry7 shl 25
    carry0 = h0 + (1 shl 25).toLong() shr 26
    h1 += carry0
    h0 -= carry0 shl 26
    carry2 = h2 + (1 shl 25).toLong() shr 26
    h3 += carry2
    h2 -= carry2 shl 26
    carry4 = h4 + (1 shl 25).toLong() shr 26
    h5 += carry4
    h4 -= carry4 shl 26
    carry6 = h6 + (1 shl 25).toLong() shr 26
    h7 += carry6
    h6 -= carry6 shl 26
    carry8 = h8 + (1 shl 25).toLong() shr 26
    h9 += carry8
    h8 -= carry8 shl 26

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
