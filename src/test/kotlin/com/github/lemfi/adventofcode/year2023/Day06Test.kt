package com.github.lemfi.adventofcode.year2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            288, Day06.star1(
                """Time:      7  15   30
Distance:  9  40  200"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            71503, Day06.star2(
                """Time:      7  15   30
Distance:  9  40  200"""
            )
        )
    }
}