package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            15, Day2.star1(
                """A Y
B X
C Z"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            12, Day2.star2(
                """A Y
B X
C Z"""
            )
        )
    }
}