package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day14 {

    private fun String?.toData() = this ?: data(14)

    fun star1(input: String?) = input
        .buildCave()
        .let { cave ->

            var fallenSand = 0
            while (sand fallFrom cave != abyss) {
                fallenSand ++
            }
            fallenSand
        }

    fun star2(input: String?) = input
        .buildCave(floor = 2)
        .let { cave ->
            var fallenSand = 0

            do {
                fallenSand ++
            } while (sand != sand fallFrom cave)

            fallenSand
        }

    private val sand = Position(500, 0)
    private val abyss: Position? = null
    private infix fun Position.fallFrom(cave: Cave): Position? = cave.fall(this)

    data class Position(val x: Int, val y: Int)

    private class Cave(positions: List<Position>, private val floor: Int?) {

        private val map = positions
            .groupBy { it.x }.toMap()
            .map { (x, p) -> x to p.groupBy { it.y }.map { (y, _) -> y to "#" }.toMap().toMutableMap() }.toMap()
            .toMutableMap()

        private val ys = positions.minOf { it.y } .. positions.maxOf { it.y } + (floor ?: 0)

        fun fall(position: Position): Position? = when {
            position.isOutFromCave() -> abyss
            position.isAvailable() && !position.below().isAvailable() -> {
                when {
                    position.below().left().isAvailable() -> fall(position.below().left())
                    position.below().right().isAvailable() -> fall(position.below().right())
                    else -> position.apply { fillCave() }
                }
            }
            else -> fall(position.below())
        }

        private fun Position.fillCave() = map.getOrPut(x) { mutableMapOf() }.put(y, "0")

        private fun Position.isOutFromCave() = y > ys.last
        private fun Position.isAvailable() = runCatching { map[x]!![y] }.getOrNull() == null && (floor == null || y != ys.last)
        private fun Position.below() = Position(x, y + 1)
        private fun Position.right() = Position(x + 1, y)
        private fun Position.left() = Position(x - 1, y)
    }

    private fun String?.buildCave(floor: Int? = null) = Cave(
        positions = toData()
            .lines()
            .flatMap { line ->
                line
                    .split(" -> ")
                    .windowed(2, 1)
                    .flatMap { (start, end) ->
                        start
                            .split(",")
                            .map { it.toInt() }
                            .let { (xstart, ystart) ->
                                end
                                    .split(",")
                                    .map { it.toInt() }
                                    .let { (xend, yend) ->
                                        (minOf(xstart, xend)..maxOf(xstart, xend)).flatMap { x ->
                                            (minOf(ystart, yend)..maxOf(ystart, yend)).map { y ->
                                                Position(x, y)
                                            }
                                        }
                                    }
                            }
                    }
            },
        floor = floor,
    )
}

fun main() {
    processStars(Day14::star1, Day14::star2)
}