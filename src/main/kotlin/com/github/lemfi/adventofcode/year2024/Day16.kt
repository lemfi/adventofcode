package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4
import kotlin.math.min

object Day16 {

    private fun String?.toData() = this ?: data(16)

    fun star1(input: String?) = input.parse().bestPaths().first

    fun star2(input: String?) = input.parse().bestPaths().second.toSet().size

    private fun String?.parse() = toData().lines().mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            when (char) {
                '#' -> emptyList()
                '.' -> Direction4.entries.map { Path(x, y, it) }
                'S' -> listOf(Start(x, y, Direction4.RIGHT))
                'E' -> Direction4.entries.map { End(x, y, it) }
                else -> error("...")
            }
        }.flatten()
    }.flatten().associateBy { (Coordinate(it.x, it.y) to it.direction) }

    private fun Map<Pair<Coordinate, Direction4>, IPath>.bestPaths(): Pair<Long, List<Coordinate>> {
        var distance = Long.MAX_VALUE
        val start = values.first { it is Start }
        val possibilities = mutableListOf(listOf(start))
        val validPaths = mutableListOf<Pair<Long, List<IPath>>>()
        while (possibilities.isNotEmpty()) {
            val currentPath = possibilities.removeAt(possibilities.lastIndex)
            val currentTile = currentPath.last()
            val nextPaths = listOf(
                currentTile.direction.toRight(),
                currentTile.direction.toLeft(),
                currentTile.direction
            ).mapNotNull { direction ->
                this[currentTile.coords.to(direction) to direction]
            }.filter { it.distance >= currentTile.distance + if (it.direction == currentTile.direction) 1L else 1001L }
            nextPaths.forEach { path ->
                if (path is End) {
                    val thisDistance = currentTile.distance + if (path.direction == currentTile.direction) 1L else 1001L
                    distance = min(distance, thisDistance)
                    validPaths.add(thisDistance to currentPath + listOf(path))
                } else {
                    possibilities.add(currentPath + listOf(path.apply {
                        this.distance =
                            currentTile.distance + if (path.direction == currentTile.direction) 1L else 1001L
                    }))
                }
            }
        }
        return distance to validPaths.filter { it.first == distance }.flatMap { (_, path) -> path.map { it.coords } }
    }

    sealed interface IPath {
        val x: Int
        val y: Int
        val direction: Direction4
        var distance: Long

        val coords: Coordinate
            get() = Coordinate(x, y)
    }

    data class Path(
        override val x: Int,
        override val y: Int,
        override val direction: Direction4,
        override var distance: Long = Long.MAX_VALUE,
    ) : IPath

    data class Start(
        override val x: Int,
        override val y: Int,
        override val direction: Direction4,
        override var distance: Long = 0,
    ) : IPath

    data class End(
        override val x: Int,
        override val y: Int,
        override val direction: Direction4,
        override var distance: Long = Long.MAX_VALUE,
    ) : IPath
}

fun main() {
    processStars(Day16::star1, Day16::star2)
}
