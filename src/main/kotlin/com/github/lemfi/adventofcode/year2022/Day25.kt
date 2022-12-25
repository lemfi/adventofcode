package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs
import kotlin.math.pow

object Day25 {

    private fun String?.toData() = this ?: data(25)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .sumOf { it.decoded() }
        .encode()

    private fun String.decoded(): Long = reversed().mapIndexed { index, c ->
        when (c) {
            '2' -> 2 * 5.0.pow(index.toDouble())
            '1' -> 1 * 5.0.pow(index.toDouble())
            '0' -> 0 * 5.0.pow(index.toDouble())
            '-' -> -1 * 5.0.pow(index.toDouble())
            '=' -> -2 * 5.0.pow(index.toDouble())
            else -> error("wtf is that notation?")
        }.toLong()
    }.sum()


    private fun Long.encode() = let { decimal ->

        var maxPow = 0
        do {
            val diff = abs(decimal - 5.0.pow(maxPow))
            maxPow++
        } while (abs(decimal - 5.0.pow(maxPow)) < diff)

        var rest = toDouble()

        (maxPow - 1 downTo 0).joinToString("") { pow ->

            val t0 = Triple(abs(abs(rest) - 0 * 5.0.pow(pow)), 0, "0")
            val t1 = Triple(abs(abs(rest) - 1 * 5.0.pow(pow)), if (rest > 0) 1 else -1, if (rest > 0) "1" else "-")
            val t2 = Triple(abs(abs(rest) - 2 * 5.0.pow(pow)), if (rest > 0) 2 else -2, if (rest > 0) "2" else "=")

            listOf(t0, t1, t2)
                .minBy { (diff, _, _) -> diff }
                .let { (_, mult, notation) ->
                    rest -= mult * 5.0.pow(pow)
                    notation
                }
        }

    }

    @Suppress("UNUSED_PARAMETER")
    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day25::star1, Day25::star2)
}