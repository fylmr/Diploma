package ru.fylmr.diploma.crypto.primitives.curve25519;

public class ge_precomp_0 {

//CONVERT #include "ge.h"

    public static void ge_precomp_0(ge_precomp h) {
        new fe_1(h.yplusx);
        new fe_1(h.yminusx);
        new fe_0(h.xy2d);
    }


}
