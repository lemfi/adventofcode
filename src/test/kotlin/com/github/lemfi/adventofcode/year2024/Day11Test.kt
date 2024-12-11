package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun star1() {
        Assertions.assertEquals(55312, Day11.star1("125 17"))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(65601038650482, Day11.star2("125 17"))
    }
}