package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun star1() {
        Assertions.assertEquals("abcdffaa", Day11.star1("abcdefgh"))
        Assertions.assertEquals("ghjaabcc", Day11.star1("ghijklmn"))
    }

    @Test
    fun star2() {
        Assertions.assertEquals("abcdffbb", Day11.star2("abcdefgh"))
        Assertions.assertEquals("ghjbbcdd", Day11.star2("ghijklmn"))
    }
}