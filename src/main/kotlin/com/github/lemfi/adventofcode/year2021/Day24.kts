package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day24 {

    private fun String?.toData() = this ?: data(24)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day24::star1, Day24::star2)