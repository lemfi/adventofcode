package com.github.lemfi.adventofcode.year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day17Test {

    @Test
    fun star1() {
        Assertions.assertEquals(45, Day17.star1("target area: x=20..30, y=-10..-5"))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(112, Day17.star2("target area: x=20..30, y=-10..-5"))
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(11175, Day17.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(3540, Day17.star2(null))
    }
}