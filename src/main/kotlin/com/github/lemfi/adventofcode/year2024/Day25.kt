package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day25 {

    private fun String?.toData() = this ?: data(25)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day25::star1, Day25::star2)
}
