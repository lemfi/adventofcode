package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            3, Day20.star1(
                """1
2
-3
3
-2
0
4"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            1623178306, Day20.star2(
                """1
2
-3
3
-2
0
4"""
            )
        )
    }
}