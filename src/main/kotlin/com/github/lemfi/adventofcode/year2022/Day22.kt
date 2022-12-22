package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import kotlin.math.sqrt

object Day22 {

    private fun String?.toData() = this ?: data(22)

    fun star1(input: String?) = input.toData().split("\n\n").let { (map, path) ->

        var remainingPath = path
        val directions = mutableListOf<Direction>(Up(0))
        var currentDirection = "R"
        while (remainingPath.isNotBlank()) {
            remainingPath.takeWhile { it.isNumber() }.let {
                remainingPath = remainingPath.substringAfter(it)
                directions.add(
                    when (currentDirection) {
                        "R" ->
                            when (directions.last()) {
                                is Right -> Down(it.toInt())
                                is Left -> Up(it.toInt())
                                is Up -> Right(it.toInt())
                                is Down -> Left(it.toInt())
                            }

                        else -> when (directions.last()) {
                            is Right -> Up(it.toInt())
                            is Left -> Down(it.toInt())
                            is Up -> Left(it.toInt())
                            is Down -> Right(it.toInt())
                        }
                    }
                )
                currentDirection = remainingPath.take(1)
                remainingPath = remainingPath.drop(1)
            }
        }
        Map(map) to directions.drop(1)
    }.let { (map, directions) ->

        map.move(directions).let { (row, col, direction) ->
            1000 * row + 4 * col + direction
        }
    }

    fun star2(input: String?) = input.toData().split("\n\n").let { (map, path) ->

        var remainingPath = path
        val directions = mutableListOf<Direction>(Up(0))
        var currentDirection = "R"
        while (remainingPath.isNotBlank()) {
            remainingPath.takeWhile { it.isNumber() }.let {
                remainingPath = remainingPath.substringAfter(it)
                directions.add(
                    when (currentDirection) {
                        "R" -> Right(it.toInt())
                        else -> Left(it.toInt())
                    }
                )
                currentDirection = remainingPath.take(1)
                remainingPath = remainingPath.drop(1)
            }
        }
        Cube(map) to directions.drop(1)
    }.let { (map, directions) ->

        map.move(directions).let { (row, col, direction) ->
            1000 * row + 4 * col + direction
        }
    }

    private fun Char.isNumber() = runCatching { digitToInt() }.getOrNull() != null

    private data class Map(private val mapAsString: String) {

        val width = mapAsString.lines().maxOf { it.length }
        val height = mapAsString.lines().size

        val map = mapAsString.lines().map {
            var s = it
            while (s.length != width) {
                s = "$s "
            }
            s
        }

        fun move(directions: List<Direction>): Triple<Int, Int, Int> {
            var currentPosition = startingPosition()
            directions.forEach { direction ->
                currentPosition = direction.move(currentPosition) // 10,0  10,5  3,5  3,7  7,7
            }
            return Triple(
                currentPosition.second + 1, currentPosition.first + 1, when (directions.last()) {
                    is Right -> 0
                    is Down -> 1
                    is Left -> 2
                    is Up -> 3
                }
            )
        }

        private fun Direction.move(currentPosition: Pair<Int, Int>): Pair<Int, Int> =
            when (this) {
                is Right -> moveRight(currentPosition, step)
                is Left -> moveLeft(currentPosition, step)
                is Up -> moveUp(currentPosition, step)
                is Down -> moveDown(currentPosition, step)
            }


        private fun moveRight(currentPosition: Pair<Int, Int>, steps: Int): Pair<Int, Int> =
            (1..steps).fold(currentPosition) { position, _ ->
                right(position)
            }

        private fun moveLeft(currentPosition: Pair<Int, Int>, steps: Int): Pair<Int, Int> =
            (1..steps).fold(currentPosition) { position, _ ->
                left(position)
            }

        private fun moveUp(currentPosition: Pair<Int, Int>, steps: Int): Pair<Int, Int> =
            (1..steps).fold(currentPosition) { position, _ ->
                up(position)
            }

        private fun moveDown(currentPosition: Pair<Int, Int>, steps: Int): Pair<Int, Int> =
            (1..steps).fold(currentPosition) { position, _ ->
                down(position)
            }

