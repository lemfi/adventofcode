package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day5.Day5
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            5, Day5.star1(
                """0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            12, Day5.star2(
                """0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"""
            )

        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(4745, Day5.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(18442, Day5.star2(null))
    }
}