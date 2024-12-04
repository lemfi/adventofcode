package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction8
import com.github.lemfi.adventofcode.stuff.ReadMap.readMap
import com.github.lemfi.adventofcode.stuff.ReadMap.toMap

object Day04 {

    private fun String?.toData() = this ?: data(4)

    fun star1(input: String?) = input
        .toData()
        .toMap<Char>()
        .let { map ->
            map.entries
                .filter { it.value == 'X' }
                .sumOf { (coordinates, _) ->
                    Direction8.entries.toTypedArray().count { direction ->
                        map.readMap(coordinates, 4, direction).joinToString("") == "XMAS"
                    }
                }
        }

    fun star2(input: String?) = input
        .toData()
        .toMap<Char>()
        .let { map ->
            map.entries
                .filter { it.value == 'A' }
                .count { (coordinates, _) ->
                    map.isMasFrom(coordinates.to(Direction8.DOWN_RIGHT), Direction8.UP_LEFT)
                            && map.isMasFrom(coordinates.to(Direction8.UP_RIGHT), Direction8.DOWN_LEFT)
                }
        }

    private fun Map<Coordinate, Char>.isMasFrom(
        coordinates: Coordinate,
        direction: Direction8,
    ) = readMap(coordinates, 3, direction).joinToString("").let {
        it == "MAS" || it == "SAM"
    }
}

fun main() {
    processStars(Day04::star1, Day04::star2)
}
