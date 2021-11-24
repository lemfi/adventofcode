package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day9 {

    private fun String?.toData() = this ?: data(9)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day9::star1, Day9::star2)