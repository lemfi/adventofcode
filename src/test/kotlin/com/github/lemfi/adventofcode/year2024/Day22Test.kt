package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day22Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            37327623, Day22.star1(
                """1
10
100
2024"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            23, Day22.star2(
                """1
2
3
2024"""
            )
        )
    }
}