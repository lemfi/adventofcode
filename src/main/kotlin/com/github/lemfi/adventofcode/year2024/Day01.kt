package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs

object Day01 {

    private fun String?.toData() = this ?: data(1)

    fun star1(input: String?) = input
        .parse()
        .let { (l1, l2) ->
            l1.sorted()
                .zip(l2.sorted())
                .sumOf { abs(it.first - it.second) }
        }

    fun star2(input: String?) = input
        .parse()
        .let { (l1, l2) ->
            l1.sumOf { number ->
                number * l2.count { it == number }
            }
        }

    private fun String?.parse() =
        toData()
            .lines()
            .map { line -> line.split("   ").let { it[0].toInt() to it[1].toInt() } }
            .unzip()
}

fun main() {
    processStars(Day01::star1, Day01::star2)
}
