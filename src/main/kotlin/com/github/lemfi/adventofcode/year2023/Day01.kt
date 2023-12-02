package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day01 {

    private fun String?.toData() = this ?: data(1)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .sumOf { line ->
            "${line.first { it.findStar1() }}${line.last { it.findStar1() }}".toInt()
        }

    fun star2(input: String?) = input
        .toData()
        .lines()
        .sumOf { line ->
            "${line.findStar2 { label }}${line.reversed().findStar2 { label.reversed() }}".toInt()
        }

    private fun Char.findStar1() =
        runCatching {
            toString().toInt()
        }.getOrNull() != null


    private fun String.findStar2(transform: Numbers.() -> String) =
        (indices).firstNotNullOf { index ->
            Numbers.entries.firstOrNull { substring(index).startsWith(it.transform()) }
        }.value
}

private enum class Numbers(val label: String, val value: Int) {

    one("one", 1),
    two("two", 2),
    three("three", 3),
    four("four", 4),
    five("five", 5),
    six("six", 6),
    seven("seven", 7),
    eight("eight", 8),
    nine("nine", 9),
    onen("1", 1),
    twon("2", 2),
    threen("3", 3),
    fourn("4", 4),
    fiven("5", 5),
    sixn("6", 6),
    sevenn("7", 7),
    eightn("8", 8),
    ninen("9", 9)
}

fun main() {
    processStars(Day01::star1, Day01::star2)
}
