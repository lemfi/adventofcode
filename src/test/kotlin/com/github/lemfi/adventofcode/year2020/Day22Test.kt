package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day22Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(306, Day22.star1("""Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(291, Day22.star2("""Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(33421, Day22.star1(Day22.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(33651, Day22.star2(Day22.data))
    }

}