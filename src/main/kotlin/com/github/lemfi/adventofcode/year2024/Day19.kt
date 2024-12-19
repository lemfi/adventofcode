package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day19 {

    private fun String?.toData() = this ?: data(19)

    fun star1(input: String?) = input.parse()
        .let { (towels, designs) ->
            designs.count { design -> design.combinations(towels) > 0 }
        }

    fun star2(input: String?) = input.parse()
        .let { (towels, designs) ->
            designs.sumOf { design -> design.combinations(towels) }
        }

    private fun String?.parse() = toData()
        .split("\n\n")
        .let { (towels, designs) -> towels.split(", ") to designs.lines() }

    private fun String.combinations(towels: List<String>): Long {
        val cache = mutableMapOf<String, Long>()
        fun String.recursiveCombos(towels: List<String>): Long =
            if (isBlank()) 1
            else towels
                .filter { startsWith(it) }
                .sumOf { towel ->
                    val next = drop(towel.length)
                    cache[next] ?: next
                        .recursiveCombos(towels.filter { it in next })
                        .apply { cache[next] = this }
                }
        return recursiveCombos(towels)
    }
}

fun main() {
    processStars(Day19::star1, Day19::star2)
}
