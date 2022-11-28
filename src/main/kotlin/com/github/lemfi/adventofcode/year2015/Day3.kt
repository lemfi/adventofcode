package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day3 {

    private fun String?.toData() = this ?: data(3)

    fun star1(input: String?) = input.toData().coordinates().toSet().count()

    private fun String.coordinates() = fold(listOf(0 to 0)) { coordinates, c ->
        coordinates.last().let { (lng, lat) ->
            when (c) {
                '^' -> coordinates + listOf(lng + 1 to lat)
                'v' -> coordinates + listOf(lng - 1 to lat)
                '<' -> coordinates + listOf(lng to lat - 1)
                '>' -> coordinates + listOf(lng to lat + 1)
                else -> error("hmm...")
            }
        }
    }

    fun star2(input: String?) = input.toData()
        .let { it.filterIndexed { index, _ -> index % 2 == 0 } to it.filterIndexed { index, _ -> index % 2 != 0 } }
        .let { (santa, robot) -> santa.coordinates() + robot.coordinates() }
        .toSet().count()
}

fun main() {
    processStars(Day3::star1, Day3::star2)
}
