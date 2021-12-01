package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day1 {

    private fun String?.toData() = this ?: data(1)

    private fun List<Int>.prev(index: Int) = if (index == 0) first() else this[index - 1]

    private fun List<Int>.increases() = mapIndexed { index, value ->
        if (prev(index) < value) 1 else 0
    }.sum()

    private fun String?.prepareData() = toData()
        .lines()
        .map { it.toInt() }

    fun star1(input: String?) = input
        .prepareData()
        .increases()

    fun star2(input: String?) = input
        .prepareData()
        .windowed(3, 1)
        .map { it.sum() }
        .increases()

}

processStars(Day1::star1, Day1::star2)