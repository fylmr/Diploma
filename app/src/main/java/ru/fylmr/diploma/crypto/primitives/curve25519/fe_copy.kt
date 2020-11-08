package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * fe_copy
 *
 * h := f
 */
fun feCopy(h: IntArray, f: IntArray) {
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
    h[0] = f0
    h[1] = f1
    h[2] = f2
    h[3] = f3
    h[4] = f4
    h[5] = f5
    h[6] = f6
    h[7] = f7
    h[8] = f8
    h[9] = f9
}
