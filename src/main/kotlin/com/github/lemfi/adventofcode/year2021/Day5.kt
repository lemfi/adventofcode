package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs

object Day5 {

    private fun String?.toData() = this ?: data(5)

    private fun String?.prepareData() = toData()
        .lines()
        .map {
            it.split(" -> ")
                .run { first().toPoint() to last().toPoint() }
        }

    private fun String.toPoint() = split(",").let { Point(it.first().toInt(), it.last().toInt()) }

    data class Point(val x: Int, val y: Int)

    fun star1(input: String?) = Grid(input.prepareData())
        .compute { p1, p2 ->
            p1.x == p2.x || p1.y == p2.y
        }

    fun star2(input: String?) = Grid(input.prepareData())
        .compute { p1, p2 ->
            p1.x == p2.x || p1.y == p2.y || abs(x - p1.x) == abs(y - p1.y)
        }

    data class Grid(
        val points: List<Pair<Point, Point>>
    ) {
        private val pointsCount = mutableMapOf<Point, Int>()

        fun compute(authorized: Point.(Point, Point) -> Boolean) = points
            .forEach { (p1, p2) ->
                (minOf(p1.x, p2.x)..maxOf(p1.x, p2.x)).forEach { x ->
                    (minOf(p1.y, p2.y)..maxOf(p1.y, p2.y)).forEach { y ->
                        with(Point(x, y)) {
                            if (authorized(p1, p2)) {
                                pointsCount[this] = (pointsCount[this] ?: 0) + 1
                            }
                        }
                    }
                }
            }.run { pointsCount.count { it.value >= 2 } }
    }

}

fun main() {
    processStars(Day5::star1, Day5::star2)
}
