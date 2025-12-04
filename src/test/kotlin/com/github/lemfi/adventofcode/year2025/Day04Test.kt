package com.github.lemfi.adventofcode.year2025

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun star1() {
        Assertions.assertEquals(13, Day04.star1("""..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@."""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(43, Day04.star2("""..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@."""))
    }
}