package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4
import com.github.lemfi.adventofcode.stuff.ReadMap.toMap

object Day12 {

    private fun String?.toData() = this ?: data(12)

    fun star1(input: String?) = input
        .parse()
        .solve { perimeter(it) }

    fun star2(input: String?) = input
        .parse()
        .solve { edges(it) }

    private fun String?.parse() = toData().toMap { it, _, _ -> "$it" }

    private fun Map<Coordinate, String>.solve(solver: Map<Coordinate, String>.(Collection<Coordinate>) -> Long): Long =
        toGroups().sumOf { it.size * solver(it) }

    private fun Map<Coordinate, String>.toGroups(): MutableList<Collection<Coordinate>> {
        val coordinates = keys.toMutableList()
        val groups = mutableListOf<Collection<Coordinate>>()
        while (coordinates.isNotEmpty()) {
            val coordinate = coordinates.removeFirst()
            extractGroup(coordinate).also {
                groups.add(it)
                coordinates.removeAll(it)
            }
        }
        return groups
    }

    private fun Map<Coordinate, String>.extractGroup(coordinate: Coordinate): Collection<Coordinate> {
        val plant = this[coordinate]!!
        val possibilities = mutableListOf(*coordinate.neighbors4().toTypedArray())
        val group = mutableSetOf(coordinate)
        while (possibilities.isNotEmpty()) {
            val next = possibilities.removeFirst()
            if (this[next] == plant && group.add(next)) {
                possibilities.add(next)
                possibilities.addAll(next.neighbors4())
            }
        }
        return group
    }

    private fun Map<Coordinate, String>.perimeter(coords: Collection<Coordinate>) =
        this[coords.first()]!!.let { plant ->
            coords.sumOf { coordinate ->
                coordinate.neighbors4()
                    .count { this[it] != plant }
                    .toLong()
            }
        }

    private fun edges(coords: Collection<Coordinate>): Long =
        coords.map {
            setOf(it, it.to(Direction4.RIGHT), it.to(Direction4.DOWN), it.to(Direction4.DOWN).to(Direction4.RIGHT))
        }.let { plantsVertices ->
            plantsVertices
                .flatten()
                .toSet()
                .sumOf { coordinate ->
                    when {
                        plantsVertices.count { it.contains(coordinate) } % 2 != 0 -> 1L // impair = 1 ou 3 = sommet
                        plantsVertices.count { it.contains(coordinate) } == 2 ->

                            // présente deux fois mais aucun côté adjacent
                            // = sommet qui touche en coin deux zones,
                            // donc on le compte deux fois, une fois pour chaque zone
                            // merci les tests je m'en serais jamais sortie ^^ :scream:

                            when {
                                plantsVertices
                                    .filter { it.contains(coordinate) }
                                    .reduce { v1, v2 -> v1 intersect v2 }
                                    .size == 1 -> 2

                                else -> 0
                            }

                        else -> 0
                    }
                }
        }

}

fun main() {
    processStars(Day12::star1, Day12::star2)
}
