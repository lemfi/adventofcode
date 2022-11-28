package com.github.lemfi.adventofcode.year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            150, Day2.star1(
                """forward 5
down 5
forward 8
up 3
down 8
forward 2"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            900, Day2.star2(
                """forward 5
down 5
forward 8
up 3
down 8
forward 2"""
            )
        )
    }

    @Test
    fun puzzle1() =
        Assertions.assertEquals(1451208, Day2.star1(null))

    @Test
    fun puzzle2() =
        Assertions.assertEquals(1620141160, Day2.star2(null))
}