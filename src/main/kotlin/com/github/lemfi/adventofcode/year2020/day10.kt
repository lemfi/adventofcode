package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.math.max

fun main() {

    Day10.star1(Day10.data)
        .apply { println(this) }

    Day10.star2(Day10.data)
        .apply { println("res: $this") }

}

object Day10 {

    fun star1(data: String): Long {

        return data.lines()
            .map { it.toLong() }
            .sorted()
            .toJoltDiffs()
            .let { it.first * it.second }
    }

    fun star2(data: String): Long {
        return data.lines()
            .map { it.toLong() }
            .sorted()
            .split()
            .let { lists ->
                lists.mapIndexed { index, it ->
                    if (index == 0) it.computePossibilities(0) + it.drop(1).computePossibilities(0) + it.drop(2).computePossibilities(0)
                    else it.computePossibilities(lists[index-1].last())
                }
            }
            .reduce { acc, i -> acc * i }
    }

    fun List<Long>.split(): List<List<Long>> =
        foldIndexed(mutableListOf(mutableListOf<Long>())) {
                index, res, current ->

            res.last().add(current)
            if (current + 3 == getOrElse(index + 1) { -1L }) res.also { it.add(mutableListOf()) }
            res
        }

    fun List<Long>.computePossibilities(prec: Long = first() - 3): Long {
        return if (size == 1) 1L
        else if (isNotEmpty() && first() <= prec + 3) {
            drop(1).computePossibilities(first()) + drop(2).computePossibilities(first()) + drop(3).computePossibilities(first())
        }
        else 0L
    }

    fun List<Long>.toJoltDiffs(): Pair<Long, Long> =
        foldIndexed(1L to 1L) {
                index, res, current ->

            if (current + 1 == getOrElse(index + 1) { -1L }) res.first + 1 to res.second
            else if (current + 3 == getOrElse(index + 1) { -1L }) res.first to res.second + 1
            else res
        }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day10.txt").readAllBytes().toString(Charset.defaultCharset()) }

}