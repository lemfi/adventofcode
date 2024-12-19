package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day19Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            6, Day19.star1(
                """r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            16, Day19.star2(
                """r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb"""
            )
        )
    }
}