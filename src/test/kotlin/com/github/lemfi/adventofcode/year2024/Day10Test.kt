package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day10Test {

    @Test
    fun star11() {
        Assertions.assertEquals(
            1, Day10.star1(
                """0123
1234
8765
9876"""
            )
        )
    }

    @Test
    fun star12() {
        Assertions.assertEquals(
            36, Day10.star1(
                """89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            81, Day10.star2(
                """89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732"""
            )
        )
    }
}