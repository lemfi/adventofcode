package com.github.lemfi.adventofcode.year2025

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun star1() {
        Assertions.assertEquals(357, Day03.star1("""987654321111111
811111111111119
234234234234278
818181911112111"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(3121910778619L, Day03.star2("""987654321111111
811111111111119
234234234234278
818181911112111"""))
    }
}