        private fun right(currentPosition: Pair<Int, Int>, fallback: Pair<Int, Int> = currentPosition): Pair<Int, Int> {
            val option = if (currentPosition.first == width - 1) 0 to currentPosition.second
            else currentPosition.first + 1 to currentPosition.second
            return if (map[option.second][option.first] == ' ') right(option, fallback)
            else if (map[option.second][option.first] == '#') fallback
            else option
        }

        private fun left(currentPosition: Pair<Int, Int>, fallback: Pair<Int, Int> = currentPosition): Pair<Int, Int> {
            val option = if (currentPosition.first == 0) (width - 1) to currentPosition.second
            else currentPosition.first - 1 to currentPosition.second
            return if (map[option.second][option.first] == ' ') left(option, fallback)
            else if (map[option.second][option.first] == '#') fallback
            else option
        }

        private fun up(currentPosition: Pair<Int, Int>, fallback: Pair<Int, Int> = currentPosition): Pair<Int, Int> {
            val option = if (currentPosition.second == 0) currentPosition.first to (height - 1)
            else currentPosition.first to currentPosition.second - 1
            return if (map[option.second][option.first] == ' ') up(option, fallback)
            else if (map[option.second][option.first] == '#') fallback
            else option
        }

        private fun down(currentPosition: Pair<Int, Int>, fallback: Pair<Int, Int> = currentPosition): Pair<Int, Int> {
            val option = if (currentPosition.second == height - 1) currentPosition.first to 0
            else currentPosition.first to currentPosition.second + 1
            return if (map[option.second][option.first] == ' ') down(option, fallback)
            else if (map[option.second][option.first] == '#') fallback
            else option
        }

        private fun startingPosition() = map.first().indexOfFirst { it == '.' }.let { it to 0 }
    }

    private data class Cube(private val mapAsString: String) {

        val width = mapAsString.lines().maxOf { it.length }
        val height = mapAsString.lines().size

        val xyFront: Face
        val xyBack: Face
        val xzUp: Face
        val xzDown: Face
        val yzLeft: Face
        val yzRight: Face
        val faceSize = sqrt((width * height / 2.0) / 6).toInt()

