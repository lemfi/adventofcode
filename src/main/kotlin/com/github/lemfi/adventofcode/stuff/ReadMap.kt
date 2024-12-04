@file:Suppress("UNCHECKED_CAST")

package com.github.lemfi.adventofcode.stuff

object ReadMap {

    fun <T> String.toMap(transformChar: (Char) -> T = { it as T }): Map<Coordinate, T> =
        lines()
            .mapIndexed { y, line ->
                line.mapIndexed { x, letter -> Coordinate(x, y) to transformChar(letter) }
            }
            .flatten()
            .toMap()

    fun <T> Map<Coordinate, T>.readMap(from: Coordinate, distance: Int, direction: Direction8): List<T> {

        val read: List<Coordinate>.() -> List<T> = {
            runCatching { map { this@readMap[it]!! } }.getOrElse { emptyList() }
        }

        return (0 until distance).map { from.to(direction, it) }.read()
    }
}