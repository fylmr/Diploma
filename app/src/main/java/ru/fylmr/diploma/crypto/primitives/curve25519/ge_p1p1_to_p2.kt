package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p1p1_to_p2
 *
 * r := p
 */
fun geCompletedToProjective(r: ge_p2, p: ge_p1p1) {
    feMultiplication(r.X, p.X, p.T)
    feMultiplication(r.Y, p.Y, p.Z)
    feMultiplication(r.Z, p.Z, p.T)
}
