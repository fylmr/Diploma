package ru.fylmr.diploma.crypto.primitives.curve25519

fun scalarMultiplication(q: ByteArray, n: ByteArray, p: ByteArray): Int {
    val e = ByteArray(32)
    val x1 = FieldElement()
    var x2 = FieldElement()
    var z2 = FieldElement()
    var x3 = FieldElement()
    val z3 = FieldElement()
    var tmp0 = FieldElement()
    val tmp1 = FieldElement()

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

    feFromBytes(x1.bytes, p)
    feOne(x2.bytes)
    feZero(z2.bytes)
    feCopy(x3.bytes, x1.bytes)
    feOne(z3.bytes)
    swap = 0

    var pos = 254
    while (pos >= 0) {
        b = e[pos / 8].toInt() ushr (pos and 7)
        b = b and 1
        swap = swap xor b
        fe_cswap(x2.bytes, x3.bytes, swap)
        fe_cswap(z2.bytes, z3.bytes, swap)
        swap = b

        /* D = X3-Z3 */
        feSubtraction(tmp0.bytes, x3.bytes, z3.bytes)

        /* B = X2-Z2 */
        feSubtraction(tmp1.bytes, x2.bytes, z2.bytes)

        /* A = X2+Z2 */
        x2 += z2

        /* C = X3+Z3 */
        z2 = x3 + z3

        /* DA = D*A */
        feMultiplication(z3.bytes, tmp0.bytes, x2.bytes)

        /* CB = C*B */
        feMultiplication(z2.bytes, z2.bytes, tmp1.bytes)

        /* BB = B^2 */
        feSquare(tmp0.bytes, tmp1.bytes)

        /* AA = A^2 */
        feSquare(tmp1.bytes, x2.bytes)

        /* t0 = DA+CB */
        x3 = z3 + z2

        /* assign x3 to t0 */

        /* t1 = DA-CB */
        feSubtraction(z2.bytes, z3.bytes, z2.bytes)

        /* X4 = AA*BB */
        feMultiplication(x2.bytes, tmp1.bytes, tmp0.bytes)

        /* E = AA-BB */
        feSubtraction(tmp1.bytes, tmp1.bytes, tmp0.bytes)

        /* t2 = t1^2 */
        feSquare(z2.bytes, z2.bytes)

        /* t3 = a24*E */
        fe_mul121666(z3.bytes, tmp1.bytes)

        /* X5 = t0^2 */
        feSquare(x3.bytes, x3.bytes)

        /* t4 = BB+t3 */
        tmp0 += z3

        /* Z5 = X1*t2 */
        feMultiplication(z3.bytes, x1.bytes, z2.bytes)

        /* Z4 = E*t4 */
        feMultiplication(z2.bytes, tmp1.bytes, tmp0.bytes)

        --pos
    }

    fe_cswap(x2.bytes, x3.bytes, swap)
    fe_cswap(z2.bytes, z3.bytes, swap)
    feInversion(z2.bytes, z2.bytes)
    feMultiplication(x2.bytes, x2.bytes, z2.bytes)
    feToBytes(q, x2.bytes)

    return 0
}
