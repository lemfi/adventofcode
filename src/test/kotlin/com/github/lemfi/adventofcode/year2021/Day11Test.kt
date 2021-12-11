package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day11.Day11
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            1656, Day11.star1(
                """5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            195, Day11.star2(
                """5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(1571, Day11.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(387, Day11.star2(null))
    }
}