        init {
            val flip: String.() -> String =
                { lines().map { it.toList().reversed().joinToString("") }.joinToString("\n") }
            val rotate90: String.() -> String = {
                lines().mapIndexed { y, line ->
                    line.toList().mapIndexed { x, _ ->
                        lines()[x].toList()[y].toString()
                    }.joinToString("")
                }
                    .joinToString("\n")
                    .flip()
            }
            val rotate180: String.() -> String = { lines().reversed().joinToString("\n") }

            val startingPosition = mapAsString.lines().first().indexOfFirst { it == '.' }.let { it to 0 }
            val faces = mutableListOf<Face>()
            xyFront = XYFront(extractTile(startingPosition, faceSize))

            if (runCatching { mapAsString.lines()[startingPosition.second][startingPosition.first + faceSize] }.getOrNull() != null) {
                val currentPosition = startingPosition.first + faceSize to startingPosition.second
                val yzRight = YZRight(extractTile(currentPosition, faceSize))
                faces.add(yzRight)
            }
            //            if (runCatching { mapAsString.lines()[startingPosition.second][startingPosition.first + 2 * faceSize] }.getOrNull() != null) {
            //                val currentPosition = startingPosition.first + 2 * faceSize to startingPosition.second
            //                val xyBack = XYBack(extractTile(currentPosition, faceSize).flip())
            //                faces.add(xyBack)
            //            }
            //            if (runCatching { mapAsString.lines()[startingPosition.second][startingPosition.first + 3 * faceSize] }.getOrNull() != null) {
            //                val currentPosition = startingPosition.first + 3 * faceSize to startingPosition.second
            //                val yzLeft = YZLeft(extractTile(currentPosition, faceSize))
            //                faces.add(yzLeft)
            //            }
            if (runCatching { mapAsString.lines()[startingPosition.second + faceSize][startingPosition.first] }.getOrNull() != null) {
                val currentPosition = startingPosition.first to startingPosition.second + faceSize
                val xzDown = XZDown(extractTile(currentPosition, faceSize))
                faces.add(xzDown)
            }
            //            if (runCatching { mapAsString.lines()[startingPosition.second + faceSize][startingPosition.first + faceSize] }.getOrNull() != null) {
            //                val currentPosition = startingPosition.first + faceSize to startingPosition.second + faceSize
            //                val yzRight = YZRight(extractTile(currentPosition, faceSize))
            //                faces.add(yzRight)
            //            }
            //            if (runCatching { mapAsString.lines()[startingPosition.second + faceSize][startingPosition.first + 2 * faceSize] }.getOrNull() != null) {
            //                val currentPosition = startingPosition.first + 2 * faceSize to startingPosition.second + faceSize
            //                val xzUp = XZUp(extractTile(currentPosition, faceSize))
            //                faces.add(xzUp)
            //            }
            //            if (runCatching { mapAsString.lines()[startingPosition.second + faceSize][startingPosition.first + 3 * faceSize] }.getOrNull() != null) {
            //                val currentPosition = startingPosition.first + 3 * faceSize to startingPosition.second + faceSize
            //                val yzLeft = YZLeft(extractTile(currentPosition, faceSize))
            //                faces.add(yzLeft)
            //            }
            if (runCatching { mapAsString.lines()[startingPosition.second + faceSize][startingPosition.first - faceSize] }.let { it.getOrNull() != null && it.getOrNull() != ' ' }) {
                val currentPosition = startingPosition.first to startingPosition.second + faceSize
                val yzLeft = YZLeft(extractTile(currentPosition, faceSize).rotate90())
                faces.add(yzLeft)
            }
            if (runCatching { mapAsString.lines()[startingPosition.second + faceSize][startingPosition.first - 2 * faceSize] }.let { it.getOrNull() != null && it.getOrNull() != ' ' }) {
                val currentPosition = startingPosition.first - 2 * faceSize to startingPosition.second + faceSize
                val xzUp = XZUp(extractTile(currentPosition, faceSize).rotate90().rotate180())
                faces.add(xzUp)
            }
            //            if (runCatching { mapAsString.lines()[startingPosition.second + faceSize][startingPosition.first - 3 * faceSize] }.let { it.getOrNull() != null && it.getOrNull() != ' ' }) {
            //                val currentPosition = startingPosition.first - 3 * faceSize to startingPosition.second + faceSize
            //                val yzRight = YZRight(extractTile(currentPosition, faceSize))
            //                faces.add(yzRight)
            //            }

            if (runCatching { mapAsString.lines()[startingPosition.second + 2 * faceSize][startingPosition.first] }.getOrNull() != null) {
                val currentPosition = startingPosition.first to startingPosition.second + 2 * faceSize
                val xyBack = XYBack(extractTile(currentPosition, faceSize).rotate180())
                faces.add(xyBack)
            }
            if (runCatching { mapAsString.lines()[startingPosition.second + 2 * faceSize][startingPosition.first + faceSize] }.getOrNull() != null) {
                val currentPosition = startingPosition.first + faceSize to startingPosition.second + 2 * faceSize
                val yzRight = YZRight(extractTile(currentPosition, faceSize).rotate90().flip().rotate180())
                faces.add(yzRight)
            }
            if (runCatching { mapAsString.lines()[startingPosition.second + 2 * faceSize][startingPosition.first - faceSize] }.let { it.getOrNull() != null && it.getOrNull() != ' ' }) {
                val currentPosition = startingPosition.first - faceSize to startingPosition.second + 2 * faceSize
                val yzLeft = YZLeft(extractTile(currentPosition, faceSize))
                faces.add(yzLeft)
            }
            if (runCatching { mapAsString.lines()[startingPosition.second + 3 * faceSize][startingPosition.first - faceSize] }.let { it.getOrNull() != null && it.getOrNull() != ' ' }) {
                val currentPosition = startingPosition.first - faceSize to startingPosition.second + 3 * faceSize
                val xzUp = XZUp(extractTile(currentPosition, faceSize)/*.rotate180()*/)
                faces.add(xzUp)
            }

            xyBack = faces.first { it is XYBack }
            xzUp = faces.first { it is XZUp }
            xzDown = faces.first { it is XZDown }
            yzLeft = faces.first { it is YZLeft }
            yzRight = faces.first { it is YZRight }

            println(yzRight.print())
        }

