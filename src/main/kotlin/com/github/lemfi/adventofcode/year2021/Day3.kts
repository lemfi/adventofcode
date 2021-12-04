package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day3 {

    private fun String?.toData() = this ?: data(3)

    private fun String.occurence(selector: (Int, Int) -> Char) = lines().let { lines ->
        (0 until lines.first().length).map { id ->
            lines.map { it[id] }
        }
    }
        .map { line -> selector(line.count { it == '0' }, line.count { it == '1' }) }
        .joinToString("")

    private fun String.gamma() = occurence { c0, c1 ->
        if (c0 > c1) '0' else '1'
    }

    private fun String.epsilon() = occurence { c0, c1 ->
        if (c0 <= c1) '0' else '1'
    }

    private fun String.gamma(index: Int) = gamma().toList()[index]

    private fun String.epsilon(index: Int) = epsilon().toList()[index]

    fun star1(input: String?) = input.toData().let {
        it.gamma().toInt(2) * it.epsilon().toInt(2)
    }

    fun star2(input: String?) = input.toData().let { numbers ->
        oxygen(numbers) * co2(numbers)
    }

    private fun oxygen(numbers: String): Int {
        return numbers.bitCriteria { epsilon(it) }
    }

    private fun co2(numbers: String): Int {
        return numbers.bitCriteria { gamma(it) }
    }

    private fun String.bitCriteria(compute: String.(Int) -> Char): Int {
        return lines()
            .toMutableList()
            .also { lines ->
                (0..lines.first().length).map { index ->
                    if (lines.size > 1) {
                        with(
                            lines.joinToString("\n")
                                .compute(index)
                        ) {
                            lines.removeIf { it[index] == this }
                        }
                    }
                }
            }.joinToString("").toInt(2)
    }
}

processStars(Day3::star1, Day3::star2)