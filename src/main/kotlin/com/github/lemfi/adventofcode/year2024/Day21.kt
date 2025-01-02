package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4
import kotlin.math.min

private typealias Path = Map<String, Long>

private fun Path.hit() = map { it.key.length * it.value }.sum()

object Day21 {

    private fun String?.toData() = this ?: data(21)

    fun star1(input: String?) = input.toData().lines()
        .sumOf { code ->
            code.removeSuffix("A").toInt() * code
                .getShortestPath(numPad)
                .getShortestPath(2)
        }

    fun star2(input: String?) = input
        .toData().lines()
        .sumOf { code ->
            code.removeSuffix("A").toInt() * code
                .getShortestPath(numPad)
                .getShortestPath(25)
        }

    private val cache = mutableMapOf<Pair<Collection<Path>, Int>, Long>()
    private fun Collection<Path>.getShortestPath(depth: Int): Long {
        return when {
            cache.containsKey(this to depth) -> cache[this to depth]!!
            depth == 0 -> minOf { it.hit() }
            else ->
                minOf { path ->
                    path.entries.sumOf { (keys, occurrences) ->
                        keys.getShortestPath(keyPad).getShortestPath(depth - 1) * occurrences
                    }
                }.also { cache[this to depth] = it }
        }
    }

    private val keyPadCache = mutableMapOf<String, Set<Map<String, Long>>>()
    private fun String.getShortestPath(pad: Map<Coordinate, String>): Collection<Path> =
        keyPadCache[this] ?: "A$this"
            .windowed(2)
            .fold(emptyList<String>()) { acc, win ->
                (win.first().toString() to win.last().toString())
                    .getShortestPath(pad)
                    .map {
                        it.joinToString("") {
                            when (it) {
                                Direction4.UP -> "^"
                                Direction4.DOWN -> "v"
                                Direction4.LEFT -> "<"
                                Direction4.RIGHT -> ">"
                            }
                        }
                    }
                    .let { p -> if (acc.isNotEmpty()) acc.flatMap { acc -> p.map { "$acc${it}A" } } else p.map { "${it}A" } }
            }
            .map {
                it.split("A")
                    .map { it + "A" }
                    .dropLast(1)
                    .groupBy { it }
                    .mapValues { it.value.size * 1L }
            }
            .toSet()
            .also {
                keyPadCache[this] = it
            }

    private fun Pair<String, String>.getShortestPath(pad: Map<Coordinate, String>): List<List<Direction4>> {

        val map = pad.map { it.key to (it.value to Int.MAX_VALUE) }.toMap().toMutableMap()
        val from = map.filter { it.value.first == first }.keys.first()
        val to = map.filter { it.value.first == second }.keys.first()

        val possibilities = mutableListOf(Triple(from, 0, emptyList<Direction4>()))
        val found = mutableListOf(listOf<Direction4>())
        var distance = Int.MAX_VALUE
        while (possibilities.isNotEmpty()) {
            val (c, currentDistance, currentPath) = possibilities.removeFirst()
            if (c == to) {
                found.add(currentPath)
                distance = min(distance, currentDistance)
                continue
            }
            val nextDirections = Direction4.entries
                .map { c.to(it) to it }
                .filter { (coordinate, _) -> map.containsKey(coordinate) && map[coordinate]!!.second >= currentDistance + 1 }
                .onEach { (coordinate, _) -> map[coordinate] = map[coordinate]!!.first to currentDistance + 1 }
                .map { (coordinate, dir) -> Triple(coordinate, map[coordinate]!!.second, currentPath + listOf(dir)) }
            possibilities.addAll(nextDirections)
        }
        return found.filter { it.size == distance }
    }

    private val numPad = mapOf(
        Coordinate(0, 0) to "7",
        Coordinate(1, 0) to "8",
        Coordinate(2, 0) to "9",
        Coordinate(0, 1) to "4",
        Coordinate(1, 1) to "5",
        Coordinate(2, 1) to "6",
        Coordinate(0, 2) to "1",
        Coordinate(1, 2) to "2",
        Coordinate(2, 2) to "3",
        Coordinate(1, 3) to "0",
        Coordinate(2, 3) to "A",
    )

    private val keyPad = mapOf(
        Coordinate(1, 0) to "^",
        Coordinate(2, 0) to "A",
        Coordinate(0, 1) to "<",
        Coordinate(1, 1) to "v",
        Coordinate(2, 1) to ">",
    )
}

fun main() {
    processStars(Day21::star1, Day21::star2)
}