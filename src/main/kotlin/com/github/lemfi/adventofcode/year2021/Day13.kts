package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day13 {

    private fun String?.toData() = this ?: data(13)

    private fun String?.prepareData() = toData().split("\n\n").let {
        it.first().toGrid() to it.last().toInstructions()
    }

    fun star1(input: String?) = input.prepareData().let { (grid, instructions) ->
        with(instructions.first()) {
            grid.map {
                if (it.axis() < range) it
                else it.fold()
            }.toSet().count()
        }
    }

    fun star2(input: String?) = input.prepareData().let { (grid, instructions) ->
        instructions.fold(grid) { foldedGrid, instruction ->
            with(instruction) {
                foldedGrid.map {
                    if (it.axis() < range) it
                    else it.fold()
                }.toSet()
            }
        }.print()
    }

    private fun String.toGrid() =
        lines().map { it.split(",").let { Point(it.first().toString().toInt(), it.last().toString().toInt()) } }.toSet()

    private fun String.toInstructions() =
        lines().map {
            it.substringAfter("fold along ").split("=").let {
                if (it.first() == "x") FoldX(it.last().toInt()) else FoldY(it.last().toInt())
            }
        }

    data class Point(val x: Int, val y: Int)
    sealed class Instruction {
        abstract val range: Int
        abstract fun Point.axis(): Int
        abstract fun Point.fold(): Point
    }

    data class FoldX(override val range: Int) : Instruction() {
        override fun Point.axis() = x
        override fun Point.fold() = Point(x - 2 * (x - range), y)
    }

    data class FoldY(override val range: Int) : Instruction() {
        override fun Point.axis() = y
        override fun Point.fold() = Point(x, y - 2 * (y - range))
    }

    fun Set<Point>.print() =
        """
        |
        |${
            (0..maxOf { it.y }).joinToString("\n") { y ->
                (0..maxOf { it.x }).joinToString("") { x ->
                    if (contains(Point(x, y))) "#" else " "
                }
            }
        }""".trimMargin()

}

processStars(Day13::star1, Day13::star2)