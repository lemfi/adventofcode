package com.github.lemfi.adventofcode.year2020

fun day15_1(data: String): Int {

    return data.split(",")
            .map { it.toInt() }.play(2020)
}

tailrec fun List<Int>.play(max: Int): Int {
    return when {
        this.size == max -> last()
        indexOf(last()) == size -1 -> (this + listOf(0)).play(max)
        else -> (this + listOf(size - 1 - dropLast(1).lastIndexOf(last()))).play(max)
    }
}

class Brain(
        val init: List<Int>
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

fun day15_2(data: String): Int {
    return data.split(",")
            .map { it.toInt() }
            .let { Brain(it).play(30000000) }
}

fun main() {

    day15_1(day15data)
            .apply { println("res: $this") }

    day15_2(day15data)
            .apply { println("res: $this") }

}

val day15data = """8,11,0,19,1,2"""
