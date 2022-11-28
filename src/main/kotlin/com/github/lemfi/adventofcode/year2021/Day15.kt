package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.min

object Day15 {

    private fun String?.toData() = this ?: data(15)

    private fun String?.prepareData() = toData().lines().let { lines ->
        (lines.indices).flatMap { y ->
            (lines[y].indices).map { x ->
                Point(x, y, lines[y][x].digitToInt())
            }
        }
    }

    fun star1(input: String?) = input
        .prepareData()
        .shortestDistance()

    fun star2(input: String?) = input
        .prepareData()
        .expand()
        .shortestDistance()

    private fun List<Point>.shortestDistance(): Int {
        val grid = toMutableList()
        val lastPoint = grid.last()

        var point = grid.first()
        var distance = 0
        while (point != lastPoint) {
            grid
                .neighbors(point)
                .onEach { it.guess = min(it.guess, distance + it.risk) }

            point = grid.minByOrNull { it.guess }!!.apply {
                distance = guess
                visited = true
                grid.remove(this)
            }
        }
        return distance
    }

    fun Collection<Point>.neighbors(point: Point) =
        filter {
            !it.visited
                    && (
                    it.x == point.x - 1 && it.y == point.y
                            || it.x == point.x + 1 && it.y == point.y
                            || it.x == point.x && it.y == point.y - 1
                            || it.x == point.x && it.y == point.y + 1
                    )
        }

    fun List<Point>.expand(): List<Point> {
        val width = maxOf { it.x } + 1
        val height = maxOf { it.y } + 1

        return (0..4).flatMap { iterx ->
            (0..4).flatMap { itery ->
                map {
                    Point(
                        it.x + width * iterx,
                        it.y + height * itery,
                        ((it.risk + iterx + itery) % 9).let { if (it == 0) 9 else it }
                    )
                }
            }
        }
    }

    data class Point(
        val x: Int,
        val y: Int,
        val risk: Int,
        var visited: Boolean = false,
        var guess: Int = Int.MAX_VALUE
    )

}

fun main() {
    processStars(Day15::star1, Day15::star2)
}
