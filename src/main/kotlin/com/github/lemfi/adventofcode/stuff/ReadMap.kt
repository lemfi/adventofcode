@file:Suppress("UNCHECKED_CAST")

package com.github.lemfi.adventofcode.stuff

object ReadMap {

    fun <T> String.toMap(transformChar: (Char, Int, Int) -> T = { c, _, _ -> c as T }): Map<Coordinate, T> =
        lines()
            .mapIndexed { y, line ->
                line.mapIndexed { x, letter -> Coordinate(x, y) to transformChar(letter, x, y) }
            }
            .flatten()
            .toMap()

    fun <T> Map<Coordinate, T>.readMap(from: Coordinate, distance: Int, direction: Direction4): List<T> =
        when (direction) {
            Direction4.UP -> readMap(from, distance, Direction8.UP)
            Direction4.DOWN -> readMap(from, distance, Direction8.DOWN)
            Direction4.LEFT -> readMap(from, distance, Direction8.LEFT)
            Direction4.RIGHT -> readMap(from, distance, Direction8.RIGHT)
        }

    fun <T> Map<Coordinate, T>.readMap(from: Coordinate, distance: Int, direction: Direction8): List<T> {

        val read: List<Coordinate>.() -> List<T> = {
            runCatching { map { this@readMap[it]!! } }.getOrElse { emptyList() }
        }

        return (0 until distance).map { from.to(direction, it) }.read()
    }

    fun <T> Map<Coordinate, T>.findPositions(
        from: Coordinate,
        direction: Direction8,
        condition: (T) -> Boolean
    ): List<Coordinate> {
        var next = from.to(direction)
        val res = mutableListOf<Coordinate>()
        while (this.containsKey(next)) {
            if (condition(this[next]!!)) res.add(next)
            next = next.to(direction)
        }
        return res
    }
}