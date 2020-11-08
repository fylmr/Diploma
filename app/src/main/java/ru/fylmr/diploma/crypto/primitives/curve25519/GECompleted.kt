package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p1p1
 *
 * (completed): ((X:Z),(Y:T)), удовлетворяющие x=X/Z, y=Y/T
 */
@Suppress("PropertyName")
class GECompleted {
    var X = FieldElement()
    var Y = FieldElement()
    var Z = FieldElement()
    var T = FieldElement()
}
