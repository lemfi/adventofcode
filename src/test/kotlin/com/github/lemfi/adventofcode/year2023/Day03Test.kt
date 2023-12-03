package com.github.lemfi.adventofcode.year2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            4361, Day03.star1(
                """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598.."""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            467835, Day03.star2(
                """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598.."""
            )
        )
    }
}