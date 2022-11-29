package com.sbc.tshkotlin.multipleresults

/**
 *   多返回值元组
 */
class XDTuples {
    companion object {

        @JvmStatic
        fun <P1, P2> tuple2(
            p1: P1,
            p2: P2,
        ): XDTuple<P1, P2> {
            return XDTuple(p1, p2)
        }

        @JvmStatic
        fun <P1, P2, P3> tuple3(
            p1: P1,
            p2: P2,
            p3: P3,
        ): XDThreeTuple<P1, P2, P3> {
            return XDThreeTuple(p1, p2, p3)
        }

        @JvmStatic
        fun <P1, P2, P3, P4> tuple4(
            p1: P1,
            p2: P2,
            p3: P3,
            p4: P4,
        ): XDFourTuple<P1, P2, P3, P4> {
            return XDFourTuple(p1, p2, p3, p4)
        }

        @JvmStatic
        fun <P1, P2, P3, P4, P5> tuple5(
            p1: P1,
            p2: P2,
            p3: P3,
            p4: P4,
            p5: P5,
        ): XDFiveTuple<P1, P2, P3, P4, P5> {
            return XDFiveTuple(p1, p2, p3, p4, p5)
        }

    }
}

data class XDTuple<P1, P2>(
    val component1: P1,
    val component2: P2,
)

data class XDThreeTuple<P1, P2, P3>(
    val component1: P1,
    val component2: P2,
    val component3: P3,
)

data class XDFourTuple<P1, P2, P3, P4>(
    val component1: P1,
    val component2: P2,
    val component3: P3,
    val component4: P4,
)

data class XDFiveTuple<P1, P2, P3, P4, P5>(
    val component1: P1,
    val component2: P2,
    val component3: P3,
    val component4: P4,
    val component5: P5,
)
