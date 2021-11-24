package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day13 {

    private fun String?.toData() = this ?: data(13)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day13::star1, Day13::star2)