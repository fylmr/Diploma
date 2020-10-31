package ru.fylmr.diploma.crypto.primitives.curve25519

fun scalarMultiplication(q: ByteArray?, n: ByteArray, p: ByteArray?): Int {
    val e = ByteArray(32)
    val x1 = IntArray(10)
    val x2 = IntArray(10)
    val z2 = IntArray(10)
    val x3 = IntArray(10)
    val z3 = IntArray(10)
    val tmp0 = IntArray(10)
    val tmp1 = IntArray(10)

    var swap: Int
    var b: Int
    var i = 0

    while (i < 32) {
        e[i] = n[i]
        ++i
    }

    //  e[0] &= 248;
    //  e[31] &= 127;
    //  e[31] |= 64;

    fe_frombytes.fe_frombytes(x1, p)
    fe_1.fe_1(x2)
    fe_0.fe_0(z2)
    fe_copy.fe_copy(x3, x1)
    fe_1.fe_1(z3)
    swap = 0

    var pos = 254
    while (pos >= 0) {
        b = e[pos / 8].toInt() ushr (pos and 7)
        b = b and 1
        swap = swap xor b
        fe_cswap.fe_cswap(x2, x3, swap)
        fe_cswap.fe_cswap(z2, z3, swap)
        swap = b

        /* D = X3-Z3 */
        fe_sub.fe_sub(tmp0, x3, z3)

        /* B = X2-Z2 */
        fe_sub.fe_sub(tmp1, x2, z2)

        /* A = X2+Z2 */
        fe_add.fe_add(x2, x2, z2)

        /* C = X3+Z3 */
        fe_add.fe_add(z2, x3, z3)

        /* DA = D*A */
        fe_mul.fe_mul(z3, tmp0, x2)

        /* CB = C*B */
        fe_mul.fe_mul(z2, z2, tmp1)

        /* BB = B^2 */
        fe_sq.fe_sq(tmp0, tmp1)

        /* AA = A^2 */
        fe_sq.fe_sq(tmp1, x2)

        /* t0 = DA+CB */
        fe_add.fe_add(x3, z3, z2)

        /* assign x3 to t0 */

        /* t1 = DA-CB */
        fe_sub.fe_sub(z2, z3, z2)

        /* X4 = AA*BB */
        fe_mul.fe_mul(x2, tmp1, tmp0)

        /* E = AA-BB */
        fe_sub.fe_sub(tmp1, tmp1, tmp0)

        /* t2 = t1^2 */
        fe_sq.fe_sq(z2, z2)

        /* t3 = a24*E */
        fe_mul121666.fe_mul121666(z3, tmp1)

        /* X5 = t0^2 */
        fe_sq.fe_sq(x3, x3)

        /* t4 = BB+t3 */
        fe_add.fe_add(tmp0, tmp0, z3)

        /* Z5 = X1*t2 */
        fe_mul.fe_mul(z3, x1, z2)

        /* Z4 = E*t4 */
        fe_mul.fe_mul(z2, tmp1, tmp0)

        --pos
    }

    fe_cswap.fe_cswap(x2, x3, swap)
    fe_cswap.fe_cswap(z2, z3, swap)
    fe_invert.fe_invert(z2, z2)
    fe_mul.fe_mul(x2, x2, z2)
    fe_tobytes.fe_tobytes(q, x2)

    return 0
}
