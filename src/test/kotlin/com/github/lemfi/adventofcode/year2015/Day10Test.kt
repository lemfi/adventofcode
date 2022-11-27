package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day10Test {

    @Test
    fun star1() {
        Assertions.assertEquals("11", Day10.group("1".toList()).joinToString(""))
        Assertions.assertEquals("21", Day10.group("11".toList()).joinToString(""))
        Assertions.assertEquals("1211", Day10.group("21".toList()).joinToString(""))
        Assertions.assertEquals("111221", Day10.group("1211".toList()).joinToString(""))
        Assertions.assertEquals("312211", Day10.group("111221".toList()).joinToString(""))

        Assertions.assertEquals(82350, Day10.star1("1"))

    }

    @Test
    fun star2() {
        Assertions.assertEquals(1166642, Day10.star2("1"))
    }
}