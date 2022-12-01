package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            24000, Day1.star1(
                """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            45000, Day1.star2(
                """1000
2000
3000

4000

5000
6000

7000
8000
9000

10000"""
            )
        )
    }
}