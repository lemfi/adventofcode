package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            2660, Day14.star1(
                """
                    Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                    Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                    """.trimIndent()
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            1564, Day14.star2(
                """
                    Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                    Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                    """.trimIndent()
            )
        )
    }
}