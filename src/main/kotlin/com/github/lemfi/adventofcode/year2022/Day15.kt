package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs
import kotlin.math.max

object Day15 {

    private fun String?.toData() = this ?: data(15)

    fun star1(input: String?) = resolve1(input, 2000000)

    fun resolve1(input: String?, y: Int) = input
        .parse()
        .fold(mutableSetOf<Int>()) { noBeaconPosition, (sensor, beacon) ->
            noBeaconPosition.apply {
                val restDistance = max(0, (sensor distanceFrom beacon) - abs(sensor.y - y))
                addAll(sensor.x - restDistance..sensor.x + restDistance)
                if (beacon.y == y) {
                    remove(beacon.x)
                }
            }
        }.size

    fun star2(input: String?) = resolve2(input, 4000000)

    fun resolve2(input: String?, maxValue: Int) = input
        .parse()
        .let { sensorsAndBeacons ->

            var position: Position? = null
            var y = 0
            while (position == null && y < maxValue) {
                val possibilities = sensorsAndBeacons
                    .filterNot { (sensor, beacon) -> sensor distanceFrom beacon < abs(sensor.y - y) } // remove sensors that are already too far
                    .fold(listOf(0..maxValue)) { beaconPositionPossibilities, (sensor, beacon) ->

                        val restDistance = max(0, (sensor distanceFrom beacon) - abs(sensor.y - y))
                        beaconPositionPossibilities subtract sensor.x - restDistance..sensor.x + restDistance

                    }
                if (possibilities.size() == 1) position = Position(possibilities.first(), y)
                y++
            }
            position
        }?.let { distressBeacon ->
            distressBeacon.x.toLong() * 4000000 + distressBeacon.y
        } ?: error("I could not find the beacon :'(")


    private fun List<IntRange>.size() = sumOf { it.last - it.first + 1 }
    private fun List<IntRange>.first() = this[0].first

    private infix fun List<IntRange>.subtract(m: IntRange): List<IntRange> = flatMap { it subtract m }
    private infix fun IntRange.subtract(m: IntRange): List<IntRange> =

        when {
            m.last < first || m.first > last -> listOf(this)
            m.first <= first && m.last >= last -> emptyList()
            m.first > first && m.last < last -> listOf(first until m.first, m.last + 1..last)
            m.first <= first -> listOf((m.last + 1)..last)
            else -> listOf(first until m.first)
        }

    private fun String?.parse() = toData()
        .lines()
        .map { line ->
            Pair(
                first = line
                    .substringAfter("Sensor at ")
                    .substringBefore(": closest beacon is at ")
                    .parsePosition(),
                second = line
                    .substringAfter(": closest beacon is at ")
                    .parsePosition()
            )
        }

    private fun String.parsePosition() = split(", ")
        .let { (x, y) -> Position(x.substringAfter("=").toInt(), y.substringAfter("=").toInt()) }

    private infix fun Position.distanceFrom(position: Position) = abs(x - position.x) + abs(y - position.y)

    private data class Position(val x: Int, val y: Int)
}

fun main() {
    processStars(Day15::star1, Day15::star2)
}