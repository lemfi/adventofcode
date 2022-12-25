package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import kotlin.math.max
import kotlin.math.min

object Day24 {

    private fun String?.toData() = this ?: data(24)

    fun star1(input: String?) = input.toData().lines().mapIndexed { y, line ->
        if (y == 0) null
        else if (line[1] == '#') null
        else line.mapIndexedNotNull { x, c ->
            when (c) {
                '#' -> null
                '.' -> null
                else -> {
                    when (c) {
                        '>' -> Blizzard(Position(x - 1, y - 1), Direction.RIGHT)
                        '<' -> Blizzard(Position(x - 1, y - 1), Direction.LEFT)
                        'v' -> Blizzard(Position(x - 1, y - 1), Direction.DOWN)
                        else -> Blizzard(Position(x - 1, y - 1), Direction.UP)
                    }
                }
            }
        }
    }
        .filterNotNull()
        .flatten()
        .let { toto ->

            val valley = Valley(
                Position(0, -1),
                Position(toto.maxOf { it.position.x }, toto.maxOf { it.position.y } + 1),
                toto)

            val seen = mutableSetOf<Pair<Int, Position>>()
            val explore = mutableListOf(valley)
            var bestMoves = Int.MAX_VALUE
            while (explore.isNotEmpty()) {
                with(explore.removeFirst()) {
                    if (moves < bestMoves - 1) {
                        moves += 1
                        this.blizzards.forEach { it.move() }
                        availablePositions().let { availablePositions ->
                            if (availablePositions.contains(exit)) {
                                bestMoves = min(bestMoves, moves)
                            } else {
                                explore.addAllIfNotContains(
                                    height,
                                    width,
                                    seen,
                                    availablePositions.map {
                                        copy(
                                            blizzards = this.blizzards.map { it.copy(position = it.position.copy()) },
                                            myPosition = it
                                        )
                                    }
                                )
                            }

                        }
                    }
                }
            }

            bestMoves
        }

    private fun MutableList<Valley>.addAllIfNotContains(
        height: Int,
        width: Int,
        seen: MutableSet<Pair<Int, Position>>,
        valleys: List<Valley>,
    ) {
        valleys.reversed().forEach { valley ->
            //            if (seen.add((valley.moves % (height * width)) to valley.myPosition)) {
            if (seen.add((valley.moves) to valley.myPosition)) {
                //                println("add (${valley.myPosition.x},${valley.myPosition.y}) at move ${valley.moves}")
                add(valley)
            } else {
                //                println("     position (${valley.myPosition.x},${valley.myPosition.y}) already seen at move ${valley.moves}")
            }
        }
    }

    fun star2(input: String?) = input.toData().lines().mapIndexed { y, line ->
        if (y == 0) null
        else if (line[1] == '#') null
        else line.mapIndexedNotNull { x, c ->
            when (c) {
                '#' -> null
                '.' -> null
                else -> {
                    when (c) {
                        '>' -> Blizzard(Position(x - 1, y - 1), Direction.RIGHT)
                        '<' -> Blizzard(Position(x - 1, y - 1), Direction.LEFT)
                        'v' -> Blizzard(Position(x - 1, y - 1), Direction.DOWN)
                        else -> Blizzard(Position(x - 1, y - 1), Direction.UP)
                    }
                }
            }
        }
    }
        .filterNotNull()
        .flatten()
        .let { toto ->

            val valley = Valley(
                Position(0, -1),
                Position(toto.maxOf { it.position.x }, toto.maxOf { it.position.y } + 1),
                toto)

            val bestMoves1 = explore(valley)
            val bestMoves2 = explore(bestMoves1.second.copy(entrance = valley.exit, exit = valley.entrance))
            val bestMoves3 = explore(bestMoves2.second.copy(entrance = valley.entrance, exit = valley.exit))

            bestMoves3.first
        }

    private fun explore(
        valley: Valley,
    ): Pair<Int, Valley> {
        val explore = mutableListOf(valley)
        val seen = mutableSetOf<Pair<Int, Position>>(0 to valley.myPosition)
        var bestMoves = Int.MAX_VALUE
        var outValley: Valley? = null
        while (explore.isNotEmpty()) {
            with(explore.removeFirst()) {
                if (moves < bestMoves - 1) {
                    moves += 1
                    this.blizzards.forEach { it.move() }
                    availablePositions().let { availablePositions ->
                        if (availablePositions.contains(exit)) {
                            bestMoves = min(bestMoves, moves)
                            outValley = copy(
                                blizzards = this.blizzards.map { it.copy(position = it.position.copy()) },
                                myPosition = exit
                            )
                        } else {
                            explore.addAllIfNotContains(
                                height,
                                width,
                                seen,
                                availablePositions.map {
                                    copy(
                                        blizzards = this.blizzards.map { it.copy(position = it.position.copy()) },
                                        myPosition = it
                                    )
                                }
                            )
                        }

                    }
                }
            }
        }
        return bestMoves to outValley!!
    }

    private data class Valley(
        val entrance: Position,
        val exit: Position,
        val blizzards: List<Blizzard>,
        var moves: Int = 0,
        var myPosition: Position = entrance,
        var voyage1: Boolean = true,
        var voyage2: Boolean = false,
        var voyage3: Boolean = false,
    ) {

        val height = max(exit.y, entrance.y)
        val width = max(exit.x, entrance.x) + 1

        fun Blizzard.move() =
            when (direction) {
                Direction.RIGHT -> if (position.x == width - 1) position.x = 0 else position.x += 1
                Direction.LEFT -> if (position.x == 0) position.x = width - 1 else position.x -= 1
                Direction.UP -> if (position.y == 0) position.y = height - 1 else position.y -= 1
                Direction.DOWN -> if (position.y == height - 1) position.y = 0 else position.y += 1
            }

        fun Position.moves() = mutableListOf<Position>().apply {
            if (y == -1) add(Position(0, 0))
            else if (y == height) add(Position(x, height - 1))
            else {
                if (exit == Position(0, -1)) {
                    if (x == exit.x && y == 0) add(exit)
                } else {
                    if (x == exit.x && y == exit.y - 1) add(exit)
                }
                if (x != width - 1) add(Position(x + 1, y))
                if (y != 0) add(Position(x, y - 1))
                if (x != 0) add(Position(x - 1, y))
                if (y != height - 1) add(Position(x, y + 1))
            }
            add(this@moves)
        }

        fun availablePositions(): List<Position> =
            myPosition.moves().filterNot { position ->
                blizzards.any { it.position == position }
            }
    }

    private data class Position(var x: Int, var y: Int)

    enum class Direction {
        RIGHT, LEFT, UP, DOWN
    }

    private data class Blizzard(val position: Position, val direction: Direction)
}

fun main() {
    processStars(Day24::star1, Day24::star2)
}