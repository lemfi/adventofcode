package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day25 {

    private fun String?.toData() = this ?: data(25)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day24::star1, Day24::star2)
}
