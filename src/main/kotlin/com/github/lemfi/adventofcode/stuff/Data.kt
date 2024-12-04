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
}

enum class Direction8 {
    UP, DOWN, LEFT, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT
}