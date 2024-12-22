package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day22 {

    private fun String?.toData() = this ?: data(22)

    fun star1(input: String?) = input.parse()
        .sumOf { it.secretNumbers().last() }

    fun star2(input: String?) = input.parse()
        .flatMap { from ->
            from.secretNumbers()
                .map { "$it".last().digitToInt() }
                .let { secrets ->
                    val sequences = (listOf("$from".last().digitToInt()) + secrets)
                    sequences
                        .mapIndexed { index, secret ->
                            if (index == 0) 0 to 0 else secret - sequences[index - 1] to secret
                        }
                        .windowed(4, 1, false)
                        .map { it.map { (sequence, _) -> sequence } to it.last().let { (_, bananas) -> bananas } }
                }
                .groupBy { (sequence, _) -> sequence }
                .map { (_, sequences) -> sequences.first() }
        }
        .groupBy { (sequence, _) -> sequence }
        .maxOf { (_, sequences) -> sequences.sumOf { (_, bananas) -> bananas } }

    private fun String?.parse() = toData()
        .lines()
        .map { it.toLong() }

    private fun Long.secretNumbers(): List<Long> {
        var secret = this
        val variations = mutableListOf<Long>()
        repeat(2000) {
            secret = ((secret * 64) xor secret) % 16777216
            secret = ((secret / 32) xor secret) % 16777216
            secret = ((secret * 2048) xor secret) % 16777216
            variations.add(secret)
        }
        return variations
    }

}

// 1686 too high
fun main() {
    processStars(Day22::star1, Day22::star2)
}
