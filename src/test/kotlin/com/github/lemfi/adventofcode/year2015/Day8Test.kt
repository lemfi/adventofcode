package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day8Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(
            12, Day8.star1(
                """""
            |"abc"
            |"aaa\"aaa"
            |"\x27"""".trimMargin()
            )
        )
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(0, Day8.star2("turn on 0,0 through 999,999"))
    }

    @Test
    fun star1() {
        Assertions.assertEquals(0, Day8.star1(null))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(0, Day8.star2(null))
    }
}