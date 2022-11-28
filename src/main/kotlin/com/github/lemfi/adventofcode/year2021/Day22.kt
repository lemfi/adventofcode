package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day22 {

    private fun String?.toData() = this ?: data(22)

    fun star1(input: String?) = input.toData().lines().map {
        if (it.startsWith("on")) {
            OnInstruction(
                Cuboid(
                    minX = it.substringAfter("x=").substringBefore("..").toLong(),
                    maxX = it.substringAfter("..").substringBefore(",y=").toLong(),
                    minY = it.substringAfter("y=").substringBefore("..").toLong(),
                    maxY = it.substringAfter("..").substringAfter("..").substringBefore(",z=").toLong(),
                    minZ = it.substringAfter("z=").substringBefore("..").toLong(),
                    maxZ = it.substringAfterLast("..").toLong(),
                )
            )
        } else {
            OffInstruction(
                Cuboid(
                    minX = it.substringAfter("x=").substringBefore("..").toLong(),
                    maxX = it.substringAfter("..").substringBefore(",y=").toLong(),
                    minY = it.substringAfter("y=").substringBefore("..").toLong(),
                    maxY = it.substringAfter("..").substringAfter("..").substringBefore(",z=").toLong(),
                    minZ = it.substringAfter("z=").substringBefore("..").toLong(),
                    maxZ = it.substringAfterLast("..").toLong(),
                )
            )
        }
    }.let { instructions ->
        instructions
            .filterNot {
                it.cuboid.minX < -50
                        || it.cuboid.maxX > 50
                        || it.cuboid.minY < -50
                        || it.cuboid.maxY > 50
                        || it.cuboid.minZ < -50
                        || it.cuboid.maxZ > 50
            }
            .fold(mutableSetOf<Cuboid>()) { acc, instruction ->

                if (instruction is OnInstruction) {

                    val intersections = acc.filter { it intersects instruction.cuboid }.toSet()
                    if (intersections.isEmpty()) acc.apply { add(instruction.cuboid) }
                    else {
                        acc.apply {

                            addAll(intersections.fold(setOf(instruction.cuboid)) { cut, inter ->
                                cut.flatMap { it - (inter intersection it) }.toSet()
                            })
                        }.also { t ->
                            do {
                                val inter = t.firstOrNull { c ->
                                    t.any { it != c && it intersects c }
                                }
                                inter?.let { internotnull ->
                                    val other = t.first { it != internotnull && it intersects internotnull }
                                    t.remove(other)
                                    t.addAll(other - (other intersection internotnull))
                                }
                            } while (inter != null)
                        }.also { universe ->

                            universe.lastOrNull { c ->
                                universe.any { it != c && c intersects it }
                            }.also {
                                if (it != null) error("Hmm ...")
                            }
                        }
                    }
                }
                if (instruction is OffInstruction) {
                    val intersections = acc.filter { it intersects instruction.cuboid }.toSet()
                    if (intersections.isNotEmpty()) {
                        acc.removeAll(intersections)
                        acc.apply {
                            intersections.forEach {
                                addAll(it - (it intersection instruction.cuboid))
                            }
                        }
                    }
                }
                acc
            }
            .sumOf { it.cubes }
    }

    sealed class Instruction {
        abstract val cuboid: Cuboid
    }

    data class OnInstruction(
        override val cuboid: Cuboid
    ) : Instruction() {
    }

    data class OffInstruction(
        override val cuboid: Cuboid
    ) : Instruction()

    fun star2(input: String?) = input.toData().lines().map {
        if (it.startsWith("on")) {
            OnInstruction(
                Cuboid(
                    minX = it.substringAfter("x=").substringBefore("..").toLong(),
                    maxX = it.substringAfter("..").substringBefore(",y=").toLong(),
                    minY = it.substringAfter("y=").substringBefore("..").toLong(),
                    maxY = it.substringAfter("..").substringAfter("..").substringBefore(",z=").toLong(),
                    minZ = it.substringAfter("z=").substringBefore("..").toLong(),
                    maxZ = it.substringAfterLast("..").toLong(),
                )
            )
        } else {
            OffInstruction(
                Cuboid(
                    minX = it.substringAfter("x=").substringBefore("..").toLong(),
                    maxX = it.substringAfter("..").substringBefore(",y=").toLong(),
                    minY = it.substringAfter("y=").substringBefore("..").toLong(),
                    maxY = it.substringAfter("..").substringAfter("..").substringBefore(",z=").toLong(),
                    minZ = it.substringAfter("z=").substringBefore("..").toLong(),
                    maxZ = it.substringAfterLast("..").toLong(),
                )
            )
        }
    }.let { instructions ->
        println(instructions.size)
        instructions
            .foldIndexed(mutableSetOf<Cuboid>()) { i, acc, instruction ->
                if (instruction is OnInstruction) {

                    val intersections = acc.filter { it intersects instruction.cuboid }.toSet()
                    if (intersections.isEmpty()) acc.apply { add(instruction.cuboid) }
                    else {
                        acc.apply {

                            addAll(intersections.fold(setOf(instruction.cuboid)) { cut, inter ->
                                cut.flatMap { it - (inter intersection it) }.toSet()
                            })
                        }.also { t ->
                            do {
                                val inter = t.firstOrNull { c ->
                                    t.any { it != c && it intersects c }
                                }
                                inter?.let { internotnull ->
                                    val other = t.first { it != internotnull && it intersects internotnull }
                                    t.remove(other)
                                    t.addAll(other - (other intersection internotnull))
                                }
                            } while (inter != null)
                        }.also { universe ->

                            universe.lastOrNull { c ->
                                universe.any { it != c && c intersects it }
                            }.also {
                                if (it != null) error("Hmm ...")
                            }
                        }
                    }
                }
                if (instruction is OffInstruction) {
                    val intersections = acc.filter { it intersects instruction.cuboid }.toSet()
                    if (intersections.isNotEmpty()) {
                        acc.removeAll(intersections)
                        acc.apply {
                            intersections.forEach {
                                addAll(it - (it intersection instruction.cuboid))
                            }
                        }
                    }
                }
                acc
            }
            .sumOf { it.cubes }
    }

    data class Cuboid(val minX: Long, val maxX: Long, val minY: Long, val maxY: Long, val minZ: Long, val maxZ: Long) {
        val cubes =
            abs(maxX - minX + 1L) * abs(maxY - minY + 1L) * abs(maxZ - minZ + 1L)

        infix fun intersects(cuboid: Cuboid): Boolean = maxX >= cuboid.minX
                && minX <= cuboid.maxX
                && maxY >= cuboid.minY
                && minY <= cuboid.maxY
                && maxZ >= cuboid.minZ
                && minZ <= cuboid.maxZ

        infix fun intersection(cuboid: Cuboid): Cuboid? =
            if (this intersects cuboid) {
                Cuboid(
                    minX = max(minX, cuboid.minX),
                    maxX = min(maxX, cuboid.maxX),
                    minY = max(minY, cuboid.minY),
                    maxY = min(maxY, cuboid.maxY),
                    minZ = max(minZ, cuboid.minZ),
                    maxZ = min(maxZ, cuboid.maxZ),
                )
            } else null

        operator fun plus(cuboid: Cuboid): Set<Cuboid> = setOf(
            *(cuboid - (this intersection cuboid)).toTypedArray(),
            this
        )
            .filterNot { it.minX > it.maxX || it.minY > it.maxY || it.minZ > it.maxZ }
            .toMutableSet().also { t ->
                do {
                    val inter = t.firstOrNull { c ->
                        t.any { it != c && it intersects c }
                    }
                    inter?.let { internotnull ->
                        val other = t.first { it != internotnull && it intersects internotnull }
                        t.remove(other)
                        t.addAll(other - (other intersection internotnull))
                    }
                } while (inter != null)
            }

        operator fun minus(cuboid: Cuboid?): Set<Cuboid> =
            if (cuboid == null) setOf(this) else {

                setOf(
                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    //
                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    //
                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = minZ,
                        maxZ = cuboid.minZ - 1,
                    ),
                    //
                    //
                    //

                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    //
                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    //
                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = cuboid.minZ,
                        maxZ = cuboid.maxZ,
                    ),

                    //

                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = minY,
                        maxY = cuboid.minY - 1,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    //
                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = cuboid.minY,
                        maxY = cuboid.maxY,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    //
                    Cuboid(
                        minX = minX,
                        maxX = cuboid.minX - 1,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.minX,
                        maxX = cuboid.maxX,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    ),
                    Cuboid(
                        minX = cuboid.maxX + 1,
                        maxX = maxX,
                        minY = cuboid.maxY + 1,
                        maxY = maxY,
                        minZ = cuboid.maxZ + 1,
                        maxZ = maxZ,
                    )
                )
                    .filterNot { it == cuboid || it.minX > it.maxX || it.minY > it.maxY || it.minZ > it.maxZ }
                    .toSet()

            }

    }
}

fun main() {
    processStars(Day22::star1, Day22::star2)
}
