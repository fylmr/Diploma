package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p3_to_p2
 */
fun ge_p3_to_p2(r: GEProjective, p: GEExtended) {
    feCopy(r.X, p.X)
    feCopy(r.Y, p.Y)
    feCopy(r.Z, p.Z)
}
