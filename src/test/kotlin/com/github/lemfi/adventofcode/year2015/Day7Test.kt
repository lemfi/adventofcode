package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.year2015.Day7.Day7
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(
            123, Day7.star1(
                """123 -> a
            |456 -> y
            |a AND y -> d
            |a OR y -> e
            |a LSHIFT 2 -> f
            |y RSHIFT 2 -> g
            |NOT a -> h
            |NOT y -> i""".trimMargin()
            )
        )
        Assertions.assertEquals(
            65079, Day7.star1(
                """123 -> x
            |456 -> y
            |x AND y -> d
            |x OR y -> e
            |x LSHIFT 2 -> f
            |y RSHIFT 2 -> g
            |NOT x -> h
            |NOT y -> a""".trimMargin()
            )
        )
    }

    @Test
    fun star1() {
        Assertions.assertEquals(956, Day7.star1(null))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(40149, Day7.star2(null))
    }
}