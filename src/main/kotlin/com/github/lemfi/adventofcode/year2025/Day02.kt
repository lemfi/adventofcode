package com.github.lemfi.adventofcode.year2025

import com.github.lemfi.adventofcode.processStars
import kotlin.math.pow

object Day02 {

    private fun String?.toData() = this ?: data(2)

    fun star1(input: String?) = input.toData()
        .split(",")
        .map { it.split("-") }
        .map { (a, b) -> a.toLong() to b.toLong() }
        .sumOf { (a, b) ->
            (a..b).sumOf {
                val pow = (10.0.pow("$it".length / 2)).toLong()
                if (it / pow == it % pow) it else 0L
            }
        }

    fun star2(input: String?) = input.toData()
        .split(",")
        .map { it.split("-") }
        .sumOf { (a, b) ->
            (a.toLong()..b.toLong()).sumOf { number -> if (number.hasRepeatingPattern()) number else 0L }
        }
}

private fun Long.hasRepeatingPattern(): Boolean {
    val len = toString().length
    return (1..len / 2)
        .filter { len % it == 0 }
        .any { k ->
            val pow = 10.0.pow(k).toLong()
            val reference = this % pow
            generateSequence(this) { n -> n / pow }
                .takeWhile { it > 0 }
                .all { n ->
                    n % pow == reference }
        }
}

fun main() {
    processStars(Day02::star1, Day02::star2)
}
