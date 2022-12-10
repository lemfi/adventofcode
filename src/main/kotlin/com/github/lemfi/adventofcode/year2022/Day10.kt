package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day10 {

    private fun String?.toData() = this ?: data(10)

    fun star1(input: String?) = input
        .readOperations()
        .foldIndexed(1 to 0) { index, (X, sum), operation ->
            operation.compute(X) to if ((index + 1) in 20..220 step 40) sum + X * (index + 1) else sum
        }.second

    fun star2(input: String?) = input
        .readOperations()
        .foldIndexed(1 to "") { index, (X, output), operation ->
            operation.compute(X) to if (index % 40 in X - 1..X + 1) "$output#" else "$output "
        }.second
        .windowed(40, 40)
        .joinToString("\n")
        .let {
            "\n$it" // because my output is shifted on first line, nothing to do with resolution, just for display ^^
        }

    private fun String?.readOperations() = toData()
        .lines()
        .flatMap {
            if (it == "noop") listOf(Noop)
            else listOf(Noop, Addx(it.split(" ").last().toInt()))
        }

    sealed interface Operation {
        fun compute(x: Int): Int
    }

    object Noop : Operation {
        override fun compute(x: Int) = x
    }

    data class Addx(val value: Int) : Operation {
        override fun compute(x: Int) = x + value
    }
}

fun main() {
    processStars(Day10::star1, Day10::star2)
}