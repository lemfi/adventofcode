package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            13, Day9.star1(
                """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            36, Day9.star2(
                """R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20"""
            )
        )
    }
}