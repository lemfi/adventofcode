package com.github.lemfi.adventofcode.year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day23Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            12521, Day23.star1(
                """#############
#...........#
###B#C#B#D###
  #A#D#C#A#
  #########"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            44169, Day23.star2(
                """#############
#...........#
###B#C#B#D###
  #A#D#C#A#
  #########"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(18282, Day23.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(50132, Day23.star2(null))
    }
}