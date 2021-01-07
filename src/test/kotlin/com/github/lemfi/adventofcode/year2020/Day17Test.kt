package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day17Test {

    @Test
    fun neighboor() {

        val expected = setOf(
                `3DCoordinates`(-1, 0, 1),
                `3DCoordinates`(0, 0, 1),
                `3DCoordinates`(1, 0, 1),
                `3DCoordinates`(-1, -1, 1),
                `3DCoordinates`(0, -1, 1),
                `3DCoordinates`(1, -1, 1),
                `3DCoordinates`(-1, 1, 1),
                `3DCoordinates`(0, 1, 1),
                `3DCoordinates`(1, 1, 1),
                `3DCoordinates`(-1, 0, 0),
                `3DCoordinates`(1, 0, 0),
                `3DCoordinates`(-1, -1, 0),
                `3DCoordinates`(0, -1, 0),
                `3DCoordinates`(1, -1, 0),
                `3DCoordinates`(-1, 1, 0),
                `3DCoordinates`(0, 1, 0),
                `3DCoordinates`(1, 1, 0),
                `3DCoordinates`(-1, 0, -1),
                `3DCoordinates`(0, 0, -1),
                `3DCoordinates`(1, 0, -1),
                `3DCoordinates`(-1, -1, -1),
                `3DCoordinates`(0, -1, -1),
                `3DCoordinates`(1, -1, -1),
                `3DCoordinates`(-1, 1, -1),
                `3DCoordinates`(0, 1, -1),
                `3DCoordinates`(1, 1, -1)
        )

        val actual = `3DCoordinates`(0, 0, 0).neighboors().toSet()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun sample1() {

        Assertions.assertEquals(112, day17_1(""".#.
..#
###"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(848, day17_2(""".#.
..#
###"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(359, day17_1(day17data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(2228, day17_2(day17data))
    }

}
