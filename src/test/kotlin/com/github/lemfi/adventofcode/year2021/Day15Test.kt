package com.github.lemfi.adventofcode.year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day15Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            40, Day15.star1(
                """1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            315, Day15.star2(
                """1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581"""
            )
        )
    }
}