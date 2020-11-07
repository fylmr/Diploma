package ru.fylmr.diploma.crypto.primitives.curve25519;

import static ru.fylmr.diploma.crypto.primitives.curve25519.Fe_mulKt.feMultiplication;

public class ge_p1p1_to_p2 {

//CONVERT #include "ge.h"

/*
r = p
*/

    public static void ge_p1p1_to_p2(ge_p2 r, ge_p1p1 p) {
        feMultiplication(r.X, p.X, p.T);
        feMultiplication(r.Y, p.Y, p.Z);
        feMultiplication(r.Z, p.Z, p.T);
    }


}
