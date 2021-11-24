package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day2 {

    private fun String?.toData() = this ?: data(2)

    fun star1(input: String?) = input.toData().lines()
        .map { it.split("x").map { it.toInt() } }
        .sumOf { (l, w, h) -> 2 * l * w + 2 * w * h + 2 * h * l + minOf(l * w, w * h, h * l) }

    fun star2(input: String?) = input.toData().lines()
        .map { it.split("x").map { it.toInt() }.sorted() }
        .sumOf { (l, w, h) -> 2 * l + 2 * w + l * w * h }
}

processStars(Day2::star1, Day2::star2)
