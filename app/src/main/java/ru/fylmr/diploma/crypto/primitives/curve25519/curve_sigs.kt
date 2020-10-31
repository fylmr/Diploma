package ru.fylmr.diploma.crypto.primitives.curve25519

fun curve25519Keygen(pubKeyOut: ByteArray?, privateKeyIn: ByteArray?) {
    val ed = ge_p3() // Ed25519 pubkey point
    val edYPlusOne = IntArray(10)
    val oneMinusEdY = IntArray(10)
    val invOneMinusEdY = IntArray(10)
    val montX = IntArray(10)

    /* Perform a fixed-base multiplication of the Edwards base point,
     (which is efficient due to precalculated tables), then convert
     to the Curve25519 montgomery-format public key.  In particular,
     convert Curve25519's "montgomery" x-coordinate into an Ed25519
     "edwards" y-coordinate:

     mont_x = (ed_y + 1) / (1 - ed_y)

     with projective coordinates:

     mont_x = (ed_y + ed_z) / (ed_z - ed_y)

     NOTE: ed_y=1 is converted to mont_x=0 since fe_invert is mod-exp
  */ge_scalarmult_base.ge_scalarmult_base(ed, privateKeyIn)
    fe_add.fe_add(edYPlusOne, ed.Y, ed.Z)
    fe_sub.fe_sub(oneMinusEdY, ed.Z, ed.Y)
    fe_invert.fe_invert(invOneMinusEdY, oneMinusEdY)
    fe_mul.fe_mul(montX, edYPlusOne, invOneMinusEdY)
    fe_tobytes.fe_tobytes(pubKeyOut, montX)
}
