package ru.fylmr.diploma.crypto.primitives.curve25519

fun scalarMultiplication(q: ByteArray, scalar: ByteArray, point: ByteArray) {
    val e = ByteArray(32)
    val x1 = FieldElement()
    var z2 = FieldElement()
    var x3 = FieldElement()

    var swap: Int
    var b: Int
    var i = 0

    while (i < 32) {
        e[i] = scalar[i]
        ++i
    }

    //  e[0] &= 248;
    //  e[31] &= 127;
    //  e[31] |= 64;

    feFromBytes(x1.bytes, point)
    var x2 = FieldElement.one
//    feZero(z2.bytes)
    feCopy(x3.bytes, x1.bytes)
    var z3 = FieldElement.one
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
        var tmp0 = x3 - z3

        /* B = X2-Z2 */
        var tmp1 = x2 - z2

        /* A = X2+Z2 */
        x2 += z2

        /* C = X3+Z3 */
        z2 = x3 + z3

        /* DA = D*A */
        z3 = tmp0 * x2

        /* CB = C*B */
        z2 *= tmp1

        /* BB = B^2 */
        tmp0 = tmp1.getSquare()

        /* AA = A^2 */
        tmp1 = x2.getSquare()

        /* t0 = DA+CB */
        x3 = z3 + z2

        /* assign x3 to t0 */

        /* t1 = DA-CB */
        z2 = z3 - z2

        /* X4 = AA*BB */
        x2 = tmp1 * tmp0

        /* E = AA-BB */
        tmp1 -= tmp0

        /* t2 = t1^2 */
        z2 = z2.getSquare()

        /* t3 = a24*E */
        z3 = tmp1.multiplyBy121666()

        /* X5 = t0^2 */
        x3 = x3.getSquare()

        /* t4 = BB+t3 */
        tmp0 += z3

        /* Z5 = X1*t2 */
        z3 = x1 * z2

        /* Z4 = E*t4 */
        z2 = tmp1 * tmp0

        --pos
    }

    fe_cswap(x2.bytes, x3.bytes, swap)
    fe_cswap(z2.bytes, z3.bytes, swap)
    z2 = z2.getInverted()
    x2 *= z2

    feToBytes(q, x2.bytes)
}
