package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day15 {

    private fun String?.toData() = this ?: data(15)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day15::star1, Day15::star2)
}
