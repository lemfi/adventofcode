package com.github.lemfi.adventofcode.year2025

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.ReadMap.toMap

object Day04 {

    private fun String?.toData() = this ?: data(4)

    fun star1(input: String?) = input.toData().toMap<Char>().removableRolls().size

    fun star2(input: String?) = input.toData().toMap<Char>().let { map ->

        var removed = 0
        var cleaned = map
        do {
            val (cleanedMap, size) = cleaned.removeRemovableRolls()
            cleaned = cleanedMap
            removed += size
        } while (size > 0)
        removed
    }

    private fun Map<Coordinate, Char>.removeRemovableRolls() = removableRolls().let { toRemove ->
        entries.associate { (coordinate, s) -> if (toRemove.containsKey(coordinate)) coordinate to '.' else coordinate to s } to toRemove.size
    }

    private fun Map<Coordinate, Char>.removableRolls() = filterKeys { coordinate ->
        this[coordinate] == '@' && coordinate.neighbors8().count { this[it] == '@' } < 4
    }
}

fun main() {
    processStars(Day04::star1, Day04::star2)
}
