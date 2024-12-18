package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day17Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            "4,6,3,5,6,3,5,2,1,0", Day17.star1(
                """Register A: 729
Register B: 0
Register C: 0

Program: 0,1,5,4,3,0"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            117440, Day17.star2(
                """Register A: 2024
Register B: 0
Register C: 0

Program: 0,3,5,4,3,0"""
            )
        )
    }
}