package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_precomp
 *
 * (Duif): (y+x, y-x, 2dxy)
 */
class GEPrecomputed() {
    var yplusx = IntArray(10)
    var yminusx = IntArray(10)
    var xy2d = IntArray(10)

    constructor(new_yplusx: IntArray, new_yminusx: IntArray, new_xy2d: IntArray) : this() {
        yplusx = new_yplusx
        yminusx = new_yminusx
        xy2d = new_xy2d
    }
}
