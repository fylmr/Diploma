package ru.fylmr.diploma.crypto.primitives.curve25519

fun gePrecomputedZero(h: GEPrecomputed) {
    h.yplusx = FieldElement.one
    h.yminusx = FieldElement.one
}
