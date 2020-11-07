package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * Группа — набор пар (x,y) элементов поля (FieldElement)
 * удовлетворяющих уравнению -x^2 + y^2 = 1 + d x^2y^2,
 * где d = -121665/121666.
 *
 *
 * (X:Y:Z:T) удовлетворяют x=X/Z, y=Y/Z, XY=ZT
 */
@Suppress("PropertyName")
class GroupElemExtended {
    @JvmField
    var X = IntArray(10)

    @JvmField
    var Y = IntArray(10)

    @JvmField
    var Z = IntArray(10)

    @JvmField
    var T = IntArray(10)

}
