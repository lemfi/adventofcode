package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day13Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(295, Day13.star1("""939
7,13,x,x,59,x,31,19"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(3417L, Day13.star2("""17,x,13,19"""))
    }

    @Test
    fun sample3() {

        Assertions.assertEquals(754018L, Day13.star2("""67,7,59,61"""))
    }

    @Test
    fun sample4() {

        Assertions.assertEquals(779210L, Day13.star2("""67,x,7,59,61"""))
    }

    @Test
    fun sample5() {

        Assertions.assertEquals(1261476L, Day13.star2("""67,7,x,59,61"""))
    }

    @Test
    fun sample6() {

        Assertions.assertEquals(1202161486L, Day13.star2("""1789,37,47,1889"""))
    }

    @Test
    fun sample7() {

        Assertions.assertEquals(1068781L, Day13.star2("""7,13,x,x,59,x,31,19"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(102, Day13.star1(Day13.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(327300950120029L, Day13.star2(Day13.data))
    }

}