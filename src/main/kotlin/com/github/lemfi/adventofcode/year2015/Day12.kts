package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day12 {

    private fun String?.toData() = this ?: data(12)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day12::star1, Day12::star2)