package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day1 {

    private fun String?.toData() = this ?: data(1)

    fun star1(input: String?) = input
        .toElfes()
        .maxOf { elfe -> elfe.supply() }

    fun star2(input: String?) = input
        .toElfes()
        .map { elfe -> elfe.supply() }
        .sortedDescending()
        .take(3)
        .sum()

    private fun String?.toElfes() = toData().split("\n\n")

    private fun String.supply() =
        split("\n")
            .map { it.toInt() }
            .reduce { acc, value -> acc + value }
}

fun main() {
    processStars(Day1::star1, Day1::star2)
}