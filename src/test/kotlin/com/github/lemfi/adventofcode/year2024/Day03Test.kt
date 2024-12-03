package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            161,
            Day03.star1("""xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""")
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            48,
            Day03.star2(
                """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
"""
            )
        )
    }
}