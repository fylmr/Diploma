package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p3_to_p2
 */
fun ge_p3_to_p2(r: GEProjective, p: GEExtended) {
    feCopy(r.X.bytes, p.X.bytes)
    feCopy(r.Y.bytes, p.Y.bytes)
    feCopy(r.Z.bytes, p.Z.bytes)
}
