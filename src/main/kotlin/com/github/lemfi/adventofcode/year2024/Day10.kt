package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4
import com.github.lemfi.adventofcode.stuff.ReadMap.toMap

object Day10 {

    private fun String?.toData() = this ?: data(10)

    fun star1(input: String?) = input.solve { mutableSetOf() }
    fun star2(input: String?) = input.solve { mutableListOf() }

    private fun String?.solve(valid: () -> MutableCollection<Coordinate>) =
        toData()
            .toMap { it, _, _ -> it.digitToInt() }
            .let { map ->
                map
                    .filter { it.value == 0 }
                    .map { it.key }
                    .sumOf { it.countValidPaths(map, valid()) }
            }

    private fun Coordinate.countValidPaths(map: Map<Coordinate, Int>, valid: MutableCollection<Coordinate>): Int {
        val toExplore = mutableListOf(this)
        do {
            val current = toExplore.removeAt(toExplore.lastIndex)
            if (map[current] == 9) valid.add(current)
            else toExplore.addAll(
                Direction4
                    .entries
                    .map { current.to(it) }
                    .filter { next -> map[next] == map[current]!! + 1 }
            )

        } while (toExplore.isNotEmpty())
        return valid.size
    }
}

fun main() {
    processStars(Day10::star1, Day10::star2)
}