package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4
import com.github.lemfi.adventofcode.stuff.ReadMap.toMap
import kotlin.math.abs

object Day20 {

    private fun String?.toData() = this ?: data(20)

    fun star1(input: String?) = input
        .parse()
        .countCheats(
            disableCollisionDuration = 2,
            expectedGain = if (input == null) 100 else 2
        )

    fun star2(input: String?) = input
        .parse()
        .countCheats(
            disableCollisionDuration = 20,
            expectedGain = if (input == null) 100 else 50
        )

    private fun Map<Coordinate, Tile>.countCheats(disableCollisionDuration: Int, expectedGain: Int) =
        minimalPath().let { path ->
            path.mapIndexed { index, from ->
                path
                    .drop(index + expectedGain)
                    .mapIndexed { pathIndex, to ->
                        val distance = from distanceOf to
                        if (
                            distance in (2..disableCollisionDuration)
                            && pathIndex - distance >= 0 //(pathIndex + index + expectedGain) - index - distance >= expectedGain
                        ) 1L
                        else 0L
                    }.sum()
            }.sum()
        }

    private fun Map<Coordinate, Tile>.minimalPath(): List<Coordinate> {
        val start = entries.first { (_, tile) -> tile is Start }.key
        val possibilities = mutableListOf(Visitor(start, mutableListOf(start)))
        var minimalPath: List<Coordinate> = mutableListOf(*keys.toTypedArray())
        while (possibilities.isNotEmpty()) {

            val (coordinate, path) = possibilities.removeAt(possibilities.lastIndex)

            val tile = this[coordinate]!!

            if (tile is End) {
                if (minimalPath.size >= tile.distance) minimalPath = path
                continue
            }

            possibilities.addAll(
                possibilities(coordinate, path)
                    .filter { (coordinate, _) -> this[coordinate]!!.distance > tile.distance + 1 && tile.distance + 1 < minimalPath.size }
                    .onEach { (coordinate, path) ->
                        this[coordinate]!!.distance = tile.distance + 1
                        path.add(coordinate)
                    }
            )
        }
        return minimalPath
    }

    private fun Map<Coordinate, Tile>.possibilities(
        from: Coordinate,
        path: MutableList<Coordinate>
    ): List<Visitor> =
        Direction4.entries
            .filter { direction ->
                from.to(direction)
                    .let { next ->
                        this[next] != null && this[next] !is Wall
                    }
            }
            .map { direction -> Visitor(from.to(direction), path) }

    private fun String?.parse() = toData()
        .toMap { c, _, _ ->
            when (c) {
                '#' -> Wall()
                '.' -> Path()
                'S' -> Start()
                'E' -> End()
                else -> error("...")
            }
        }

    private infix fun Coordinate.distanceOf(other: Coordinate) = abs(other.x - x) + abs(other.y - y)

    data class Visitor(
        val coordinate: Coordinate,
        val path: MutableList<Coordinate> = mutableListOf()
    )

    sealed interface Tile {
        var distance: Long
    }

    data class Start(override var distance: Long = 0) : Tile

    data class End(override var distance: Long = Long.MAX_VALUE) : Tile

    data class Path(override var distance: Long = Long.MAX_VALUE) : Tile

    data class Wall(override var distance: Long = Long.MAX_VALUE) : Tile
}

fun main() {
    processStars(Day20::star1, Day20::star2)
}
