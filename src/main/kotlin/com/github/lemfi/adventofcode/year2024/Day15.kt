package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.stuff.Coordinate
import com.github.lemfi.adventofcode.stuff.Direction4

object Day15 {

    private fun String?.toData() = this ?: data(15)

    fun star1(input: String?) = input.parse()
        .let { (warehouse, moves) ->
            warehouse
                .lines()
                .mapIndexed { y, line ->
                    line.mapIndexed { x, char ->
                        when (char) {
                            '#' -> Wall(x, y)
                            'O' -> Box(x, y)
                            '@' -> Robot(x, y)
                            '.' -> null
                            else -> error("$char")
                        }
                    }
                        .filterNotNull()
                }
                .flatten() to moves.buildMoves()
        }
        .let { (stuff, moves) ->
            stuff.move(moves)
            stuff.filterIsInstance<Box>().sumOf { 100 * it.y + it.x }
        }
    
    fun star2(input: String?) = input.parse()
        .let { (warehouse, moves) ->
            warehouse
                .lines()
                .mapIndexed { y, line ->
                    line.flatMapIndexed { x, char ->
                        when (char) {
                            '#' -> listOf(Wall(x * 2, y), Wall(x * 2 + 1, y))
                            'O' -> listOf(Box2(x * 2, x * 2 + 1, y))
                            '@' -> listOf(Robot(x * 2, y))
                            '.' -> listOf(null)
                            else -> error("$char")
                        }
                    }.filterNotNull()
                }
                .flatten() to moves.buildMoves()
        }
        .let { (stuff, moves) ->
            stuff.move(moves)
            stuff.filterIsInstance<Box2>().toSet().sumOf { 100 * it.y + it.x }
        }

    private fun List<WarehouseStuff>.move(
        moves: List<Direction4>,
    ) {

        moves.forEach { direction ->
            val robot: Robot = filterIsInstance<Robot>().first()
            val toMove = mutableListOf<WarehouseStuff>()
            if (canMove(robot.coordinate.to(direction), direction, toMove)) {
                move(toMove, robot, direction)
            }
        }
    }

    private fun move(
        toMove: MutableList<WarehouseStuff>,
        robot: Robot,
        direction: Direction4
    ) {
        toMove.add(robot)
        toMove.distinct().forEach { ws ->
            when (ws) {
                is Box, is Robot -> Coordinate(ws.x, ws.y).to(direction).apply {
                    ws.x = x
                    ws.y = y
                }

                is Box2 -> {
                    ws.x = Coordinate(ws.x, ws.y).to(direction).x
                    ws.x2 = Coordinate(ws.x2, ws.y).to(direction).x
                    ws.y = Coordinate(ws.x, ws.y).to(direction).y
                }

                else -> {}
            }
        }
    }

    private fun String?.parse() = toData().split("\n\n")

    private fun String.buildMoves() = mapNotNull {
        when (it) {
            '>' -> Direction4.RIGHT
            '<' -> Direction4.LEFT
            '^' -> Direction4.UP
            'v' -> Direction4.DOWN
            '\n' -> null
            else -> error("$it")
        }
    }

    fun List<WarehouseStuff>.canMove(
        destination: Coordinate,
        direction: Direction4,
        toMove: MutableList<WarehouseStuff>
    ): Boolean {
        val stuff = firstOrNull {
            if (it is Box2) (it.x == destination.x || it.x2 == destination.x) && it.y == destination.y
            else it.x == destination.x && it.y == destination.y
        }
        val canMove = when (stuff) {
            null -> true
            is Wall -> false
            is Box -> canMove(destination.to(direction), direction, toMove)
            is Box2 -> {
                when (direction) {
                    Direction4.UP, Direction4.DOWN ->
                        canMove(Coordinate(stuff.x, stuff.y).to(direction), direction, toMove)
                                && canMove(Coordinate(stuff.x2, stuff.y).to(direction), direction, toMove)

                    Direction4.LEFT ->
                        canMove(Coordinate(stuff.x, stuff.y).to(direction), direction, toMove)

                    Direction4.RIGHT ->
                        canMove(Coordinate(stuff.x2, stuff.y).to(direction), direction, toMove)
                }
            }

            is Robot -> error("wtf...")
        }
        if (canMove && stuff != null) {
            toMove.add(stuff)
        }
        return canMove
    }

    sealed interface WarehouseStuff {
        var x: Int
        var y: Int

        val coordinate
            get() = Coordinate(x, y)
    }

    data class Box(
        override var x: Int,
        override var y: Int,
    ) : WarehouseStuff {
        override fun toString() = "0"
    }

    data class Box2(
        override var x: Int,
        var x2: Int,
        override var y: Int,
    ) : WarehouseStuff {
        override fun toString() = "0"
    }

    data class Robot(
        override var x: Int,
        override var y: Int,
    ) : WarehouseStuff {
        override fun toString() = "@"
    }

    data class Wall(
        override var x: Int,
        override var y: Int,
    ) : WarehouseStuff {
        override fun toString() = "#"
    }
}

fun main() {
    processStars(Day15::star1, Day15::star2)
}