package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day6.star1(Day6.data)
        .apply { println(this) }

    Day6.star2(Day6.data)
        .apply { println(this) }

}

object Day6 {

    fun star1(data: String): Int {

        return data
            .split("\n\n")
            .map { it.lines().map { it.toSet() } }
            .map { it.reduce { answer1, answer2 -> answer1.union(answer2)} }
            .flatten()
            .count()

    }

    fun star2(data: String): Int {
        return data
            .split("\n\n")
            .map { it.lines().map { it.toSet() } }
            .map { it.reduce { answer1, answer2 -> answer1.intersect(answer2)} }
            .flatten()
            .count()
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day6.txt").readAllBytes().toString(Charset.defaultCharset()) }
}