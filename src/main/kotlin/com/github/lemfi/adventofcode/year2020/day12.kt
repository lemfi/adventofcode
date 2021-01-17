package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    Day12.star1(Day12.data)
        .apply { println("res: $this") }

    Day12.star2(Day12.data)
        .apply { println("res: $this") }

}

object Day12 {

    fun star1(data: String): Int {

        return data.lines()
            .map { it.subSequence(0..0) to it.substring(1).toInt() }
            .fold(Nav1()) {
                    res, line -> res.apply { move(line.first.toString(), line.second) }
            }.let {
                abs(it.ew) + abs(it.ns)
            }
    }

    fun star2(data: String): Int {
        return  data.lines()
            .map { it.subSequence(0..0) to it.substring(1).toInt() }
            .fold(Nav2()) {
                    res, line -> res.apply { command(line.first.toString(), line.second) }
            }.let {
                it.north + it.east + it.west + it.south
            }
    }

    data class Nav1(
        var direction: String = "E",
        var ns: Int = 0,
        var ew: Int = 0,
    ) {
        fun move(dir: String, value: Int) {
            when (dir) {
                "S" -> ns += value
                "N" -> ns -= value
                "E" -> ew += value
                "W" -> ew -= value
                "F" -> move(direction, value)
                "L", "R" -> computeDirection(dir, value)
            }
        }

        private fun computeDirection(orientation: String, value: Int) {
            direction = listOf("N", "E", "S", "W", "N", "E", "S", "W").let {
                if (orientation == "R") it.get(it.indexOf(direction) + value / 90)
                else it.get(it.lastIndexOf(direction) - value / 90)
            }
        }
    }

    data class Waypoint(
        var dir: String,
        var value: Int,
    )

    data class Nav2(
        var waypoints: List<Waypoint> = listOf(Waypoint("E", 10), Waypoint("S", 0), Waypoint("W", 0), Waypoint("N", 1)),
        var north: Int = 0,
        var south: Int = 0,
        var east: Int = 0,
        var west: Int = 0,
    ) {
        fun command(dir: String, value: Int): Nav2 {
            when (dir) {
                "S", "N", "E", "W" -> waypoints.first { it.dir == dir }.value += value
                "F" -> move(value)
                "L", "R" -> computeDirection(dir, value)

            }
            return this
        }

        private fun move(value: Int) {
            waypoints.forEach {
                    waypoint -> when(waypoint.dir) {
                "S" -> {
                    south += max(waypoint.value * value - north, 0)
                    north -= min(waypoint.value * value, north)
                }
                "N" -> {
                    north += max(waypoint.value * value - south, 0)
                    south -= min(waypoint.value * value, south)
                }
                "E" -> {
                    east +=  max(waypoint.value * value - west, 0)
                    west -=  min(waypoint.value * value, west)
                }
                "W" -> {
                    west += max(waypoint.value * value - east, 0)
                    east -= min(waypoint.value * value, east)
                }
            }
            }
        }

        private fun computeDirection(orientation: String, value: Int) {
            waypoints = waypoints.fold(mutableListOf()) { res, way ->
                res.apply {
                    add(
                        Waypoint(
                            listOf("N", "E", "S", "W", "N", "E", "S", "W").let {
                                if (orientation == "R") it.get(it.indexOf(way.dir) + value / 90)
                                else it.get(it.lastIndexOf(way.dir) - value / 90)
                            }, waypoints.first { it.dir == way.dir }.value)
                    )
                }
            }
        }
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day12.txt").readAllBytes().toString(Charset.defaultCharset()) }
}