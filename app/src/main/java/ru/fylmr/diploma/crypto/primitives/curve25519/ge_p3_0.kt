package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * ge_p3_0
 */
fun geExtendedZero(h: GEExtended) {
    //CONVERT #include "ge.h"
    feZero(h.X.bytes)
    feOne(h.Y.bytes)
    feOne(h.Z.bytes)
    feZero(h.T.bytes)
}
