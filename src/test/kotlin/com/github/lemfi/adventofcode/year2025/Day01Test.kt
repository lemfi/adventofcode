package com.github.lemfi.adventofcode.year2025

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun star1() {
        Assertions.assertEquals(3, Day01.star1("""L68
L30
R48
L5
R60
L55
L1
L99
R14
L82"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(6, Day01.star2("""L68
L30
R48
L5
R60
L55
L1
L99
R14
L82"""))
    }
}