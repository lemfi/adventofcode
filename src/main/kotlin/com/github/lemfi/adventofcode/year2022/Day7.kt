package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day7 {

    private fun String?.toData() = this ?: data(7)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day7::star1, Day7::star2)
}