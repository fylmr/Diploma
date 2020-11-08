package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p1p1_to_p3
 *
 * Completed в Extended
 */
fun geCompletedToExtended(r: GEExtended, p: GECompleted) {
    feMultiplication(r.X, p.X, p.T)
    feMultiplication(r.Y, p.Y, p.Z)
    feMultiplication(r.Z, p.Z, p.T)
    feMultiplication(r.T, p.X, p.Y)
}
