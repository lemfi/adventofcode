package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day21.Day21
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day21Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            739785, Day21.star1(
                """Player 1 starting position: 4
Player 2 starting position: 8"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            444356092776315L, Day21.star2(
                """Player 1 starting position: 4
Player 2 starting position: 8"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(1196172, Day21.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(106768284484217L, Day21.star2(null))
    }
}