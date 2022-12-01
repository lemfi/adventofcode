package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day23 {

    private fun String?.toData() = this ?: data(23)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

fun main() {
    processStars(Day23::star1, Day23::star2)
}