package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day14 {

    private fun String?.toData() = this ?: data(14)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day14::star1, Day14::star2)