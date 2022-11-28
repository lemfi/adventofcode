package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.data
import com.github.lemfi.adventofcode.processStars

object Day4 {

    private fun String?.toData() = this ?: data("4")

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day4::star1, Day4::star2)
}