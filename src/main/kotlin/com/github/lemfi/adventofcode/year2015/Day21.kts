package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day21 {

    private fun String?.toData() = this ?: data(21)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day21::star1, Day21::star2)