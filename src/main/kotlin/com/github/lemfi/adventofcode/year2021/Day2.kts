package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day2 {

    private fun String?.toData() = this ?: data(2)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day2::star1, Day2::star2)