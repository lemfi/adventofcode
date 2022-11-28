package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.data
import com.github.lemfi.adventofcode.processStars

object Day1 {

    private fun String?.toData() = this ?: data("1")

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day1::star1, Day1::star2)
}