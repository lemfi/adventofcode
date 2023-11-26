package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day09 {

    private fun String?.toData() = this ?: data(9)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day09::star1, Day09::star2)
}
