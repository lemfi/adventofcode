package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun star1() {
        Assertions.assertEquals(24, Day14.star1("""498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(93, Day14.star2("""498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9"""))
    }
}