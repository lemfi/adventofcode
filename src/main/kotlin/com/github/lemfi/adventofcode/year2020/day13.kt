package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day13.star1(Day13.data)
        .apply { println("res: $this") }

    Day13.star2(Day13.data)
        .apply { println("res: $this") }

}

object Day13 {

    fun star1(data: String): Int {

        return data.lines()
            .let {
                it.first().toInt() to it.last().split(",").filterNot { it == "x" }.map { it.toInt() }
            }
            .let { p->
                p.second.map { it - (p.first + it) % it to it }
            }.fold(Pair(Int.MAX_VALUE, Int.MAX_VALUE)) {
                    res, p -> if (p.first < res.first) p else res
            }.let {
                it.first * it.second
            }
    }

    fun star2(data: String): Long {

        return data.lines().last().split(",").let {
            it.mapIndexed { index, i -> i to index.toLong() }
        }.let {
            it.filterNot { it.first == "x" }
        }.map { it.first.toLong() to it.second }
            .let { start ->

                var t = 0L
                var index = 1L
                for (i in 2..start.size) {
                    val entries = start.dropLast(start.size - i)
                    var found = false
                    while (!found) {
                        t += index
                        found = entries.all { ((t + it.second) % it.first) == 0L }
                    }
                    index = entries.map { it.first }.reduce { acc, value -> acc * value }
                }
                t
            }


    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day13.txt").readAllBytes().toString(Charset.defaultCharset()) }
}