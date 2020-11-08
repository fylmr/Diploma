package ru.fylmr.diploma.crypto.primitives.curve25519

/**
 * r = 2*p
 * ge_p2_dbl
 */
fun ge_p2_dbl(r: GECompleted, p: GEProjective) {
    val t0 = IntArray(10)
    //CONVERT #include "ge_p2_dbl.h"

    /* qhasm: enter ge_p2_dbl */

    /* qhasm: fe X1 */

    /* qhasm: fe Y1 */

    /* qhasm: fe Z1 */

    /* qhasm: fe A */

    /* qhasm: fe AA */

    /* qhasm: fe XX */

    /* qhasm: fe YY */

    /* qhasm: fe B */

    /* qhasm: fe X3 */

    /* qhasm: fe Y3 */

    /* qhasm: fe Z3 */

    /* qhasm: fe T3 */

    /* qhasm: XX=X1^2 */
    /* asm 1: fe_sq.fe_sq(>XX=fe#1,<X1=fe#11); */
    /* asm 2: fe_sq.fe_sq(>XX=r.X,<X1=p.X); */
    feSquare(r.X.bytes, p.X)

    /* qhasm: YY=Y1^2 */
    /* asm 1: fe_sq.fe_sq(>YY=fe#3,<Y1=fe#12); */
    /* asm 2: fe_sq.fe_sq(>YY=r.Z,<Y1=p.Y); */
    feSquare(r.Z.bytes, p.Y)

    /* qhasm: B=2*Z1^2 */
    /* asm 1: fe_sq2.fe_sq2(>B=fe#4,<Z1=fe#13); */
    /* asm 2: fe_sq2.fe_sq2(>B=r.T,<Z1=p.Z); */
    feDoubleSquare(r.T.bytes, p.Z)

    /* qhasm: A=X1+Y1 */
    /* asm 1: fe_add.fe_add(>A=fe#2,<X1=fe#11,<Y1=fe#12); */
    /* asm 2: fe_add.fe_add(>A=r.Y,<X1=p.X,<Y1=p.Y); */
    feAddition(r.Y.bytes, p.X, p.Y)

    /* qhasm: AA=A^2 */
    /* asm 1: fe_sq.fe_sq(>AA=fe#5,<A=fe#2); */
    /* asm 2: fe_sq.fe_sq(>AA=t0,<A=r.Y); */
    feSquare(t0, r.Y.bytes)

    /* qhasm: Y3=YY+XX */
    /* asm 1: fe_add.fe_add(>Y3=fe#2,<YY=fe#3,<XX=fe#1); */
    /* asm 2: fe_add.fe_add(>Y3=r.Y,<YY=r.Z,<XX=r.X); */
    feAddition(r.Y.bytes, r.Z.bytes, r.X.bytes)

    /* qhasm: Z3=YY-XX */
    /* asm 1: fe_sub.fe_sub(>Z3=fe#3,<YY=fe#3,<XX=fe#1); */
    /* asm 2: fe_sub.fe_sub(>Z3=r.Z,<YY=r.Z,<XX=r.X); */
    feSubtraction(r.Z.bytes, r.Z.bytes, r.X.bytes)

    /* qhasm: X3=AA-Y3 */
    /* asm 1: fe_sub.fe_sub(>X3=fe#1,<AA=fe#5,<Y3=fe#2); */
    /* asm 2: fe_sub.fe_sub(>X3=r.X,<AA=t0,<Y3=r.Y); */
    feSubtraction(r.X.bytes, t0, r.Y.bytes)

    /* qhasm: T3=B-Z3 */
    /* asm 1: fe_sub.fe_sub(>T3=fe#4,<B=fe#4,<Z3=fe#3); */
    /* asm 2: fe_sub.fe_sub(>T3=r.T,<B=r.T,<Z3=r.Z); */
    feSubtraction(r.T.bytes, r.T.bytes, r.Z.bytes)

    /* qhasm: return */
}
