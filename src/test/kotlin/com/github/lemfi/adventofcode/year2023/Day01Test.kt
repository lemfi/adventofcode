package com.github.lemfi.adventofcode.year2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            142, Day01.star1(
                """1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            281, Day01.star2(
                """two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen"""
            )
        )
    }
}