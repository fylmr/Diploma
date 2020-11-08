package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p1p1
 *
 * (completed): ((X:Z),(Y:T)), удовлетворяющие x=X/Z, y=Y/T
 */
@Suppress("PropertyName")
class GECompleted {
    var X = IntArray(10)
    var Y = IntArray(10)
    var Z = IntArray(10)
    var T = IntArray(10)
}
