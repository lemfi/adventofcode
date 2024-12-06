package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4
import com.github.lemfi.adventofcode.stuff.ReadMap.toMap

object Day06 {

    private fun String?.toData() = this ?: data(6)

    fun star1(input: String?) = input
        .toData()
        .toMap { it, _, _ -> "$it" }
        .observeGuard()
        .size


    fun star2(input: String?) = input
        .toData()
        .toMap { it, _, _ -> "$it" }
        .let { map ->
            map.observeGuard()
                .filterNot { map[it] == "^" }
                .filter {
                    isInLoop(map.toMutableMap().apply { this[it] = "#" })
                }
                .size
        }

    private fun Map<Coordinate, String>.observeGuard(): MutableSet<Coordinate> {
        val visited = mutableSetOf<Coordinate>()
        var (guard, direction) = entries.first { it.value == "^" }.key to Direction4.UP

        visited += guard
        do {
            val next = this[guard.to(direction)]

            when (next) {
                "#" -> direction = direction.toRight()
                ".", "^" -> {
                    guard = guard.to(direction)
                    visited += guard
                }
            }
        } while (next != null)

        return visited
    }

    private fun isInLoop(map: Map<Coordinate, String>): Boolean {

        var (guard, direction) = map.entries.first { it.value == "^" }.key to Direction4.UP
        val visited = mutableSetOf(guard to direction)
        var isLooping = false

        do {
            val next = map[guard.to(direction)]

            when (next) {
                "#" -> direction = direction.toRight()
                ".", "^" -> {
                    guard = guard.to(direction)
                    isLooping = !visited.add(guard to direction)
                }
            }
        } while (next != null && !isLooping)

        return isLooping
    }
}

fun main() {
    processStars(Day06::star1, Day06::star2)
}
