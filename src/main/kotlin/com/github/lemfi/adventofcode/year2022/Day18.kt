package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.data
import com.github.lemfi.adventofcode.processStars

object Day18 {

    private fun String?.toData() = this ?: data("18")

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day18::star1, Day18::star2)
}