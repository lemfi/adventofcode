package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day17.star1(Day17.data)
        .apply { println("res: $this") }

    Day17.star2(Day17.data)
        .apply { println("res: $this") }

}

object Day17 {

    fun star1(data: String): Int {

        return data
            .toActive3DCoordinates()
            .toCube()
            .also {
                (1..6).forEach { _ ->
                    it.mutate()
                }
            }
            .actives.size
    }

    fun star2(data: String): Int {

        return data
            .toActive4DCoordinates()
            .toCube()
            .also {
                (1..6).forEach { _ ->
                    it.mutate()
                }
            }
            .actives.size
    }

    data class Cube(val actives: MutableList<Coordinates>) {

        fun mutate() {
            val toSwithOff = actives.filter { it.neighboors().count { it.isActive() }.let { it != 2 && it != 3 } }
            val toSwithOn = actives.flatMap { it.neighboors() }.filterNot { it.isActive() }.groupBy { it }.filter { it.value.size == 3 }

            actives.removeAll(toSwithOff)
            actives.addAll(toSwithOn.keys)
        }

        private fun Coordinates.isActive() = actives.contains(this)
    }

    fun String.toActive3DCoordinates() = mutableListOf<Coordinates.`3DCoordinates`>().also { res ->
        lines().forEachIndexed { y, line ->
            line.toList().forEachIndexed { x, value ->
                if (value == '#') res.add(Coordinates.`3DCoordinates`(x, y, 0))
            }
        }
    }

    fun String.toActive4DCoordinates() = mutableListOf<Coordinates.`4DCoordinates`>().also { res ->
        lines().forEachIndexed { y, line ->
            line.toList().forEachIndexed { x, value ->
                if (value == '#') res.add(Coordinates.`4DCoordinates`(x, y, 0, 0))
            }
        }
    }

    sealed class Coordinates {
        abstract fun neighboors(): List<Coordinates>

        data class `3DCoordinates`(val x: Int, val y: Int, val z: Int): Coordinates() {

            override fun neighboors() = (x - 1 .. x + 1).flatMap { x ->
                (y - 1 .. y + 1).flatMap { y ->
                    (z - 1 .. z + 1).flatMap { z -> listOf(`3DCoordinates`(x, y, z)) }
                }
            }.filterNot { it == this }
        }


        data class `4DCoordinates`(val x: Int, val y: Int,  val z: Int, val w: Int): Coordinates() {

            override fun neighboors() = (x - 1 .. x + 1).flatMap { x ->
                (y - 1 .. y + 1).flatMap { y ->
                    (z - 1 .. z + 1).flatMap { z ->
                        (w - 1 .. w + 1).flatMap { w -> listOf(`4DCoordinates`(x, y, z, w)) }
                    }
                }
            }.filterNot { it == this }
        }
    }

    @Suppress("unchecked_cast")
    @JvmName("3dToCube")
    fun MutableList<Coordinates.`3DCoordinates`>.toCube() = Cube(this as MutableList<Coordinates>)
    @Suppress("unchecked_cast")
    @JvmName("4dToCube")
    fun MutableList<Coordinates.`4DCoordinates`>.toCube() = Cube(this as MutableList<Coordinates>)

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day17.txt").readAllBytes().toString(Charset.defaultCharset()) }

}