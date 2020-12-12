package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(25, day12_1("""F10
N3
F7
R90
F11"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(286, day12_2("""F10
N3
F7
R90
F11"""))
    }

    @Test
    fun `waypoint changes as expected`() {
        val nav = Nav2()
        nav.command("N", 3).let {
            Assertions.assertEquals(
                    setOf(Waypoint("E", 10), Waypoint("N", 4), Waypoint("S", 0), Waypoint("W", 0)),
                    it.waypoints.toSet()
            )
        }

        nav.command("R", 90).let {
            Assertions.assertEquals(
                    setOf(Waypoint("E", 4), Waypoint("N", 0), Waypoint("S", 10), Waypoint("W", 0)),
                    it.waypoints.toSet()
            )
        }

        nav.command("L", 90).let {
            Assertions.assertEquals(
                    setOf(Waypoint("E", 10), Waypoint("N", 4), Waypoint("S", 0), Waypoint("W", 0)),
                    it.waypoints.toSet()
            )
        }

        nav.command("R", 180).let {
            Assertions.assertEquals(
                    setOf(Waypoint("W", 10), Waypoint("S", 4), Waypoint("N", 0), Waypoint("E", 0)),
                    it.waypoints.toSet()
            )
        }

        nav.command("L", 180).let {
            Assertions.assertEquals(
                    setOf(Waypoint("E", 10), Waypoint("N", 4), Waypoint("S", 0), Waypoint("W", 0)),
                    it.waypoints.toSet()
            )
        }

        nav.command("R", 270).let {
            Assertions.assertEquals(
                    setOf(Waypoint("N", 10), Waypoint("W", 4), Waypoint("E", 0), Waypoint("S", 0)),
                    it.waypoints.toSet()
            )
        }


    }
    @Test
    fun `forward changes as expected`() {
        val nav = Nav2()

        nav.command("F", 10).let {
            Assertions.assertEquals(100, it.east)
            Assertions.assertEquals(10, it.north)
        }

        nav.command("N", 3)

        nav.command("F", 7).let {
            Assertions.assertEquals(170, it.east)
            Assertions.assertEquals(38, it.north)
        }

    }

    @Test
    fun real1() {
        Assertions.assertEquals(1838, day12_1(day12data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(89936, day12_2(day12data))
    }

}