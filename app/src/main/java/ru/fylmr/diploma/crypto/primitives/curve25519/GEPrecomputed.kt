package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_precomp
 *
 * (Duif): (y+x, y-x, 2dxy)
 */
class GEPrecomputed() {
    var yplusx = FieldElement()
    var yminusx = FieldElement()
    var xy2d = FieldElement()

    constructor(new_yplusx: IntArray, new_yminusx: IntArray, new_xy2d: IntArray) : this() {
        yplusx = FieldElement(new_yplusx)
        yminusx = FieldElement(new_yminusx)
        xy2d = FieldElement(new_xy2d)
    }
}
