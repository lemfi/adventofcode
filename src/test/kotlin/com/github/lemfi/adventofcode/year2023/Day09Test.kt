package com.github.lemfi.adventofcode.year2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day09Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            114, Day09.star1(
                """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            2, Day09.star2(
                """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
            )
        )
    }
}