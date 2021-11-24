package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day11 {

    private fun String?.toData() = this ?: data(11)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day11::star1, Day11::star2)