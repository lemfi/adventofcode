package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            11, Day01.star1(
                """3   4
4   3
2   5
1   3
3   9
3   3"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            31, Day01.star2(
                """3   4
4   3
2   5
1   3
3   9
3   3"""
            )
        )
    }
}