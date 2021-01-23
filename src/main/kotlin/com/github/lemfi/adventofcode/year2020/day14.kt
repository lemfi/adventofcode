package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day14.star1(Day14.data)
        .apply { println("res: $this") }

    Day14.star2(Day14.data)
        .apply { println("res: $this") }

}

object Day14 {

    fun star1(data: String): Long {

        return data.play(Computer::rules1)
    }

    fun star2(data: String): Long {

        return data.play(Computer::rules2)
    }

    fun String.play(rule: Computer.(MutableMap<Long, Long>)->MutableMap<Long, Long>): Long {
        return split("mask = ")
            .filterNot { it.isBlank() }
            .map {
                it.toComputer()
            }.let {
                it.fold(mutableMapOf<Long, Long>()) {
                        res, computer -> computer.rule(res)
                }
            }.values.sum()
    }

    private fun String.toComputer() =
        lines().filterNot { it.isBlank() }
            .let {
                Computer(
                    mask = it.first().toList(),
                    operations = it.drop(1)
                        .map {
                            it.split(" = ").let { it.first().substringAfter("mem[").substringBefore("]").toInt() to it.last().toInt() }
                        }
                )
            }

    data class Computer(
        val mask: List<Char>,
        val operations: List<Pair<Int, Int>>
    ) {
        fun rules1(current: MutableMap<Long, Long>): MutableMap<Long, Long> {
            operations.forEach { op ->
                Integer.toBinaryString(op.second).padStart(36, '0').toList()
                    .zip(mask)
                    .map {
                        when (it.second) {
                            '0' -> 0L
                            '1' -> 1L
                            else -> it.first.toString().toLong()
                        }
                    }
                    .joinToString("").toLong(2)
                    .let {
                        current.put(op.first.toLong(), it)
                    }
            }
            return current
        }

        fun rules2(current: MutableMap<Long, Long>): MutableMap<Long, Long> {
            operations.forEach { op ->
                Integer.toBinaryString(op.first).padStart(36, '0').toList()
                    .zip(mask)
                    .map {
                        when (it.second) {
                            '0' -> setOf(it.first.toString().toLong())
                            '1' -> setOf(1L)
                            else -> setOf(1L, 0L)
                        }
                    }
                    .toAddresses()
                    .forEach {
                        it.joinToString("").toLong(2)
                            .let {
                                current.put(it, op.second.toLong())
                            }

                    }
            }
            return current
        }
    }

    fun List<Set<Long>>.toAddresses(current: List<Long> = emptyList()): List<List<Long>> {

        if (this.isEmpty()) return listOf(current)

        return if (this.first().size == 1) drop(1).toAddresses(current + first().toList()[0])
        else first().toList().let {
            listOf(
                drop(1).toAddresses(current + listOf(it.first())),
                drop(1).toAddresses(current + listOf(it.last()))
            )
        }.flatten()
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day14.txt").readAllBytes().toString(Charset.defaultCharset()) }

}