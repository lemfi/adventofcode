package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day24Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            18, Day24.star1(
                """#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            54, Day24.star2(
                """#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#"""
            )
        )
    }
}