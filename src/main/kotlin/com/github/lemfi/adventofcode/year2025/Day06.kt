package com.github.lemfi.adventofcode.year2025

import com.github.lemfi.adventofcode.processStars

object Day06 {

    private fun String?.toData() = this ?: data(6)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day06::star1, Day06::star2)
}
