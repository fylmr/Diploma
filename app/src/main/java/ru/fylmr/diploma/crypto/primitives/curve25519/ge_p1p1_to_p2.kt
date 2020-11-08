package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p1p1_to_p2
 *
 * r := p
 */
fun geCompletedToProjective(r: GEProjective, p: GECompleted) {
    feMultiplication(r.X, p.X.bytes, p.T.bytes)
    feMultiplication(r.Y, p.Y.bytes, p.Z.bytes)
    feMultiplication(r.Z, p.Z.bytes, p.T.bytes)
}
