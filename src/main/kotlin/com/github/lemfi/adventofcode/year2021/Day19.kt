package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs
import kotlin.math.max

object Day19 {

    private fun String?.toData() = this ?: data(19)

    private fun String?.prepareData() = toData().split("\n\n").mapIndexed { index, scanner ->
        Scanner(
            name = "Scanner $index",
            beacons = scanner
                .lines()
                .drop(1)
                .map {
                    it.split(",")
                        .let { (x, y, z) -> Position(x.toInt(), y.toInt(), z.toInt()) }
                }
        )
    }

    fun star1(input: String?) = input
        .prepareData()
        .discoverScannersAlignment()
        .fold(mutableSetOf<Position>()) { acc, p ->
            acc.apply { addAll(p.beaconsRotated?.map { p.position + it } ?: emptyList()) }
        }.size

    fun star2(input: String?) = input
        .prepareData()
        .discoverScannersAlignment()
        .let { scanners ->
            scanners.fold(Long.MIN_VALUE) { max, scanner1 ->
                scanners.fold(max) { tryMax, scanner2 ->
                    max(scanner1.position.distance(scanner2.position).run { x + y + z }.toLong(), tryMax)
                }
            }
        }


    private fun List<Scanner>.discoverScannersAlignment() = also { scanners ->
        scanners.first().beaconsRotated = scanners.first().beacons
        while (scanners.any { !it.rotationFound() }) {
            val found = scanners.filter { it.rotationFound() }
            val notFound = scanners.filter { !it.rotationFound() }

            notFound.forEach { nf ->
                found.forEach { f ->
                    if (!nf.rotationFound())
                        nf.rotations.forEach { rotation ->
                            f.beaconsRotated!!.flatMap { b1 ->
                                rotation.map { b2 ->
                                    b1 - b2
                                }
                            }.let { vectors ->

                                val matching = vectors
                                    .groupBy { it }
                                    .filter { it.value.size >= 12 }
                                    .keys
                                    .firstOrNull()

                                if (matching != null) {
                                    nf.beaconsRotated = rotation
                                    nf.position = f.position + matching
                                }

                            }
                        }
                }
            }
        }
    }

    data class Position(val x: Int, val y: Int, val z: Int) {
        override fun toString() = "($x,$y,$z)"

        fun distance(position: Position) = Position(abs(x - position.x), abs(y - position.y), abs(z - position.z))
        operator fun minus(position: Position) = Position(x - position.x, y - position.y, z - position.z)
        operator fun plus(position: Position) = Position(x + position.x, y + position.y, z + position.z)

        val rotations: List<Position> by lazy {
            listOf(

                Position(x, y, z),
                Position(-y, x, z),
                Position(y, -x, z),
                Position(-x, -y, z),

                Position(-x, y, -z),
                Position(y, x, -z),
                Position(x, -y, -z),
                Position(-y, -x, -z),

                Position(x, -z, y),
                Position(z, x, y),
                Position(-x, z, y),
                Position(-z, -x, y),

                Position(x, z, -y),
                Position(z, -x, -y),
                Position(-x, -z, -y),
                Position(-z, x, -y),

                Position(-z, y, x),
                Position(y, z, x),
                Position(z, -y, x),
                Position(-y, -z, x),

                Position(z, y, -x),
                Position(y, -z, -x),
                Position(-z, -y, -x),
                Position(-y, z, -x),

                )
        }
    }

    data class Scanner(val name: String, var position: Position = Position(0, 0, 0), val beacons: List<Position>) {

        var beaconsRotated: List<Position>? = null
        fun rotationFound(): Boolean = beaconsRotated != null

        val rotations = (0 until 24).map { rotation -> beacons.map { it.rotations[rotation] } }
    }

}

fun main() {
    processStars(Day19::star1, Day19::star2)
}
