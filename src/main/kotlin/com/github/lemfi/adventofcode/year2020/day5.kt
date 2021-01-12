package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day5.star1(Day5.data).apply { println(this) }
    Day5.star2(Day5.data).apply { println(this) }
}

object Day5 {

    val rows = 0..127
    val columns = 0..7
    val seats = 0 until (128 * 8)

    fun star1(data: String) = data.lines()
        .map { getSeat(it) }
        .sorted()
        .last()

    fun star2(data: String) =  data.lines()
        .map { getSeat(it) }
        .let { takenSeats ->
            seats.first { takenSeats.contains(it - 1) && takenSeats.contains(it + 1) && !takenSeats.contains(it) }
        }

    fun getSeat(data: String) = data.toCharArray().fold(rows to columns) {
            (rows, columns), code ->
        when(code) {
            'F' -> firstHalf(rows) to columns
            'B' -> secondHalf(rows) to columns
            'L' -> rows to firstHalf(columns)
            'R' -> rows to secondHalf(columns)
            else -> (rows to columns) // should not happen
        }
    }.let { (row, column) ->
        row.first * 8 + column.first
    }

    fun firstHalf(range: IntRange) = range.first..(range.last - range.first) / 2 + range.first
    fun secondHalf(range: IntRange) = (range.last - range.first) / 2 + range.first + 1..range.last

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day5.txt").readAllBytes().toString(Charset.defaultCharset()) }
}