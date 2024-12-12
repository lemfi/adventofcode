package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun star11() {
        Assertions.assertEquals(
            140, Day12.star1(
                """AAAA
BBCD
BBCC
EEEC"""
            )
        )
    }

    @Test
    fun star12() {
        Assertions.assertEquals(
            1930, Day12.star1(
                """RRRRIICCFF
RRRRIICCCF
VVRRRCCFFF
VVRCCCJFFF
VVVVCJJCFE
VVIVCCJJEE
VVIIICJJEE
MIIIIIJJEE
MIIISIJEEE
MMMISSJEEE"""
            )
        )
    }

    @Test
    fun star21() {
        Assertions.assertEquals(
            80, Day12.star2(
                """AAAA
BBCD
BBCC
EEEC"""
            )
        )
    }

    @Test
    fun star22() {
        Assertions.assertEquals(
            236, Day12.star2(
                """EEEEE
EXXXX
EEEEE
EXXXX
EEEEE"""
            )
        )
    }

    @Test
    fun star23() {
        Assertions.assertEquals(
            368, Day12.star2(
                """AAAAAA
AAABBA
AAABBA
ABBAAA
ABBAAA
AAAAAA"""
            )
        )
    }

    @Test
    fun star24() {
        Assertions.assertEquals(
            436, Day12.star2(
                """OOOOO
OXOXO
OOOOO
OXOXO
OOOOO"""
            )
        )
    }
}