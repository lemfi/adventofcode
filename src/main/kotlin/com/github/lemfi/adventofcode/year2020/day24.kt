package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.system.measureTimeMillis


fun main() {

    measureTimeMillis {
        Day24.star1(Day24.data)
            .apply { println("res: $this") }
    }.apply { println("   in ${this / 1000}s") }

    measureTimeMillis {

        Day24.star2(Day24.data)
            .apply { println("res: $this") }
    }.apply { println("   in ${this / 1000}s") }

}

object Day24 {

    fun star1(data: String): Int {

        return data.lines()
            .toDirections()
            .map { it.toSmallestPath() }
            .toFloor()
            .countBlackTiles()
    }

    fun star2(data: String): Int {

        return data.lines()
            .toDirections()
            .map { it.toSmallestPath() }
            .toFloor().apply { (1..100).forEach { flip() } }
            .countBlackTiles()
    }

    fun List<String>.toDirections() = map {
        it.replace("se", "-se-")
            .replace("sw", "-sw-")
            .replace("nw", "-nw-")
            .replace("ne", "-ne-")
            .replace("ew", "-e-w-")
            .replace("we", "-w-e-")
            .replace("ww", "-w-w-")
            .replace("ee", "-e-e-")
            .replace("--", "-")
            .split("-")
            .filterNot { it.isBlank() }
            .map { Direction.valueOf(it.toUpperCase()) }
    }

    fun List<Direction>.toSmallestPath(): List<Direction> = toMutableList().apply {

        (0 until minOf(count { it == Direction.W }, count { it == Direction.SE })).forEach {
            remove(Direction.W)
            remove(Direction.SE)
            add(Direction.SW)
        }

        (0 until minOf(count { it == Direction.W }, count { it == Direction.NE })).forEach {
            remove(Direction.W)
            remove(Direction.NE)
            add(Direction.NW)
        }

        (0 until minOf(count { it == Direction.E }, count { it == Direction.SW })).forEach {
            remove(Direction.E)
            remove(Direction.SW)
            add(Direction.SE)
        }

        (0 until minOf(count { it == Direction.E }, count { it == Direction.NW })).forEach {
            remove(Direction.E)
            remove(Direction.NW)
            add(Direction.NE)
        }

        (0 until minOf(count { it == Direction.NE }, count { it == Direction.SW })).forEach {
            remove(Direction.SW)
            remove(Direction.NE)
        }

        (0 until minOf(count { it == Direction.SE }, count { it == Direction.NW })).forEach {
            remove(Direction.NW)
            remove(Direction.SE)
        }

        (0 until minOf(count { it == Direction.NW }, count { it == Direction.SW })).forEach {
            remove(Direction.NW)
            remove(Direction.SW)
            add(Direction.W)
        }

        (0 until minOf(count { it == Direction.NE }, count { it == Direction.SE })).forEach {
            remove(Direction.NE)
            remove(Direction.SE)
            add(Direction.E)
        }

        (0 until minOf(count { it == Direction.E }, count { it == Direction.W })).forEach {
            remove(Direction.E)
            remove(Direction.W)
        }
    }.let {
        if (this.size != it.size) it.toSmallestPath() else it.sortedBy { it.name }
    }

    fun List<List<Direction>>.toFloor() = Floor(this)

    class Floor(val map: List<List<Direction>>) {

        private val blackTiles = mutableSetOf<List<Direction>>()

        init {
            map.forEach { if (blackTiles.contains(it)) blackTiles.remove(it) else blackTiles.add(it) }
        }

        fun flip() {

            val toAdd = blackTiles.flatMap { it.toNeighboors() }
                .distinct()
                .filterNot { blackTiles.contains(it) }
                .filter { it.countBlackNeighboors() == 2 }

            val toRemove = blackTiles.filter { it.countBlackNeighboors().run { this == 0 || this > 2 } }

            blackTiles.removeAll(toRemove)
            blackTiles.addAll(toAdd)

        }

        private fun List<Direction>.toNeighboors() = listOf(this + listOf(Direction.SW), this + listOf(Direction.SE), this + listOf(Direction.NE), this + listOf(Direction.NW), this + listOf(Direction.E), this + listOf(Direction.W))
            .map { it.toSmallestPath() }

        private fun List<Direction>.countBlackNeighboors() = toNeighboors().count { blackTiles.contains(it) }

        fun countBlackTiles() = blackTiles.count()
    }


    enum class Direction {
        E, SE, SW, W, NW, NE
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day24.txt").readAllBytes().toString(Charset.defaultCharset()) }

}