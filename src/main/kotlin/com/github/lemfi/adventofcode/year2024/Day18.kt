package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4

object Day18 {

    private fun String?.toData() = this ?: data(18)

    fun star1(input: String?) = input.run {
        val take = if (this == null) 1024 else 12
        val width = if (this == null) 70 else 6

        parse().findDistance(take, width, true)
    }

    fun star2(input: String?) = input.run {
        val take = if (this == null) 1024 else 12
        val width = if (this == null) 70 else 6

        parse().run {
            this[
                (size - 1 downTo take)
                    .toList()
                    .first { take -> findDistance(take, width, false) != Long.MAX_VALUE }
            ].let { "${it.x},${it.y}" }
        }
    }

    private fun List<Coordinate>.findDistance(take: Int, width: Int, forMinimalDistance: Boolean) =
        take(take)
            .toMap(width)
            .associateWith { Long.MAX_VALUE }
            .run {
                val map = toMutableMap()
                var position = Coordinate(0, 0)
                var distance = Long.MAX_VALUE
                val possibilities = mutableListOf(
                    *Direction4.entries
                        .map { position.to(it) }
                        .filter { map.containsKey(it) }
                        .map { it to 1L }.toTypedArray()
                )
                while (possibilities.isNotEmpty()) {
                    val p = possibilities.removeAt(possibilities.lastIndex)
                    position = p.first
                    val currentDistance = p.second
                    if (currentDistance + 1 > distance) continue
                    if (position != Coordinate(width, width)) {
                        possibilities.addAll(
                            Direction4.entries
                                .asSequence()
                                .map { position.to(it) }
                                .filter { (map[it] ?: Long.MIN_VALUE) > (currentDistance + 1L) }
                                .onEach { map[it] = currentDistance + 1L }
                                .map { it to currentDistance + 1 }
                                .toList()
                        )
                    } else {
                        distance = minOf(distance, currentDistance)
                        if (!forMinimalDistance) break
                    }
                }
                distance
            }

    private fun List<Coordinate>.toMap(
        width: Int,
    ) = (0..width).flatMap { y ->
        (0..width).mapNotNull { x ->
            if (contains(Coordinate(x, y))) null else Coordinate(x, y)
        }
    }

    private fun String?.parse() = toData()
        .lines()
        .map { it.split(",").let { (x, y) -> Coordinate(x.toInt(), y.toInt()) } }

}

fun main() {
    processStars(Day18::star1, Day18::star2)
}
