package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day16 {

    private fun String?.toData() = this ?: data(16)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day16::star1, Day16::star2)
}
