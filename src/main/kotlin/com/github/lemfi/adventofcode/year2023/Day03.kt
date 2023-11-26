package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day03 {

    private fun String?.toData() = this ?: data(3)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day03::star1, Day03::star2)
}
