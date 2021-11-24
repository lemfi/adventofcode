package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.year2015.Day2.Day2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun star1() {
        Assertions.assertEquals(1588178, Day2.star1(null))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(3783758, Day2.star2(null))
    }
}