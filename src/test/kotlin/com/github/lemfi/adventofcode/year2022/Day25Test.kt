package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day25Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            "2=-1=0", Day25.star1(
                """1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(0, Day25.star2(null))
    }
}