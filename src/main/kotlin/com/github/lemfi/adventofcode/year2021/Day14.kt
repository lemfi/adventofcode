package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day14 {

    private fun String?.toData() = this ?: data(14)

    private fun String?.prepareData() = toData().lines().let { line ->
        (line.first().let { it.toPairs() to it.toList().last() }).let { (pairs, lastChar) ->
            Triple(pairs, lastChar, line.drop(2).map { it.split(" -> ").run { first() to last() } })
        }
    }

    fun star1(input: String?) = input.prepareData()
        .let { (polymerPairs, lastChar, patterns) ->
            polymerPairs.grow(patterns, 10).toResult(lastChar)
        }

    fun star2(input: String?) = input.prepareData()
        .let { (polymerPairs, lastChar, patterns) ->
            polymerPairs.grow(patterns, 40).toResult(lastChar)
        }

    private fun MutableMap<String, Long>.grow(
        patterns: List<Pair<String, String>>,
        steps: Int
    ) = apply {

        (1..steps).forEach { _ ->
            filter { it.value > 0 }.toMutableMap().let { polymerPairs ->
                polymerPairs.forEach { (pattern, count) ->
                    val newChar = patterns.first { it.first == pattern }.second

                    increment(pattern, -count)
                    increment("${pattern.first()}$newChar", count)
                    increment("$newChar${pattern.last()}", count)
                }
            }
        }
    }

    private fun Map<String, Long>.toResult(lastChar: Char) = filter { it.value > 0 }
        .map { (pattern, count) -> pattern.first() to count }
        .fold(mutableMapOf<Char, Long>()) { acc, (c, count) -> acc.apply { increment(c, count) } }
        .apply { increment(lastChar, 1) }
        .run { maxOf { it.value } - minOf { it.value } }

    private fun String.toPairs() = toList()
        .let { chars ->
            chars.foldIndexed(mutableListOf<String>()) { index, acc, c ->
                acc.apply { if (index < length - 1) add("$c${chars[index + 1]}") }
            }
        }
        .groupBy { it }
        .map { it.key to it.value.size.toLong() }
        .toMap().toMutableMap()

    private fun <T> MutableMap<T, Long>.increment(pattern: T, inc: Long) {
        this[pattern] = getOrDefault(pattern, 0L) + inc
    }
}

fun main() {
    processStars(Day14::star1, Day14::star2)
}
