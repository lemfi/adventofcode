package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day14Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(165, Day14.star1("""mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0
mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(208, Day14.star2("""mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(17934269678453, Day14.star1(Day14.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(3440662844064, Day14.star2(Day14.data))
    }

}