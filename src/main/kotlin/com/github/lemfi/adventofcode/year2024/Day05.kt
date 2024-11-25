package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day05 {

    private fun String?.toData() = this ?: data(5)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day05::star1, Day05::star2)
}
