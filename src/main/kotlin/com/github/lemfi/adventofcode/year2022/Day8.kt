package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day8 {

    private fun String?.toData() = this ?: data(8)

    fun star1(input: String?) = input.toData().lines().let { lines ->
        lines.mapIndexed { y, line ->
            line.mapIndexed { x, _ ->
                if (isVisible(x, y, lines)) 1 else 0
            }.sum()
        }.sum()
    }

    fun star2(input: String?) = input.toData().lines().let { lines ->
        lines.mapIndexed { y, line ->
            line.mapIndexed { x, _ ->
                score(x, y, lines)
            }.max()
        }.max()
    }


    private fun score(x: Int, y: Int, lines: List<String>) =
        Triple(
            lines[y][x].digitToInt(),
            lines.size,
            lines.first().length
        ).let { (height, gridHeight, gridWidth) ->

            left(x).horizontalScore(y, lines, height) *
                    right(x, gridWidth).horizontalScore(y, lines, height) *
                    top(y).verticalScore(x, lines, height) *
                    bottom(y, gridHeight).verticalScore(x, lines, height)
        }

    private fun List<Boolean>.computeScore() =
        takeWhile { lowerTree -> lowerTree }.count() + if (any { lowerTree -> !lowerTree }) 1 else 0

    private fun isVisible(x: Int, y: Int, lines: List<String>) =
        Triple(
            lines[y][x].digitToInt(),
            lines.size,
            lines.first().length
        ).let { (height, gridHeight, gridWidth) ->

            left(x).isHorizontalVisible(y, lines, height)
                    || right(x, gridWidth).isHorizontalVisible(y, lines, height)
                    || top(y).isVerticalVisible(x, lines, height)
                    || bottom(y, gridHeight).isVerticalVisible(x, lines, height)
        }

    private fun left(x: Int) = (x - 1 downTo 0)
    private fun right(x: Int, max: Int) = (x + 1 until max)
    private fun top(y: Int) = (y - 1 downTo 0)
    private fun bottom(y: Int, max: Int) = (y + 1 until max)


    private fun IntProgression.isHorizontalVisible(y: Int, lines: List<String>, height: Int) =
        all { (it to y).isLowerThanTree(lines, height) }

    private fun IntProgression.isVerticalVisible(x: Int, lines: List<String>, height: Int) =
        all { (x to it).isLowerThanTree(lines, height) }

    private fun IntProgression.horizontalScore(y: Int, lines: List<String>, height: Int) =
        map { (it to y).isLowerThanTree(lines, height) }.computeScore()

    private fun IntProgression.verticalScore(x: Int, lines: List<String>, height: Int) =
        map { (x to it).isLowerThanTree(lines, height) }.computeScore()

    private fun Pair<Int, Int>.isLowerThanTree(
        lines: List<String>,
        treeHeight: Int
    ) = let { (x, y) ->
        lines[y][x].digitToInt() < treeHeight
    }
}

fun main() {
    processStars(Day8::star1, Day8::star2)
}