package ru.fylmr.diploma.crypto.primitives.curve25519

fun scalarMultiplication(q: ByteArray, n: ByteArray, p: ByteArray): Int {
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

    feFromBytes(x1, p)
    feOne(x2)
    feZero(z2)
    feCopy(x3, x1)
    feOne(z3)
    swap = 0

    var pos = 254
    while (pos >= 0) {
        b = e[pos / 8].toInt() ushr (pos and 7)
        b = b and 1
        swap = swap xor b
        fe_cswap(x2, x3, swap)
        fe_cswap(z2, z3, swap)
        swap = b

        /* D = X3-Z3 */
        feSubtraction(tmp0, x3, z3)

        /* B = X2-Z2 */
        feSubtraction(tmp1, x2, z2)

        /* A = X2+Z2 */
        feAddition(x2, x2, z2)

        /* C = X3+Z3 */
        feAddition(z2, x3, z3)

        /* DA = D*A */
        feMultiplication(z3, tmp0, x2)

        /* CB = C*B */
        feMultiplication(z2, z2, tmp1)

        /* BB = B^2 */
        feSquare(tmp0, tmp1)

        /* AA = A^2 */
        feSquare(tmp1, x2)

        /* t0 = DA+CB */
        feAddition(x3, z3, z2)

        /* assign x3 to t0 */

        /* t1 = DA-CB */
        feSubtraction(z2, z3, z2)

        /* X4 = AA*BB */
        feMultiplication(x2, tmp1, tmp0)

        /* E = AA-BB */
        feSubtraction(tmp1, tmp1, tmp0)

        /* t2 = t1^2 */
        feSquare(z2, z2)

        /* t3 = a24*E */
        fe_mul121666(z3, tmp1)

        /* X5 = t0^2 */
        feSquare(x3, x3)

        /* t4 = BB+t3 */
        feAddition(tmp0, tmp0, z3)

        /* Z5 = X1*t2 */
        feMultiplication(z3, x1, z2)

        /* Z4 = E*t4 */
        feMultiplication(z2, tmp1, tmp0)

        --pos
    }

    fe_cswap(x2, x3, swap)
    fe_cswap(z2, z3, swap)
    feInversion(z2, z2)
    feMultiplication(x2, x2, z2)
    feToBytes(q, x2)

    return 0
}
