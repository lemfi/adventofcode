package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day17 {

    private fun String?.toData() = this ?: data(17)

    fun star1(input: String?) = input
        .toData()
        .map { if (it == '>') Direction.RIGHT else Direction.LEFT }
        .let { gas ->
            play(2022, gas)
        }

    fun star2(input: String?) = input
        .toData()
        .map { if (it == '>') Direction.RIGHT else Direction.LEFT }
        .let { gas ->
            play(1000000000000, gas)
        }

    private fun play(turns: Long, gas: List<Direction>): Long {
        val floor = mutableListOf(
            Point(0, -1),
            Point(1, -1),
            Point(2, -1),
            Point(3, -1),
            Point(4, -1),
            Point(5, -1),
            Point(6, -1),
        )
        var shapeIndex = 0
        var gasIndex = 0
        val heightVariations: MutableList<Long> = mutableListOf(0)
        var pattern: List<Long> = emptyList()

        for (turn in 1..turns) {

            val shape = shapes[shapeIndex % 5]

            shape.appear(floor = floor)
            do {
                shape.gasEffect(floor, gas[gasIndex++ % gas.size])
            } while (shape.moveDown(floor))

            floor.addAll(shape.translation())
            heightVariations.add(floor.maxOf { it.y } - heightVariations.sum())
            if (turn % gas.size == 0L) {
                pattern = pattern(heightVariations)
            }
            if (pattern.isNotEmpty()) {
                break
            }
            shapeIndex++
        }

        return if (pattern.isNotEmpty()) {

            val startIndexOfPattern = startIndexOfPattern(heightVariations, pattern)

            val firstPartHeight = heightVariations.take(startIndexOfPattern).sum()

            val turnForPattern = turns - startIndexOfPattern + 1
            val iterationsOfPattern = turnForPattern / pattern.size
            val restTurns = turnForPattern % pattern.size
            println("$startIndexOfPattern > $turnForPattern > $iterationsOfPattern > $restTurns")

            firstPartHeight +
                    iterationsOfPattern * pattern.sum() +
                    (1..restTurns.toInt()).sumOf { pattern[(it - 1)] } +
                    1 // because my floor is at 0

        } else {
            return floor.maxOf { it.y } + 1
        }

    }

    private fun startIndexOfPattern(heightVariations: List<Long>, pattern: List<Long>): Int {
        var index = 0
        do {
            index++
            val window = heightVariations.drop(index).take(pattern.size)
        } while (window != pattern)
        return index
    }

    private fun pattern(list: List<Long>): List<Long> {
        return if (list.size > 3) {
            (list.size - 1 downTo 2)
                .firstOrNull { windowSize ->
                    list.windowed(windowSize, windowSize).let {
                        it.size > 3 && it.drop(1).dropLast(1).toSet().size == 1
                    }
                }
                ?.let { windowSize ->
                    list.windowed(windowSize, windowSize)[1]
                } ?: emptyList()
        } else {
            emptyList()
        }
    }

    private enum class Direction {
        LEFT, RIGHT
    }

    private val shapes = listOf(Shape1, Shape2, Shape3, Shape4, Shape5)

    private sealed interface Shape {
        val width: Int
        val height: Int
        val points: List<Point>

        var position: Point

        fun appear(floor: List<Point>) {

            position = Point(2, floor.maxOf { it.y } + 4)

        }

        fun moveDown(floor: List<Point>): Boolean {
            return if (
                points.none {
                    floor.contains(Point(it.x + position.x, it.y + position.y - 1))
                }
            ) {
                position = Point(position.x, position.y - 1)
                true
            } else {
                false
            }
        }

        fun gasEffect(floor: List<Point>, direction: Direction) {
            if (
                points.none {
                    val newPosition = it.x + position.x + (if (direction == Direction.RIGHT) 1 else -1)
                    newPosition > 6 || newPosition < 0 || floor.contains(Point(newPosition, it.y + position.y))
                }
            ) {
                position = Point(position.x + (if (direction == Direction.RIGHT) 1 else -1), position.y)
            }
        }

        fun translation() = points.map { Point(it.x + position.x, it.y + position.y) }
    }

    private object Shape1 : Shape {
        override val width = 4
        override val height = 1
        override val points: List<Point> = listOf(
            Point(0, 0),
            Point(1, 0),
            Point(2, 0),
            Point(3, 0),
        )

        override var position = Point(0, 0)
    }

    private object Shape2 : Shape {
        override val width = 3
        override val height = 3
        override val points: List<Point> = listOf(
            Point(1, 0),
            Point(0, 1),
            Point(1, 1),
            Point(2, 1),
            Point(1, 2),
        )

        override var position = Point(0, 0)
    }

    private object Shape3 : Shape {
        override val width = 3
        override val height = 3
        override val points: List<Point> = listOf(
            Point(0, 0),
            Point(1, 0),
            Point(2, 0),
            Point(2, 1),
            Point(2, 2),
        )

        override var position = Point(0, 0)
    }

    private object Shape4 : Shape {
        override val width = 1
        override val height = 4
        override val points: List<Point> = listOf(
            Point(0, 0),
            Point(0, 1),
            Point(0, 2),
            Point(0, 3),
        )

        override var position = Point(0, 0)
    }

    private object Shape5 : Shape {
        override val width = 2
        override val height = 2
        override val points: List<Point> = listOf(
            Point(0, 0),
            Point(0, 1),
            Point(1, 0),
            Point(1, 1),
        )

        override var position = Point(0, 0)
    }

    private data class Point(
        val x: Long,
        val y: Long,
    )
}

fun main() {
    processStars(Day17::star1, Day17::star2)
}