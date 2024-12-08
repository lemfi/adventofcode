package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.ReadMap.toMap

object Day08 {

    private fun String?.toData() = this ?: data(8)

    fun star1(input: String?) = input
        .solve { antennas, _ ->
            antennas.flatMap { antenna ->
                antennas.toMutableList().apply { remove(antenna) }.map {
                    Coordinate(it.x - distance(antenna, it).first, it.y - distance(antenna, it).second)
                }
            }
        }

    fun star2(input: String?) = input
        .solve { antennas, map ->
            antennas.flatMap { antenna ->
                antennas.toMutableList()
                    .apply { remove(antenna) }
                    .flatMap { other ->
                        distance(antenna, other).let { distance ->
                            var next = Coordinate(other.x - distance.first, other.y - distance.second)
                            val res = mutableListOf(antenna)
                            while (map.containsKey(next)) {
                                res.add(next)
                                next = Coordinate(next.x - distance.first, next.y - distance.second)
                            }
                            res
                        }
                    }
            }
        }

    private fun String?.solve(solver: (List<Coordinate>, Map<Coordinate, String>) -> List<Coordinate>) = toData()
        .toMap { it, _, _ -> it.toString() }
        .let { map ->
            map.entries
                .filter { it.value != "." }
                .groupBy { it.value }
                .map { it.value.map { entry -> entry.key } }
                .flatMap { antennas ->
                    solver(antennas, map)
                }
                .toSet()
                .filter { map.containsKey(it) }
                .size
        }

    private fun distance(c1: Coordinate, c2: Coordinate) = c1.x - c2.x to c1.y - c2.y
}

fun main() {
    processStars(Day08::star1, Day08::star2)
}
