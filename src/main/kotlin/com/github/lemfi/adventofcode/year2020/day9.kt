package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset


fun main() {

    Day9.star1(25, Day9.data)
        .apply { println("res: $this") }

    Day9.star2(25, Day9.data)
        .apply { println("res: $this") }

}

object Day9 {

    fun star1(preamble: Int, data: String): Long {

        return data.lines()
            .map { it.toLong() }
            .findInvalid(preamble)
    }

    fun star2(preamble: Int, data: String): Long {

        return data.lines()
            .map { it.toLong() }
            .let { values ->
                values.findInvalid(preamble)
                    .let { invalid ->
                        values.filter { it < invalid } `to first contigous entries that sums to` invalid
                    }
            }
            .let { it.first() + it.last() }
    }

    tailrec fun List<Long>.headSumsTo(sum: Long, res: List<Long> = emptyList()): List<Long> {

        return if (this.isEmpty()) res
        else {
            val trySum = res.sum() + first()
            if (trySum == sum) res + listOf(first())
            else if (trySum < sum) {
                drop(1).headSumsTo(sum, res + listOf(first()))
            } else emptyList()
        }
    }

    infix tailrec fun List<Long>.`to first contigous entries that sums to`(sum: Long): List<Long> {

        return if (isEmpty()) emptyList()
        else {
            headSumsTo(sum).let {
                if (it.isNotEmpty()) it.sorted()
                else {
                    drop(1) `to first contigous entries that sums to` sum
                }
            }
        }
    }

    tailrec fun List<Long>.findInvalid(preamble: Int): Long {

        if (preamble > this.size) return -1

        val preambleLst = take(preamble)
        val value = this[preamble]
        return if (value.isValid(preambleLst)) {
            this.drop(1).findInvalid(preamble)
        } else value
    }

    fun Long.isValid(lst: List<Long>) =
        lst.any { first -> lst.any { second -> first != second && first + second == this } }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day9.txt").readAllBytes().toString(Charset.defaultCharset()) }

}