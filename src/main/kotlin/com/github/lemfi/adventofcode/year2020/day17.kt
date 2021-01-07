package com.github.lemfi.adventofcode.year2020

fun day17_1(data: String): Int {

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

fun day17_2(data: String): Int {

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

fun String.toActive3DCoordinates() = mutableListOf<`3DCoordinates`>().also { res ->
    lines().forEachIndexed { y, line ->
        line.toList().forEachIndexed { x, value ->
            if (value == '#') res.add(`3DCoordinates`(x, y, 0))
        }
    }
}

fun String.toActive4DCoordinates() = mutableListOf<`4DCoordinates`>().also { res ->
    lines().forEachIndexed { y, line ->
        line.toList().forEachIndexed { x, value ->
            if (value == '#') res.add(`4DCoordinates`(x, y, 0, 0))
        }
    }
}

sealed class Coordinates {
    abstract fun neighboors(): List<Coordinates>
}

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

@Suppress("unchecked_cast")
@JvmName("3dToCube")
fun MutableList<`3DCoordinates`>.toCube() = Cube(this as MutableList<Coordinates>)
@Suppress("unchecked_cast")
@JvmName("4dToCube")
fun MutableList<`4DCoordinates`>.toCube() = Cube(this as MutableList<Coordinates>)

fun main() {

    day17_1(day17data)
            .apply { println("res: $this") }

    day17_2(day17data)
            .apply { println("res: $this") }

}

val day17data = """.##..#.#
#...##.#
##...#.#
.##.##..
...#.#.#
.##.#..#
...#..##
###..##."""
