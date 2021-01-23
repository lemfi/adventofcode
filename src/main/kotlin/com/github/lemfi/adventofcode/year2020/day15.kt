package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day15.star1(Day15.data)
        .apply { println("res: $this") }

    Day15.star2(Day15.data)
        .apply { println("res: $this") }

}

object Day15 {

    fun star1(data: String): Int {

        return data.split(",")
            .map { it.toInt() }
            .play(2020)
    }

    fun star2(data: String): Int {
        return data.split(",")
            .map { it.toInt() }
            .let { Brain(it).play(30000000) }
    }

    tailrec fun List<Int>.play(max: Int): Int {
        return when {
            this.size == max -> last()
            indexOf(last()) == size -1 -> (this + listOf(0)).play(max)
            else -> (this + listOf(size - 1 - dropLast(1).lastIndexOf(last()))).play(max)
        }
    }

    class Brain(
        init: List<Int>
    ) {
        var last: Int
        var vues: MutableMap<Int, List<Int>>

        init {
            last = init.last()
            vues = init.mapIndexed { index, i -> i to listOf(index + 1) }.toMap().toMutableMap()
        }

        fun play(iterations: Int): Int {
            (this.vues.entries.size + 1 .. iterations).forEach { index ->

                val seen = vues.getOrDefault(last, mutableListOf())

                last = if (seen.size == 2) seen.last() - seen.first() else 0

                val seenlast = vues.getOrDefault(last, mutableListOf())
                if (seenlast.isNotEmpty()) {
                    vues.put(last, listOf(seenlast.last(), index))
                } else {
                    vues.put(last, listOf(index))
                }
            }
            return last
        }
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day15.txt").readAllBytes().toString(Charset.defaultCharset()) }

}