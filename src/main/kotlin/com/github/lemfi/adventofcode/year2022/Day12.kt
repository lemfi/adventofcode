package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import kotlin.math.min

object Day12 {

    private fun String?.toData() = this ?: data(12)

    fun star1(input: String?) = input
        .toData()
        .prepareData()
        .computeMinDistance(Int.MAX_VALUE)

    fun star2(input: String?) = input
        .toData()
        .replace("S", "a")
        .prepareData()
        .expandStartingPoints()
        .fold(Int.MAX_VALUE) { minDistance, positions ->
            min(minDistance, positions.computeMinDistance(minDistance))
        }


    private fun List<Position>.computeMinDistance(
        minDistance: Int
    ): Int {
        val grid = toMutableList()
        val map = grid.toMap()
        val lastPoint = grid.first { it is Stop }

        val pending = mutableSetOf<Position>()

        var point = grid.first { it is Start }

        var distance = 0
        while (point != lastPoint && distance < minDistance) {
            map
                .visitableNeighbors(point)
                .onEach { it.distance = min(it.distance, distance + 1) }
                .let { visitableNeighbors -> pending.addAll(visitableNeighbors) }

            point = (runCatching { pending.minBy { it.distance } }.getOrNull() ?: grid.first())
                .apply { visited = true }
                .also { nextPoint ->
                    distance = nextPoint.distance

                    pending.remove(nextPoint)
                    grid.remove(nextPoint)
                }
        }
        return distance
    }

    private fun List<Position>.toMap() = groupBy { it.x }
        .map { (x, v) -> x to v.groupBy { it.y }
            .map { (y, v) -> y to v.first() }.toMap()
        }.toMap()

    private fun List<Position>.expandStartingPoints() =
        let { initial ->
            initial
                .filter { it.level == 0 }
                .map { newStart ->
                    initial.map {
                        when (it) {
                            is Path -> if (it.x == newStart.x && it.y == newStart.y)
                                Start(
                                    x = it.x,
                                    y = it.y,
                                    level = 0,
                                ) else it.copy()

                            is Stop -> it.copy()
                            else -> error("no Start here")
                        }
                    }
                }
        }

    private fun Map<Int, Map<Int, Position>>.visitableNeighbors(position: Position) =
        listOfNotNull(
            runCatching { this[position.x]!![position.y - 1] }.getOrNull(),
            runCatching { this[position.x]!![position.y + 1] }.getOrNull(),
            runCatching { this[position.x - 1]!![position.y] }.getOrNull(),
            runCatching { this[position.x + 1]!![position.y] }.getOrNull(),
        ).filter { it.level <= position.level + 1 && !it.visited}



    private fun String.prepareData() = lines()
        .flatMapIndexed { y, line ->
            line.mapIndexed { x, it ->
                when (it) {
                    'S' -> Start(x, y, 0)
                    'E' -> Stop(x, y, 25)
                    else -> Path(x, y, alphabet.indexOf(it))
                }
            }
        }

    private sealed interface Position {
        val x: Int
        val y: Int
        val level: Int
        var distance: Int
        var visited: Boolean
    }

    private data class Path(
        override val x: Int, override val y: Int, override val level: Int,
    ) : Position {
        override var distance: Int = Int.MAX_VALUE
        override var visited: Boolean = false

        override fun toString() = "($x, $y, $distance)"
    }

    private data class Start(
        override val x: Int, override val y: Int, override val level: Int,
    ) : Position {
        override var distance: Int = 0
        override var visited: Boolean = true
    }

    private data class Stop(
        override val x: Int, override val y: Int, override val level: Int,
    ) : Position  {
        override var distance: Int = Int.MAX_VALUE
        override var visited: Boolean = false
    }

    private val alphabet = listOf(
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z'
    )
}

fun main() {
    processStars(Day12::star1, Day12::star2)
}