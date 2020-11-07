package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p3_0
 */
fun geExtendedZero(h: GEExtended) {
    //CONVERT #include "ge.h"
    fe_0(h.X)
    fe_1(h.Y)
    fe_1(h.Z)
    fe_0(h.T)
}
