package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day8 {

    private fun String?.toData() = this ?: data(8)

    fun star1(input: String?) = input.toData().lines()
        .flatMap { it.split(" | ").last().split(" ") }
        .count { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7 }

    fun star2(input: String?) = input.prepareData()

        .sumOf { (please, `these numbers`) ->
            please decode `these numbers`
        }

    data class Number(val wires: Set<Char>) {
        constructor(wiresAsString: String) : this(wiresAsString.toSet())

        val length = wires.size

        var representation: Int? = null

        override fun toString() = representation?.toString() ?: "X"
    }

    private fun String?.prepareData() = toData().lines().map { line ->
        line.split(" | ")
            .let { (numbers, toDecode) ->
                Pair(
                    first = numbers.split(" ").map { Number(it) }.toMutableList(),
                    second = toDecode.split(" ").map { Number(it) }.toMutableList()
                )
            }
    }

    private infix fun MutableList<Number>.decode(toDecode: MutableList<Number>) =
        apply {

            first { it.length == 2 }.apply { representation = 1 }
            first { it.length == 4 }.apply { representation = 4 }
            first { it.length == 3 }.apply { representation = 7 }
            first { it.length == 7 }.apply { representation = 8 }

            filter { it.representation == null }
                .filter { it.length == 5 }
                .first { it `has its display included in` display(1) }
                .apply { representation = 3 }

            filter { it.representation == null }
                .filter { it.length == 6 }
                .first { !(it `has its display included in` display(7)) }
                .apply { representation = 6 }

            filter { it.representation == null }
                .filter { it.length == 6 }
                .first { it `has its display included in` display(4) }
                .apply { representation = 9 }

            filter { it.representation == null }
                .first { it.length == 6 }
                .apply { representation = 0 }

            filter { it.representation == null }
                .filter { it.length == 5 }
                .first { display(6) `has its display included in` it }
                .apply { representation = 5 }

            filter { it.representation == null }
                .first { it.length == 5 }
                .apply { representation = 2 }

        }.run {
            toDecode.map { numberToDecode ->
                first { it.wires == numberToDecode.wires }
            }
        }.joinToString("").toInt()

    private infix fun Number.`has its display included in`(display: Number) = wires.containsAll(display.wires)
    private fun List<Number>.display(number: Int) = first { it.representation == number }

}

fun main() {
    processStars(Day8::star1, Day8::star2)
}
