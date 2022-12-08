package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day8Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            21, Day8.star1(
                """30373
25512
65332
33549
35390"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            8, Day8.star2(
                """30373
25512
65332
33549
35390"""
            )
        )
    }
}