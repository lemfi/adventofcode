package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day1.Day1
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            7, Day1.star1(
                """199
200
208
210
200
207
240
269
260
263"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            5, Day1.star2(
                """199
200
208
210
200
207
240
269
260
263"""
            )
        )
    }
}