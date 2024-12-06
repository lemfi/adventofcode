package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            41, Day06.star1(
                """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#..."""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            6, Day06.star2(
                """....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#..."""
            )
        )
    }
}