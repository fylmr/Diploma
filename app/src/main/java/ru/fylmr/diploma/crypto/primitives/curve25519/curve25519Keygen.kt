package ru.fylmr.diploma.crypto.primitives.curve25519

fun curve25519Keygen(pubKeyOut: ByteArray, privateKeyIn: ByteArray) {
    val ed = GEExtended() // Ed25519 pubkey point

    /*
    Выполнить умножение базовой точки кривой Эдвардса,
    (эффективно благодаря превычисленным таблицам),
    затем сконвертиоровать в открытый ключ в форме Монтгомери для Curve25519

    В частности, так:
    mont_x = (ed_y + 1) / (1 - ed_y)

    в проективных координатах:
    mont_x = (ed_y + ed_z) / (ed_z - ed_y)

    Замечание: ed_y=1 конвертируется в mont_x=0, т.к. fe_invert это mod-exp
    */

    geScalarMultBase(ed, privateKeyIn.map { it.toInt() }.toIntArray())
    val edYPlusOne = ed.Y + ed.Z
    val oneMinusEdY = ed.Z - ed.Y
    val invOneMinusEdY = oneMinusEdY.getInverted()
    val montX = edYPlusOne * invOneMinusEdY
    feToBytes(pubKeyOut, montX.bytes)
}
