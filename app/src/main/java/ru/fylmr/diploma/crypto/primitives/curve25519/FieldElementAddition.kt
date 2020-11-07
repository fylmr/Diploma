package ru.fylmr.diploma.crypto.primitives.curve25519

object FieldElementAddition {
    /**
     * h = f + g
     */
    @JvmStatic
    fun fieldElementAddition(h: IntArray, f: IntArray, g: IntArray) {
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
}
