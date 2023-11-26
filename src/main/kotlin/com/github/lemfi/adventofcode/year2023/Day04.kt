package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day04 {

    private fun String?.toData() = this ?: data(4)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day04::star1, Day04::star2)
}
