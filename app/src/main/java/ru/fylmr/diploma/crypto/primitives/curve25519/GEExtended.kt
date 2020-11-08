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
class GEExtended {
    @JvmField
    var X = FieldElement()

    @JvmField
    var Y = FieldElement()

    @JvmField
    var Z = FieldElement()

    @JvmField
    var T = FieldElement()

}