        private fun extractTile(currentPosition: Pair<Int, Int>, faceSize: Int) = mapAsString.lines().let { lines ->
            (currentPosition.second until currentPosition.second + faceSize).map { line ->
                lines[line].substring(currentPosition.first, currentPosition.first + faceSize)
            }.joinToString("\n")
        }


        fun move(directions: List<Direction>): Triple<Int, Int, Int> {
            var currentPosition = startingPosition()
            var currentDirection: Direction = Up(0)
            directions.forEach { direction ->
                val dd = direction.fromCurrent(currentDirection).move(currentPosition) // 10,0  10,5  3,5  3,7  7,7
                currentPosition = dd.first
                currentDirection = dd.second
            }
            return Triple(
                currentPosition.y + 1, currentPosition.x + 1, when (directions.last()) {
                    is Right -> 0
                    is Down -> 1
                    is Left -> 2
                    is Up -> 3
                }
            )
        }

        private fun Direction.fromCurrent(currentDirection: Direction) =
            when (currentDirection) {
                is Right -> when (this) {
                    is Right -> Down(step)
                    is Left -> Up(step)
                    else -> error("hmm...")
                }

                is Left -> when (this) {
                    is Right -> Up(step)
                    is Left -> Down(step)
                    else -> error("hmm...")
                }

                is Up -> when (this) {
                    is Right -> Right(step)
                    is Left -> Left(step)
                    else -> error("hmm...")
                }

                is Down -> when (this) {
                    is Right -> Left(step)
                    is Left -> Right(step)
                    else -> error("hmm...")
                }
            }

        private fun Direction.move(currentPosition: Position): Pair<Position, Direction> {
            var currentDirection = this
            return (step downTo 1).fold(currentPosition) { position, _ ->
                println("$position")
                when (currentDirection) {
                    is Right -> {
                        val (newDirection, newPosition) = right(position)
                        currentDirection = newDirection
                        newPosition
                    }

                    is Left -> {
                        val (newDirection, newPosition) = left(position)
                        currentDirection = newDirection
                        newPosition
                    }

                    is Up -> {
                        val (newDirection, newPosition) = up(position)
                        currentDirection = newDirection
                        newPosition
                    }

                    is Down -> {
                        val (newDirection, newPosition) = down(position)
                        currentDirection = newDirection
                        newPosition
                    }
                }

            } to currentDirection
        }

        private fun right(
            currentPosition: Position,
            fallback: Pair<Direction, Position> = Right(0) to currentPosition
        ): Pair<Direction, Position> {
            val option = if (currentPosition.x == faceSize - 1) {
                when (currentPosition.face) {
                    is XYFront -> {
                        Right(0) to Position(0, currentPosition.y, yzRight)
                    }

                    is XYBack -> {
                        Right(0) to Position(0, currentPosition.y, yzLeft)
                    }

                    is YZRight -> {
                        Right(0) to Position(0, currentPosition.y, xyBack)
                    }

                    is YZLeft -> {
                        Right(0) to Position(0, currentPosition.y, xyFront)
                    }

                    is XZUp -> {
                        Down(0) to Position(currentPosition.y, faceSize - 1, yzRight)
                    }

                    is XZDown -> {
                        Down(0) to Position(faceSize - 1 - currentPosition.y, 0, yzRight)
                    }
                }
            } else Right(0) to Position(currentPosition.x + 1, currentPosition.y, currentPosition.face)

            return if (option.second.face.lines()[option.second.y][option.second.x] == '#') fallback
            else option
        }

        private fun left(
            currentPosition: Position,
            fallback: Pair<Direction, Position> = Left(0) to currentPosition
        ): Pair<Direction, Position> {
            val option = if (currentPosition.x == 0) {
                when (currentPosition.face) {
                    is XYFront -> {
                        Left(0) to Position(faceSize - 1, currentPosition.y, yzLeft)
                    }

                    is XYBack -> {
                        Left(0) to Position(faceSize - 1, currentPosition.y, yzRight)
                    }

                    is YZRight -> {
                        Left(0) to Position(faceSize - 1, currentPosition.y, xyFront)
                    }

                    is YZLeft -> {
                        Left(0) to Position(faceSize - 1, currentPosition.y, xyBack)
                    }

                    is XZUp -> {
                        Down(0) to Position(currentPosition.y, faceSize - 1, yzLeft)
                    }

                    is XZDown -> {
                        Up(0) to Position(currentPosition.y, 0, yzLeft)
                    }
                }
            } else Left(0) to Position(currentPosition.x - 1, currentPosition.y, currentPosition.face)

            return if (option.second.face.lines()[option.second.y][option.second.x] == '#') fallback
            else option
        }

