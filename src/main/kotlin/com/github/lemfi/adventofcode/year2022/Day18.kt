package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day18 {

    private fun String?.toData() = this ?: data(18)

    fun star1(input: String?) = input
        .parse()
        .exposed()

    //    fun star2(input: String?) = input
    //        .parse()
    //        .let { cubes ->
    //            val minX = cubes.minOf { it.x }
    //            val maxX = cubes.maxOf { it.x }
    //            val minY = cubes.minOf { it.y }
    //            val maxY = cubes.maxOf { it.y }
    //            val minZ = cubes.minOf { it.z }
    //            val maxZ = cubes.maxOf { it.z }
    //
    //            cubes.exposed() -
    //                    (minX..maxX).flatMap { x ->
    //                        (minY..maxY).flatMap { y ->
    //                            (minZ..maxZ).mapNotNull { z ->
    //                                val isOut =
    //                                    Position(x, y, z).isOut(cubes, emptyList())
    //                                if (cubes.contains(Position(x, y, z)) || isOut) {
    //                                    println(Position(x, y, z).toString() + " is OUT")
    //                                    null
    //                                } else {
    //                                    println(Position(x, y, z).toString() + " is IN")
    //                                    Position(x, y, z)
    //                                }
    //                            }
    //                        }
    //                    }.let {
    //                        it
    //                            .exposed()
    //                    }
    //
    //        }

    fun star2(input: String?) = input
        .parse()
        .let { cubes ->
            val minX = cubes.minOf { it.x }
            val maxX = cubes.maxOf { it.x }
            val minY = cubes.minOf { it.y }
            val maxY = cubes.maxOf { it.y }
            val minZ = cubes.minOf { it.z }
            val maxZ = cubes.maxOf { it.z }

            val out = mutableSetOf<Position>()

            (1..20).forEach { _ ->
                (minX..maxX).forEach { x ->
                    (minY..maxY).forEach { y ->
                        (minZ..maxZ).forEach { z ->
                            if ((z == minZ || z == maxZ || x == maxX || x == minX || y == minY || y == minZ)
                                && !cubes.contains(Position(x, y, z))
                            ) {
                                out.add(Position(x, y, z))
                            } else if (!cubes.contains(Position(x, y, z)) && Position(x, y, z).neighbors()
                                    .any { out.contains(it) }
                            ) {
                                out.add(Position(x, y, z))
                            }
                        }
                    }
                }
            }

            cubes.exposed() -
                    (minX..maxX).flatMap { x ->
                        (minY..maxY).flatMap { y ->
                            (minZ..maxZ).mapNotNull { z ->
                                if (cubes.contains(Position(x, y, z)) || out.contains(
                                        Position(
                                            x,
                                            y,
                                            z
                                        )
                                    )
                                ) null else Position(
                                    x,
                                    y,
                                    z
                                )
                            }
                        }
                    }.exposed()
        }

    private fun Position.isOut(cubes: List<Position>, exclusions: List<Position>): Boolean {
        val outX = {
            cubes.none { (it.x > x) && it.y == y && it.z == z } ||
                    cubes.none { (it.x < x) && it.y == y && it.z == z }
        }
        val outY = {
            cubes.none { (it.y > y) && it.x == x && it.z == z } ||
                    cubes.none { (it.y < y) && it.x == x && it.z == z }
        }
        val outZ = {
            cubes.none { (it.z > z) && it.x == x && it.y == y } ||
                    cubes.none { (it.z < z) && it.x == x && it.y == y }
        }

        return !cubes.contains(Position(x, y, z))
                && (outX() || outY() || outZ() || neighbors(exclusions).fold(exclusions to false) { (exclusions, out), nb ->
            if (out) exclusions to out else
                exclusions + listOf(this) to nb.isOut(cubes, exclusions + listOf(this))
        }.second)
    }

    private fun Position.neighbors(exclusion: List<Position> = emptyList()): List<Position> {
        return listOf(
            Position(x + 1, y, z), Position(x - 1, y, z),
            Position(x, y + 1, z), Position(x, y - 1, z),
            Position(x, y, z + 1), Position(x, y, z - 1),
        ).filterNot { exclusion.contains(it) }
    }

    private fun String?.parse() = toData()
        .lines()
        .map { it.split(",").let { (x, y, z) -> Position(x.toInt(), y.toInt(), z.toInt()) } }

    private fun List<Position>.exposed() =
        fold(0) { exposed, cube ->
            exposed + 6 - count {
                (it.touchingX(cube)) || (it.touchingY(cube)) || (it.touchingZ(cube))
            }
        }

    private fun Position.touchingZ(
        cube: Position
    ) = (z == cube.z + 1 || z == cube.z - 1) && x == cube.x && y == cube.y

    private fun Position.touchingY(
        cube: Position
    ) = (y == cube.y + 1 || y == cube.y - 1) && x == cube.x && z == cube.z

    private fun Position.touchingX(
        cube: Position
    ) = (x == cube.x + 1 || x == cube.x - 1) && y == cube.y && z == cube.z

    private data class Position(val x: Int, val y: Int, val z: Int)
}

fun main() {
    processStars(Day18::star1, Day18::star2)
}