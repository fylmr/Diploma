package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p1p1_to_p3
 *
 * Completed в Extended
 */
fun geCompletedToExtended(r: GEExtended, p: GECompleted) {
    r.X = p.X * p.T
    r.Y = p.Y * p.Z
    r.Z = p.Z * p.T
    r.T = p.X * p.Y
}
