package ru.fylmr.diploma.crypto.primitives.curve25519;

public class ge_p3_0 {

//CONVERT #include "ge.h"

    public static void ge_p3_0(GroupElemExtended h) {
        new fe_0(h.X);
        new fe_1(h.Y);
        new fe_1(h.Z);
        new fe_0(h.T);
    }


}
