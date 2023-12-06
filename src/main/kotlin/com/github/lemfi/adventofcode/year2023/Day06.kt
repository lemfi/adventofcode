package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day06 {

    private fun String?.toData() = this ?: data(6)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .compute()

    fun star2(input: String?) = input
        .toData()
        .lines()
        .map { it.replace(" ", "") }
        .compute()

    private fun List<String>.compute() =
        map { Regex("""\d+""").findAll(it).map { it.value.toLong() }.toList() }
            .filterNot { it.isEmpty() }
            .let { (l1, l2) -> l1.zip(l2) }
            .map { (time, distance) ->
                (0 until time).count { hold ->
                    hold * (time - hold) > distance
                }
            }.reduce { acc, i -> acc * i }
}

fun main() {
    processStars(Day06::star1, Day06::star2)
}
