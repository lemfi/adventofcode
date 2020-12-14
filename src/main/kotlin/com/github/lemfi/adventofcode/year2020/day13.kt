package com.github.lemfi.adventofcode.year2020

import java.math.BigInteger

fun day13_1(data: String): Int {

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


// 1261477/7
fun day13_2(data: String): BigInteger {

    return data.lines().last().split(",").let {
        it.mapIndexed { index, i -> i to index.toBigInteger() }
    }.let {
        it.filterNot { it.first == "x" }
    }.map { it.first.toBigInteger() to it.second }
            .let { start ->

                var t = BigInteger.valueOf(0)
                var index = BigInteger.valueOf(1)
                for (i in 2..start.size) {
                    val entries = start.dropLast(start.size - i)
                    var found = false
                    while (!found) {
                        t += index
                        found = entries.all { ((t + it.second) % it.first) == BigInteger.ZERO }
                    }
                    index = entries.map { it.first }.reduce { acc, value -> acc * value }
                }
                t
            }


}

fun main() {

    day13_1(day13data)
            .apply { println("res: $this") }

    day13_2(day13data)
            .apply { println("res: $this") }

}

val day13data = """1000053
19,x,x,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,523,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,23,x,x,x,x,x,29,x,547,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,17"""


val t = "17,0  13,2  19,3  => 3417"