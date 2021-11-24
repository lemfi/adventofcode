package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day20 {

    private fun String?.toData() = this ?: data(20)

    fun star1(input: String?) = 0

    fun star2(input: String?) = 0
}

processStars(Day20::star1, Day20::star2)