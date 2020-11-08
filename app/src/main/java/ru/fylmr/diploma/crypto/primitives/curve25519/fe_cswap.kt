package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * fe_cswap
 *
 * Заменить (f,g) на (g,f) если b == 1;
 * заменить (f,g) на (f,g) если b == 0.
 */
fun fe_cswap(f: IntArray, g: IntArray, b: Int) {
    var b = b
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
    var x0 = f0 xor g0
    var x1 = f1 xor g1
    var x2 = f2 xor g2
    var x3 = f3 xor g3
    var x4 = f4 xor g4
    var x5 = f5 xor g5
    var x6 = f6 xor g6
    var x7 = f7 xor g7
    var x8 = f8 xor g8
    var x9 = f9 xor g9
    b = -b
    x0 = x0 and b
    x1 = x1 and b
    x2 = x2 and b
    x3 = x3 and b
    x4 = x4 and b
    x5 = x5 and b
    x6 = x6 and b
    x7 = x7 and b
    x8 = x8 and b
    x9 = x9 and b
    f[0] = f0 xor x0
    f[1] = f1 xor x1
    f[2] = f2 xor x2
    f[3] = f3 xor x3
    f[4] = f4 xor x4
    f[5] = f5 xor x5
    f[6] = f6 xor x6
    f[7] = f7 xor x7
    f[8] = f8 xor x8
    f[9] = f9 xor x9
    g[0] = g0 xor x0
    g[1] = g1 xor x1
    g[2] = g2 xor x2
    g[3] = g3 xor x3
    g[4] = g4 xor x4
    g[5] = g5 xor x5
    g[6] = g6 xor x6
    g[7] = g7 xor x7
    g[8] = g8 xor x8
    g[9] = g9 xor x9
}
