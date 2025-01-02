package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day21Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            126384, Day21.star1(
                """029A
980A
179A
456A
379A"""
            )
        )
    }

    @Test
    fun star12() {
        Assertions.assertEquals(
            68 * 29, Day21.star1(
                """029A"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(0, Day21.star2(null))
    }
}