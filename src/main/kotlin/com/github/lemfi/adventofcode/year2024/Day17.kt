package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import kotlin.math.pow
import kotlin.properties.Delegates

object Day17 {

    private fun String?.toData() = this ?: data(17)

    fun star1(input: String?) = input.toData()
        .split("\n\n")
        .let { (registers, program) ->
            registers.split("\n").map { it.substringAfter(": ").toInt() } to
                    program
                        .substringAfter(": ").split(",")
                        .map { it.toInt() }
        }.let { (registers, program) ->

            var (A, B, C) = registers
            var pointer = 0
            var jumbAfterComputing: Int
            val outpout = mutableListOf<Int>()

            fun Int.combo() =
                when (this) {
                    0, 1, 2, 3 -> this
                    4 -> A
                    5 -> B
                    6 -> C
                    7 -> error("... combo error 7 ...")
                    else -> error("... combo error ...")
                }

            fun getOperation(opcode: Int): (Int) -> Unit {
                return when (opcode) {
                    0 -> { it -> A = (A / (2.0.pow(it.combo().toDouble()))).toInt() }
                    1 -> { it -> B = B xor it }
                    2 -> { it -> B = it.combo() % 8 }
                    3 -> { it ->
                        if (A != 0) {
                            pointer = it
                            jumbAfterComputing = 0
                        }
                    }

                    4 -> { _ -> B = B xor C }
                    5 -> { it -> outpout.add((it.combo() % 8)) }
                    6 -> { it -> B = (A / (2.0.pow(it.combo().toDouble()))).toInt() }
                    7 -> { it -> C = (A / (2.0.pow(it.combo().toDouble()))).toInt() }

                    else -> error("unknown opcode: $opcode")
                }
            }

            while (true) {
                if (pointer >= program.size) break
                jumbAfterComputing = 2
                val opcode = program[pointer]
                val operand = program[pointer + 1]
                getOperation(opcode).invoke(operand)
                pointer += jumbAfterComputing
            }

            outpout.joinToString(",")
        }

    fun star2(input: String?) = input.toData()
        .split("\n\n")
        .let { (registers, program) ->
            registers.split("\n").map { it.substringAfter(": ").toInt() } to
                    program
                        .substringAfter(": ").split(",")
                        .map { it.toLong() }
        }
        .let { (_, program) ->

            val operations = program.windowed(2, 2)

            var lstPreviousA = listOf(0L)
            program.reversed().forEach { expectedOutput ->
                lstPreviousA = lstPreviousA.flatMap { previousA ->
                    (0L + previousA * 8..7L + previousA * 8).filter { possibleA ->

                        var A = possibleA
                        var B = 0L
                        var C = 0L
                        var output by Delegates.notNull<Long>()

                        fun Long.combo() =
                            when (this) {
                                0L, 1L, 2L, 3L -> this
                                4L -> A
                                5L -> B
                                6L -> C
                                7L -> error("... combo error 7 ...")
                                else -> error("... combo error ...")
                            }

                        fun Long.toOperation(): (Long) -> Unit =
                            when (this) {
                                0L -> { it -> A = (A / (2.0.pow(it.combo().toInt()))).toLong() }
                                1L -> { it -> B = B xor it }
                                2L -> { it -> B = it.combo() % 8 }
                                3L -> { _ -> } // loops are ignored now
                                4L -> { _ -> B = B xor C }
                                5L -> { it -> output = (it.combo() % 8) }
                                6L -> { it -> B = (A / (2.0.pow(it.combo().toInt()))).toLong() }
                                7L -> { it -> C = (A / (2.0.pow(it.combo().toInt()))).toLong() }

                                else -> error("unknown opcode: $this")
                            }

                        operations.forEach { (operation, value) -> operation.toOperation().invoke(value) }

                        output == expectedOutput
                    }
                }
            }
            lstPreviousA.minOf { it }
        }
}

fun main() {
    processStars(Day17::star1, Day17::star2)
}