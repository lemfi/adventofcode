package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day4Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            2, Day4.star1(
                """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            4, Day4.star2(
                """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"""
            )
        )
    }
}