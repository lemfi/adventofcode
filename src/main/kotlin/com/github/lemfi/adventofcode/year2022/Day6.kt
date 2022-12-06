package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day6 {

    private fun String?.toData() = this ?: data(6)

    fun star1(input: String?) = input.findStartPacketMarker(4)

    fun star2(input: String?) = input.findStartPacketMarker(14)

    private fun String?.findStartPacketMarker(nbDistinctCharacters: Int) = toData()
        .toList()
        .windowed(nbDistinctCharacters)
        .indexOfFirst { it.size == it.toSet().size } + nbDistinctCharacters
}

fun main() {
    processStars(Day6::star1, Day6::star2)
}