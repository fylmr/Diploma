package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p3_dbl
 *
 * r = 2*p
 */
fun ge_p3_dbl(r: ge_p1p1, p: GEExtended) {
    val q = ge_p2()
    ge_p3_to_p2(q, p)
    ge_p2_dbl(r, q)
}
