package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day08 {

    private fun String?.toData() = this ?: data(8)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day08::star1, Day08::star2)
}
