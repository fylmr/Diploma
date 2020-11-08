package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * fe_neg
 *
 * h := -f
 */
fun fe_neg(h: IntArray, f: IntArray) {
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
    val h0 = -f0
    val h1 = -f1
    val h2 = -f2
    val h3 = -f3
    val h4 = -f4
    val h5 = -f5
    val h6 = -f6
    val h7 = -f7
    val h8 = -f8
    val h9 = -f9
    h[0] = h0
    h[1] = h1
    h[2] = h2
    h[3] = h3
    h[4] = h4
    h[5] = h5
    h[6] = h6
    h[7] = h7
    h[8] = h8
    h[9] = h9
}