        private fun up(
            currentPosition: Position,
            fallback: Pair<Direction, Position> = Right(0) to currentPosition
        ): Pair<Direction, Position> {
            val option = if (currentPosition.y == 0) {
                when (currentPosition.face) {
                    is XYFront -> {
                        Up(0) to Position(currentPosition.x, 0, xzUp)
                    }

                    is XYBack -> {
                        Up(0) to Position(currentPosition.x, faceSize - 1, xzUp)
                    }

                    is YZRight -> {
                        Left(0) to Position(faceSize - 1, currentPosition.x, xzUp)
                    }

                    is YZLeft -> {
                        Right(0) to Position(0, currentPosition.x, xzUp)
                    }

                    is XZUp -> {
                        Down(0) to Position(currentPosition.x, 0, xyBack)
                    }

                    is XZDown -> {
                        Up(0) to Position(currentPosition.x, faceSize - 1, xyFront)
                    }
                }
            } else Left(0) to Position(currentPosition.x, currentPosition.y - 1, currentPosition.face)

            return if (option.second.face.lines()[option.second.y][option.second.x] == '#') fallback
            else option
        }

        // OK
        private fun down(
            currentPosition: Position,
            fallback: Pair<Direction, Position> = Right(0) to currentPosition
        ): Pair<Direction, Position> {
            val option = if (currentPosition.y == faceSize - 1) {
                when (currentPosition.face) {
                    is XYFront -> {
                        Down(0) to Position(currentPosition.x, 0, xzDown)
                    }

                    is XYBack -> {
                        Up(0) to Position(currentPosition.x, faceSize - 1, xzDown)
                    }

                    is YZRight -> {
                        Left(0) to Position(faceSize - 1, currentPosition.x, xzDown)
                    }

                    is YZLeft -> {
                        Right(0) to Position(0, currentPosition.x, xzDown)
                    }

                    is XZUp -> {
                        Down(0) to Position(currentPosition.x, 0, xyFront)
                    }

                    is XZDown -> {
                        Up(0) to Position(currentPosition.x, faceSize - 1, xyBack)
                    }
                }
            } else Down(0) to Position(currentPosition.x, currentPosition.y + 1, currentPosition.face)

            return if (option.second.face.lines()[option.second.y][option.second.x] == '#') fallback
            else option
        }

        private fun startingPosition() =
            Position(0, 0, xyFront)
    }

    private sealed interface Direction {
        val step: Int
    }

    data class Left(override val step: Int) : Direction
    data class Right(override val step: Int) : Direction
    data class Up(override val step: Int) : Direction
    data class Down(override val step: Int) : Direction

    private sealed interface Face {
        fun lines(): List<String>
        fun print(): String =
            lines().map { line ->
                line.map { c ->
                    "$c"
                }.joinToString("")
            }.joinToString("\n")

    }

    private data class XYFront(val mapAsString: String) : Face {
        override fun toString() = "XYF"

        override fun lines(): List<String> = mapAsString.lines()
    }

    private data class XYBack(val mapAsString: String) : Face {
        override fun toString() = "XYB"

        override fun lines(): List<String> = mapAsString.lines()
    }

    private data class YZRight(val mapAsString: String) : Face {
        override fun toString() = "YZR"

        override fun lines(): List<String> = mapAsString.lines()
    }

    private data class YZLeft(val mapAsString: String) : Face {
        override fun toString() = "YZL"

        override fun lines(): List<String> = mapAsString.lines()
    }

    private data class XZUp(val mapAsString: String) : Face {
        override fun toString() = "XZU"

        override fun lines(): List<String> = mapAsString.lines()
    }

    private data class XZDown(val mapAsString: String) : Face {
        override fun toString() = "XZD"

        override fun lines(): List<String> = mapAsString.lines()
    }

    private data class Position(val x: Int, val y: Int, val face: Face)
}

fun main() {
    processStars(Day22::star1, Day22::star2)
}