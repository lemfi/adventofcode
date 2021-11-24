package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day3 {

    private fun String?.toData() = this ?: data(3)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day3::star1, Day3::star2)