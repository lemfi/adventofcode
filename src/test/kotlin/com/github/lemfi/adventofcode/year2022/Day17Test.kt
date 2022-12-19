package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day17Test {

    @Test
    fun star1() {
        Assertions.assertEquals(3068, Day17.star1(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(1514285714288L, Day17.star2(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"))
    }
}