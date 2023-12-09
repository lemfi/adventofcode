package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day09 {

    private fun String?.toData() = this ?: data(9)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .sumOf { it.toNextVal() }

    fun star2(input: String?) = input
        .toData()
        .lines()
        .sumOf { it.toPrevVal() }

    private fun String.toNextVal() = toSteps()
        .reduce { prev, step ->
            step.toMutableList()
                .apply { add(step.last() + prev.last()) }

        }.last()

    private fun String.toPrevVal() = toSteps()
        .reduce { prev, step ->
            step.toMutableList()
                .apply { add(0, step.first() - prev.first()) }
        }.first()

    private fun String.toSteps() =
        Regex("""-?\d+""").findAll(this).map { it.value.toLong() }.toList()
            .let { initial ->
                val steps = mutableListOf(initial)
                var source = initial
                do {
                    source = source.windowed(2, 1).map { (one, two) -> two - one }
                    steps.add(0, source)
                } while (!source.all { it == 0L })
                steps
            }
}

fun main() {
    processStars(Day09::star1, Day09::star2)
}
