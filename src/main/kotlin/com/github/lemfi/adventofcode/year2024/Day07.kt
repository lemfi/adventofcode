package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day07 {

    private fun String?.toData() = this ?: data(7)

    fun star1(input: String?) = input
        .parse()
        .sumOf { (result, values) ->
            result.check(values.drop(1), values[0], { a, b -> a + b }, { a, b -> a * b })
        }

    fun star2(input: String?) = input
        .parse()
        .sumOf { (result, values) ->
            result.check(
                values.drop(1),
                values[0],
                { a, b -> a + b },
                { a, b -> a * b },
                { a, b -> "$a$b".toLong() },
            )
        }

    private fun String?.parse() = toData()
        .lines()
        .map {
            it
                .split(": ")
                .let { (a, b) ->
                    a.toLong() to b.split(" ").map(String::toLong)
                }
        }

    private fun Long.check(values: List<Long>, currentValue: Long, vararg operations: (Long, Long) -> Long): Long =
        when {

            values.isEmpty() -> when (currentValue) {
                this -> this
                else -> 0
            }

            else -> operations
                .firstOrNull { operation ->
                    check(
                        values.drop(1),
                        operation(currentValue, values[0]),
                        *operations
                    ) == this
                }
                ?.let { this }
                ?: 0
        }
}

fun main() {
    processStars(Day07::star1, Day07::star2)
}