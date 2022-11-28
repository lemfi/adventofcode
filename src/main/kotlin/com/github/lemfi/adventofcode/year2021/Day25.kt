package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.properties.Delegates

object Day25 {

    private fun String?.toData() = this ?: data(25)

    data class Position(val x: Int, val y: Int)

    val grid: MutableMap<Position, String> = mutableMapOf()
    val cucumbers: MutableMap<String, MutableSet<Position>> = mutableMapOf(
        ">" to mutableSetOf(),
        "v" to mutableSetOf(),
    )

    var maxW by Delegates.notNull<Int>()
    var maxH by Delegates.notNull<Int>()

    fun star1(input: String?) = input.toData().let {
        grid.clear()
        cucumbers.onEach { it.value.clear() }
        maxH = it.lines().count() - 1
        maxW = it.lines().first().count() - 1
        (it.lines().indices).forEach { y ->
            (it.lines()[y].indices).forEach { x ->
                it.lines()[y][x].toString().let { spot ->
                    grid[Position(x, y)] = spot
                    if (spot != ".") cucumbers[spot]!!.add(Position(x, y))
                }
            }
        }.let {
            var steps = 0
            do {
                var movingCucumber: Boolean

                cucumbers[">"]!!
                    .sortedBy { position -> position.x }
                    .fold(
                        Pair(
                            mutableListOf<Position>(),
                            mutableMapOf<Position, String>()
                        )
                    ) { (newPositions, newGrid), cucumberPosition ->
                        val right = cucumberPosition.right()
                        if (right.isAvailable()) {
                            newPositions.add(right)
                            newGrid[right] = ">"
                            newGrid[cucumberPosition] = "."
                        } else {
                            newPositions.add(cucumberPosition)
                        }
                        newPositions to newGrid
                    }.let { (newPositions, newGrid) ->
                        movingCucumber = (newPositions intersect cucumbers[">"]!!).size != newPositions.size
                        cucumbers[">"] = newPositions.toMutableSet()
                        newGrid.forEach { grid[it.key] = it.value }
                    }

                cucumbers["v"]!!
                    .sortedBy { it.y }
                    .fold(
                        Pair(
                            mutableListOf<Position>(),
                            mutableMapOf<Position, String>()
                        )
                    ) { (newPositions, newGrid), cucumberPosition ->
                        val down = cucumberPosition.down()
                        if (down.isAvailable()) {
                            newPositions.add(down)
                            newGrid[down] = "v"
                            newGrid[cucumberPosition] = "."
                        } else {
                            newPositions.add(cucumberPosition)
                        }
                        newPositions to newGrid
                    }.let { (newPositions, newGrid) ->
                        movingCucumber =
                            movingCucumber || (newPositions intersect cucumbers["v"]!!).size != newPositions.size
                        cucumbers["v"] = newPositions.toMutableSet()
                        newGrid.forEach { grid[it.key] = it.value }
                    }

                steps++
            } while (movingCucumber)
            steps
        }

    }

    fun Position.isAvailable() = grid[this] == "."
    fun Position.right() = Position(if (x == maxW) 0 else x + 1, y)
    fun Position.down() = Position(x, if (y == maxH) 0 else y + 1)

}

fun main() {
    processStars(Day25::star1)
}
