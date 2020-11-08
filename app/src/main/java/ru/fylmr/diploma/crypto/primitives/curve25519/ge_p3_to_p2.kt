package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p3_to_p2
 */
fun ge_p3_to_p2(r: ge_p2, p: GEExtended) {
    fe_copy(r.X, p.X)
    fe_copy(r.Y, p.Y)
    fe_copy(r.Z, p.Z)
}
