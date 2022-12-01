package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day19 {

    private fun String?.toData() = this ?: data(19)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day19::star1, Day19::star2)
}