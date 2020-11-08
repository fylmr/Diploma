package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p3_0
 */
fun geExtendedZero(h: GEExtended) {
    h.Y = FieldElement.one
    h.Z = FieldElement.one
}
