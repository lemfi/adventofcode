package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day25Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            3, Day25.star1(
                """#####
.####
.####
.####
.#.#.
.#...
.....

#####
##.##
.#.##
...##
...#.
...#.
.....

.....
#....
#....
#...#
#.#.#
#.###
#####

.....
.....
#.#..
###..
###.#
###.#
#####

.....
.....
.....
#....
#.#..
#.#.#
#####"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(0, Day25.star2(null))
    }
}