package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day10 {

    private fun String?.toData() = this ?: data(10)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day10::star1, Day10::star2)