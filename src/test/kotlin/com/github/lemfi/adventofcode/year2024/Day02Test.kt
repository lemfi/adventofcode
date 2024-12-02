package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            2, Day02.star1(
                """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            4, Day02.star2(
                """7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9"""
            )
        )
    }
}