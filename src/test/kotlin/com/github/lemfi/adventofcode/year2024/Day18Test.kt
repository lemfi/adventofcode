package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            22, Day18.star1(
                """5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            "6,1", Day18.star2(
                """5,4
4,2
4,5
3,0
2,1
6,3
2,4
1,5
0,6
3,3
2,6
5,1
1,2
5,5
2,5
6,5
1,4
0,4
6,4
1,1
6,1
1,0
0,5
1,6
2,0"""
            )
        )
    }
}