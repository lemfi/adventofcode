package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            64, Day18.star1(
                """2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            58, Day18.star2(
                """2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5"""
            )
        )
    }

    @Test
    fun testPerso() {
        Assertions.assertEquals(
            96, Day18.star2(
                """1,1,1
1,1,2
1,1,3
1,1,4
1,2,1
1,2,2
1,2,3
1,2,4
1,3,1
1,3,2
1,3,3
1,3,4
1,4,1
1,4,2
1,4,3
1,4,4
2,1,1
2,1,2
2,1,3
2,1,4
2,2,1
2,2,2
2,2,3
2,2,4
2,3,1
2,3,2
2,3,3
2,3,4
2,4,1
2,4,2
2,4,3
2,4,4
3,1,1
3,1,2
3,1,3
3,1,4
3,2,1
3,2,2
3,2,3
3,2,4
3,3,1
3,3,2
3,3,3
3,3,4
3,4,1
3,4,2
3,4,3
3,4,4
4,1,1
4,1,2
4,1,3
4,1,4
4,2,1
4,2,2
4,2,3
4,2,4
4,3,1
4,3,2
4,3,3
4,3,4
4,4,1
4,4,2
4,4,3
4,4,4"""
            )
        )
    }
}