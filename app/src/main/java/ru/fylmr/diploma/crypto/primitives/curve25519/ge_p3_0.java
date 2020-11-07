package ru.fylmr.diploma.crypto.primitives.curve25519;

public class ge_p3_0 {

//CONVERT #include "ge.h"

    public static void ge_p3_0(GroupElemExtended h) {
        fe_0.fe_0(h.X);
        fe_1.fe_1(h.Y);
        fe_1.fe_1(h.Z);
        fe_0.fe_0(h.T);
    }


}
