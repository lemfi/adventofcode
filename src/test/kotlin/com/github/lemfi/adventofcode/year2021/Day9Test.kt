package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day9.Day9
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            15, Day9.star1(
                """2199943210
3987894921
9856789892
8767896789
9899965678"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            1134, Day9.star2(
                """2199943210
3987894921
9856789892
8767896789
9899965678"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(496, Day9.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(902880, Day9.star2(null))
    }
}