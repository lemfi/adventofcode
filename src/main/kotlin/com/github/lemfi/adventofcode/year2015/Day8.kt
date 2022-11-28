package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day8 {

    private fun String?.toData() = this ?: data(8)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day8::star1, Day8::star2)
}
