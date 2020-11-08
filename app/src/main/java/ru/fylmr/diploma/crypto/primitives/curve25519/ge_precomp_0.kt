package ru.fylmr.diploma.crypto.primitives.curve25519

fun gePrecomputedZero(h: GEPrecomputed) {
    //CONVERT #include "ge.h"
    feOne(h.yplusx)
    feOne(h.yminusx)
    feZero(h.xy2d)
}
