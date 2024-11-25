package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day01 {

    private fun String?.toData() = this ?: data(1)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day01::star1, Day01::star2)
}
