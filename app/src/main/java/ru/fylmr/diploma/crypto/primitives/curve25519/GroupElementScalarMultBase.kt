package ru.fylmr.diploma.crypto.primitives.curve25519

private fun equal(b: Int, c: Int): Int {
    val x = b xor c /* 0: yes; 1..255: no */
    var y = x /* 0: yes; 1..255: no */
    y -= 1 /* 4294967295: yes; 0..254: no */
    y = y ushr 31 /* 1: yes; 0: no */
    return y
}

private fun negative(b: Int): Int {
    var x = b.toLong() /* 18446744073709551361..18446744073709551615: yes; 0..255: no */
    x = x ushr 63 /* 1: yes; 0: no */
    return x.toInt()
}

private fun conditionalMove(t: ge_precomp, u: ge_precomp, b: Int) {
    condMove(t.yplusx, u.yplusx, b)
    condMove(t.yminusx, u.yminusx, b)
    condMove(t.xy2d, u.xy2d, b)
}

private fun select(t: ge_precomp, pos: Int, b: Int) {
    val base = when {
        pos <= 7 -> ge_precomp_base_0_7.base
        pos <= 15 -> ge_precomp_base_8_15.base
        pos <= 23 -> ge_precomp_base_16_23.base
        else -> ge_precomp_base_24_31.base
    }
    val minust = ge_precomp()
    val bnegative = negative(b)
    val babs = b - (-bnegative and b shl 1)
    ge_precomp_0.ge_precomp_0(t)
    conditionalMove(t, base[pos][0], equal(babs, 1))
    conditionalMove(t, base[pos][1], equal(babs, 2))
    conditionalMove(t, base[pos][2], equal(babs, 3))
    conditionalMove(t, base[pos][3], equal(babs, 4))
    conditionalMove(t, base[pos][4], equal(babs, 5))
    conditionalMove(t, base[pos][5], equal(babs, 6))
    conditionalMove(t, base[pos][6], equal(babs, 7))
    conditionalMove(t, base[pos][7], equal(babs, 8))

    fe_copy.fe_copy(minust.yplusx, t.yminusx)
    fe_copy.fe_copy(minust.yminusx, t.yplusx)
    fe_neg.fe_neg(minust.xy2d, t.xy2d)

    conditionalMove(t, minust, bnegative)
}

/**
 * h = a * B
 * где a = a[0] + 256*a[1] +...+ 256^31*a[31]
 * B — базовая точка Ed25519 (x,4/5) с положительным x.
 *
 * Условия:
 * a[31] <= 127
 */
fun groupElementScalarMultBase(h: GroupElemExtended?, a: IntArray) {
    val e = IntArray(64)
    var carry: Int
    val r = ge_p1p1()
    val s = ge_p2()
    val t = ge_precomp()
    var i = 0

    while (i < 32) {
        e[2 * i + 0] = (a[i] ushr 0 and 15)
        e[2 * i + 1] = (a[i] ushr 4 and 15)
        ++i
    }

    /* each e[i] is between 0 and 15 */
    /* e[63] is between 0 and 7 */

    carry = 0
    i = 0

    while (i < 63) {
        e[i] += carry
        carry = (e[i] + 8)
        carry = carry shr 4
        e[i] -= carry shl 4
        ++i
    }
    e[63] += carry
    /* each e[i] is between -8 and 8 */ge_p3_0.ge_p3_0(h)
    i = 1
    while (i < 64) {
        select(t, i / 2, e[i])
        ge_madd.ge_madd(r, h, t)
        ge_p1p1_to_p3.ge_p1p1_to_p3(h, r)
        i += 2
    }
    ge_p3_dbl.ge_p3_dbl(r, h)
    ge_p1p1_to_p2.ge_p1p1_to_p2(s, r)
    ge_p2_dbl.ge_p2_dbl(r, s)
    ge_p1p1_to_p2.ge_p1p1_to_p2(s, r)
    ge_p2_dbl.ge_p2_dbl(r, s)
    ge_p1p1_to_p2.ge_p1p1_to_p2(s, r)
    ge_p2_dbl.ge_p2_dbl(r, s)
    ge_p1p1_to_p3.ge_p1p1_to_p3(h, r)
    i = 0
    while (i < 64) {
        select(t, i / 2, e[i])
        ge_madd.ge_madd(r, h, t)
        ge_p1p1_to_p3.ge_p1p1_to_p3(h, r)
        i += 2
    }
}
