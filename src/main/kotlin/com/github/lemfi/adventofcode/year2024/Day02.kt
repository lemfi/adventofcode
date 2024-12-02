package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day02 {

    private fun String?.toData() = this ?: data(2)

    fun star1(input: String?) = input
        .parse()
        .count { it.isValid() }

    fun star2(input: String?) = input
        .parse()
        .count { level ->
            level.indices.sortedDescending().any { level.toMutableList().apply { removeAt(it) }.isValid() }
        }

    private fun List<Int>.isValid() =
        (sorted() == this || sortedDescending() == this) &&
                sorted()
                    .let { numbers ->
                        numbers.indices.all {
                            runCatching { numbers[it + 1] in (numbers[it] + 1..numbers[it] + 3) }.getOrElse { true }
                        }
                    }

    private fun String?.parse() = toData()
        .lines()
        .map { it.split(" ").map { s -> s.toInt() } }
}

fun main() {
    processStars(Day02::star1, Day02::star2)
}
