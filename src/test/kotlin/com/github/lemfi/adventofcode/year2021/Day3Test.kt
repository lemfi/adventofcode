package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day3.Day3
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day3Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            198, Day3.star1(
                """00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            230, Day3.star2(
                """00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(4118544, Day3.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(3832770, Day3.star2(null))
    }
}