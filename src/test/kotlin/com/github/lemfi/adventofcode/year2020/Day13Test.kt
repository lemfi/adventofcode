package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day13Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(295, day13_1("""939
7,13,x,x,59,x,31,19"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(BigInteger.valueOf(3417), day13_2("""17,x,13,19"""))
    }

    @Test
    fun sample3() {

        Assertions.assertEquals(BigInteger.valueOf(754018), day13_2("""67,7,59,61"""))
    }

    @Test
    fun sample4() {

        Assertions.assertEquals(BigInteger.valueOf(779210), day13_2("""67,x,7,59,61"""))
    }

    @Test
    fun sample5() {

        Assertions.assertEquals(BigInteger.valueOf(1261476), day13_2("""67,7,x,59,61"""))
    }

    @Test
    fun sample6() {

        Assertions.assertEquals(BigInteger.valueOf(1202161486), day13_2("""1789,37,47,1889"""))
    }

    @Test
    fun sample7() {

        Assertions.assertEquals(BigInteger.valueOf(1068781), day13_2("""7,13,x,x,59,x,31,19"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(102, day13_1(day13data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(BigInteger.valueOf(327300950120029), day13_2(day13data))
    }

}