package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day22 {

    private fun String?.toData() = this ?: data(22)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day22::star1, Day22::star2)
}
