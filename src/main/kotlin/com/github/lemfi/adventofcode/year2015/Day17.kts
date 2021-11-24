package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day17 {

    private fun String?.toData() = this ?: data(17)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day17::star1, Day17::star2)