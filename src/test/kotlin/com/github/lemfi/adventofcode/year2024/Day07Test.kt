package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day07Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            3749, Day07.star1(
                """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            11387, Day07.star2(
                """190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20"""
            )
        )
    }
}