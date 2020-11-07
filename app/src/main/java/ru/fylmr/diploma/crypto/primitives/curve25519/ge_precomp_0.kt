package ru.fylmr.diploma.crypto.primitives.curve25519

fun gePrecomp0(h: ge_precomp) {
    //CONVERT #include "ge.h"
    fe_1(h.yplusx)
    fe_1(h.yminusx)
    fe_0(h.xy2d)
}
