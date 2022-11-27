package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day9 {

    private fun String?.toData() = (this ?: data(9))
        .lines()
        .map {
            Triple(
                it.substringBefore(" to "),
                it.substringAfter(" to ").substringBefore(" = "),
                it.substringAfter(" = ").toInt()
            )
        }

    fun star1(input: String?) = input
        .toData()
        .let { lines ->
            val towns = lines.flatMap { (t1, t2, _) -> listOf(t1, t2) }.toSet()
            towns.minOf { from(lines, it, towns - it) { min() } }
        }

    private fun from(
        distances: List<Triple<String, String, Int>>,
        town: String,
        toVisit: Set<String>,
        resolve: List<Int>.() -> Int
    ): Int {
        return if (toVisit.size == 1) distance(distances, town, toVisit.first())
        else toVisit
            .map { t2 -> distance(distances, town, t2) + from(distances, t2, toVisit - t2, resolve) }
            .resolve()
    }

    fun star2(input: String?) = input
        .toData()
        .let { lines ->
            val towns = lines.flatMap { (t1, t2, _) -> listOf(t1, t2) }.toSet()
            towns.maxOf { from(lines, it, towns - it) { max() } }
        }


    private fun distance(distances: List<Triple<String, String, Int>>, t1: String, t2: String) =
        distances.first { (tt1, tt2) -> tt1 == t1 && tt2 == t2 || tt1 == t2 && tt2 == t1 }.third
}

fun main() {
    processStars(Day9::star1, Day9::star2)
}
