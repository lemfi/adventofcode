package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            229632480, Day14.star1(null)
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(7051, Day14.star2(null))
    }
}