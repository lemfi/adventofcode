package com.github.lemfi.adventofcode.year2025

import com.github.lemfi.adventofcode.processStars

object Day07 {

    private fun String?.toData() = this ?: data(7)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day07::star1, Day07::star2)
}
