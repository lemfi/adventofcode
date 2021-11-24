package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day5 {

    private fun String?.toData() = this ?: data(5)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day5::star1, Day5::star2)