package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.math.abs

fun main() {

    Day11.star1(Day11.data)
        .apply { println(this) }

    Day11.star2(Day11.data)
        .apply { println("res: $this") }

}

object Day11 {

    fun star1(data: String) = data
        .lines()
        .map { it.toCharArray().toList() }
        .applyRules(4, seat1).first.sumOf { it.count { it == '#' } }

    fun star2(data: String) = data
        .lines()
        .map { it.toCharArray().toList() }
        .applyRules(5, seat2).first.sumOf { it.count { it == '#' } }

    fun List<List<Char>>.applyRules(acceptedContigous: Int, seats: (input: List<List<Char>>, x: Int, y: Int) -> List<Char>): Pair<List<List<Char>>, Boolean> {

        return mapIndexed {
                position, line  ->
            line.applyRule(this, acceptedContigous, position, seats)
        }.fold(listOf<List<Char>>() to false) {
                res, current -> res.first + listOf(current.first) to (res.second || current.second)
        }.let {
            if (it.second) it.first.applyRules(acceptedContigous, seats)
            else it
        }
    }

    fun List<Char>.applyRule(input:List<List<Char>>, acceptedContigous: Int, x: Int, seats: (input: List<List<Char>>, x: Int, y: Int) -> List<Char>): Pair<List<Char>, Boolean> {
        return mapIndexed {
                y, column  ->
            column.rule(input, acceptedContigous, x, y, seats)
        }.fold(listOf<Char>() to false) {
                res, current -> res.first + listOf(current.first) to (res.second || current.second)
        }
    }

    fun Char.rule(input: List<List<Char>>, acceptedContigous: Int, x: Int, y: Int, seats: (input: List<List<Char>>, x: Int, y: Int) -> List<Char>) =
        if (this == '.') this to false
        else seats(input, x, y)
            .let {
                if (this == 'L' && it.none { it == '#' }) '#' to true
                else if (this == '#' && it.filter { it == '#' }.size >= acceptedContigous) 'L' to true
                else this to false
            }

    fun List<List<Char>>.get(x: Int, y: Int) =
        getOrElse(x) { listOf('.') }
            .getOrElse(y) { '.' }

    val seat1: (input: List<List<Char>>, x: Int, y: Int)->List<Char> =
        { input, x, y ->
            listOf(input.get(x - 1, y - 1), input.get(x - 1, y), input.get(x - 1, y + 1), input.get(x, y - 1), input.get(x, y + 1), input.get(x + 1, y - 1), input.get(x + 1, y), input.get(x + 1, y + 1))
        }

    val seat2: (input: List<List<Char>>, x: Int, y: Int)->List<Char> =
        { input, x, y ->
            listOf(seat2rec(input, x, y, -1, -1), seat2rec(input, x, y, -1, 0), seat2rec(input, x, y, -1, 1), seat2rec(input, x, y, 0, -1), seat2rec(input, x, y, 0, 1), seat2rec(input, x, y, 1, -1), seat2rec(input, x, y, 1, 0), seat2rec(input, x, y, 1, 1))
        }

    tailrec fun seat2rec(input: List<List<Char>>, initX: Int, initY: Int, moveX: Int, moveY: Int): Char {

        if (initX + moveX < 0 || initY + moveY < 0 || initX + moveX == input.size || initY + moveY == input.first().size) return '.'

        if (input[initX + moveX][initY + moveY] == '.') return seat2rec(input, initX + moveX, initY + moveY, moveX, moveY)
        else return input[initX + moveX][initY + moveY]
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day11.txt").readAllBytes().toString(Charset.defaultCharset()) }

}