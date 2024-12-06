package com.github.lemfi.adventofcode.stuff

data class Coordinate(val x: Int, val y: Int) {

    fun to(direction: Direction8, distance: Int = 1): Coordinate =
        when (direction) {
            Direction8.UP -> Coordinate(x, y - distance)
            Direction8.DOWN -> Coordinate(x, y + distance)
            Direction8.RIGHT -> Coordinate(x + distance, y)
            Direction8.LEFT -> Coordinate(x - distance, y)
            Direction8.UP_RIGHT -> Coordinate(x + distance, y - distance)
            Direction8.UP_LEFT -> Coordinate(x - distance, y - distance)
            Direction8.DOWN_LEFT -> Coordinate(x - distance, y + distance)
            Direction8.DOWN_RIGHT -> Coordinate(x + distance, y + distance)
        }

    fun to(direction: Direction4, distance: Int = 1): Coordinate =
        when (direction) {
            Direction4.UP -> Coordinate(x, y - distance)
            Direction4.DOWN -> Coordinate(x, y + distance)
            Direction4.RIGHT -> Coordinate(x + distance, y)
            Direction4.LEFT -> Coordinate(x - distance, y)
        }

    fun from(direction: Direction4, distance: Int = 1): Coordinate =
        when (direction) {
            Direction4.UP -> Coordinate(x, y + distance)
            Direction4.DOWN -> Coordinate(x, y - distance)
            Direction4.RIGHT -> Coordinate(x - distance, y)
            Direction4.LEFT -> Coordinate(x + distance, y)
        }

    fun neighbors4() = Direction4.entries.map { to(it) }

    fun neighbors8() = Direction8.entries.map { to(it) }
}

enum class Direction8 {
    UP, DOWN, LEFT, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT
}

enum class Direction4 {
    UP, DOWN, LEFT, RIGHT;

    fun toRight() = when (this) {
        UP -> RIGHT
        DOWN -> LEFT
        LEFT -> UP
        RIGHT -> DOWN
    }

    fun toLeft() = when (this) {
        UP -> LEFT
        DOWN -> RIGHT
        LEFT -> DOWN
        RIGHT -> UP
    }
}