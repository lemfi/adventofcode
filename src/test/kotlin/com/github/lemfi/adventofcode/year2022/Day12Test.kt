package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun star1() {
        Assertions.assertEquals(31, Day12.star1("""Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(29, Day12.star2("""Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi"""))
    }
}