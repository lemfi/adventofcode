package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.year2015.Day3.Day3
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day3Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(2, Day3.star1(">"))
        Assertions.assertEquals(4, Day3.star1("^>v<"))
        Assertions.assertEquals(2, Day3.star1("^v^v^v^v^v"))
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(3, Day3.star2("^v"))
        Assertions.assertEquals(3, Day3.star2("^>v<"))
        Assertions.assertEquals(11, Day3.star2("^v^v^v^v^v"))
    }

    @Test
    fun star1() {
        Assertions.assertEquals(2565, Day3.star1(null))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(2639, Day3.star2(null))
    }
}