package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day6 {

    private fun String?.toData() = this ?: data(6)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day6::star1, Day6::star2)