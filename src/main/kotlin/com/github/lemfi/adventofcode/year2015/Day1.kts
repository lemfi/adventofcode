package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day1 {

    private fun String?.toData() = this ?: data(1)

    fun star1(input: String?) = input.toData()
        .let { data ->
            data.count { it == '(' } - data.count { it == ')' }
        }

    fun star2(input: String?) = input.toData()
        .toList()
        .map { if (it == '(') 1 else -1 }
        .foldIndexed(0 to -1) { index, (floor, floorIndex), value ->
            if (floorIndex != -1) floor to floorIndex
            else (floor + value).let { if (it == -1) it to index + 1 else it to -1 }
        }.second
}

processStars(Day1::star1, Day1::star2)
