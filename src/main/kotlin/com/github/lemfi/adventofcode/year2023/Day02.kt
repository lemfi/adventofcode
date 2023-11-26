package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day02 {

    private fun String?.toData() = this ?: data(2)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day02::star1, Day02::star2)
}
