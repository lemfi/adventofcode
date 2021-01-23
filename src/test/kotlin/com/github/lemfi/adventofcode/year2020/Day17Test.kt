package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day17Test {

    @Test
    fun neighboor() {

        val expected = setOf(
                Day17.Coordinates.`3DCoordinates`(-1, 0, 1),
                Day17.Coordinates.`3DCoordinates`(0, 0, 1),
                Day17.Coordinates.`3DCoordinates`(1, 0, 1),
                Day17.Coordinates.`3DCoordinates`(-1, -1, 1),
                Day17.Coordinates.`3DCoordinates`(0, -1, 1),
                Day17.Coordinates.`3DCoordinates`(1, -1, 1),
                Day17.Coordinates.`3DCoordinates`(-1, 1, 1),
                Day17.Coordinates.`3DCoordinates`(0, 1, 1),
                Day17.Coordinates.`3DCoordinates`(1, 1, 1),
                Day17.Coordinates.`3DCoordinates`(-1, 0, 0),
                Day17.Coordinates.`3DCoordinates`(1, 0, 0),
                Day17.Coordinates.`3DCoordinates`(-1, -1, 0),
                Day17.Coordinates.`3DCoordinates`(0, -1, 0),
                Day17.Coordinates.`3DCoordinates`(1, -1, 0),
                Day17.Coordinates.`3DCoordinates`(-1, 1, 0),
                Day17.Coordinates.`3DCoordinates`(0, 1, 0),
                Day17.Coordinates.`3DCoordinates`(1, 1, 0),
                Day17.Coordinates.`3DCoordinates`(-1, 0, -1),
                Day17.Coordinates.`3DCoordinates`(0, 0, -1),
                Day17.Coordinates.`3DCoordinates`(1, 0, -1),
                Day17.Coordinates.`3DCoordinates`(-1, -1, -1),
                Day17.Coordinates.`3DCoordinates`(0, -1, -1),
                Day17.Coordinates.`3DCoordinates`(1, -1, -1),
                Day17.Coordinates.`3DCoordinates`(-1, 1, -1),
                Day17.Coordinates.`3DCoordinates`(0, 1, -1),
                Day17.Coordinates.`3DCoordinates`(1, 1, -1)
        )

        val actual = Day17.Coordinates.`3DCoordinates`(0, 0, 0).neighboors().toSet()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun sample1() {

        Assertions.assertEquals(112, Day17.star1(""".#.
..#
###"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(848, Day17.star2(""".#.
..#
###"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(359, Day17.star1(Day17.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(2228, Day17.star2(Day17.data))
    }

}
