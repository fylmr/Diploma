package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p2
 *
 * (projective): (X:Y:Z), удовлетворяющие x=X/Z, y=Y/Z
 */
@Suppress("PropertyName")
class GEProjective {
    var X = FieldElement()
    var Y = FieldElement()
    var Z = FieldElement()
}
