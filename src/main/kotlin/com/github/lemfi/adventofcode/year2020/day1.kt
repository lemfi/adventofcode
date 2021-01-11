package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {
    Day1.star1(Day1.data).apply { println(this) }
    Day1.star2(Day1.data).apply { println(this) }
}

object Day1 {

    fun star1(data: String) = data.lines().map { it.toInt() }
        .let { list -> list.filter { entry -> list.any { entry + it == 2020 } } }
        .let { it.first() * it.last() }

    fun star2(data: String) = data.lines().map { it.toInt() }
        .let { list -> list
            .filter { entry ->
                list
                    .map { entry to it }
                    .let { l2 -> l2.any { (first, second) -> list.any { third -> first + second + third == 2020 } } } }
        }
        .reduce { acc, i -> acc * i }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day1.txt").readAllBytes().toString(Charset.defaultCharset()) }

}