package com.github.lemfi.adventofcode.year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day25Test {

    @Test
    fun star11() {
        Assertions.assertEquals(
            58, Day25.star1(
                """v...>>.vv>
.vv>>.vv..
>>.>v>...v
>>v>>.>.v.
v>v.vv.v..
>.>>..v...
.vv..>.>v.
v.v..>>v.v
....v..v.>"""
            )
        )
    }

    @Test
    fun star12() {
        Assertions.assertEquals(582, Day25.star1(null))
    }

}