package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day05 {

    private fun String?.toData() = this ?: data(5)

    fun star1(input: String?) = input.parse()
        .let { (rules, pages) ->
            pages
                .filter { it.isCorrect(rules) }
                .sumOf { it[it.size / 2] }
        }

    fun star2(input: String?) = input.parse()
        .let { (rules, pages) ->
            pages
                .filterNot { it.isCorrect(rules) }
                .map { it.fix(rules) }
                .sumOf { it[it.size / 2] }
        }

    private fun String?.parse() = toData()
        .split("\n\n")
        .let { (rules, pages) ->
            rules.lines()
                .map {
                    it.split("|").let { it[0].toInt() to it[1].toInt() }
                } to pages.lines().map { it.split(",").map { it.toInt() } }

        }

    private fun List<Int>.fix(rules: List<Pair<Int, Int>>): List<Int> =
        sortedWith { a, b ->
            when {
                rules.any { (before, after) -> before == b && after == a } -> 1
                else -> -1
            }
        }

    private fun List<Int>.isCorrect(
        rules: List<Pair<Int, Int>>
    ) = indices.all { index ->
        drop(index + 1).none { checkAfter -> rules.any { (before, after) -> after == this[index] && before == checkAfter } }
    }
}

fun main() {
    processStars(Day05::star1, Day05::star2)
}
