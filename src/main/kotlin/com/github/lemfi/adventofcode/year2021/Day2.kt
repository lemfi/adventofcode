package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day2 {

    private fun String?.toData() = this ?: data(2)

    private fun String?.prepareData() = toData()
        .lines()
        .map {
            it.split(" ").let { (instruction, value) ->
                when (instruction) {
                    "forward" -> Forward(value.toInt())
                    "up" -> Up(value.toInt())
                    "down" -> Down(value.toInt())
                    else -> error("hmm...")
                }
            }
        }

    fun star1(input: String?) = input
        .prepareData()
        .fold(0 to 0) { (x, y), instruction ->
            instruction.resolve1(x, y)
        }.let { (x, y) -> x * y }

    fun star2(input: String?) = input
        .prepareData()
        .fold(Triple(0, 0, 0)) { (x, y, aim), instruction ->
            instruction.resolve2(x, y, aim)
        }.let { (x, y) -> x * y }

    sealed class Instruction {
        abstract val move: Int
        abstract fun resolve1(x: Int, y: Int): Pair<Int, Int>
        abstract fun resolve2(x: Int, y: Int, aim: Int): Triple<Int, Int, Int>
    }

    data class Forward(override val move: Int) : Instruction() {
        override fun resolve1(x: Int, y: Int) = (x + move) to y
        override fun resolve2(x: Int, y: Int, aim: Int) = Triple(x + move, y - aim * move, aim)
    }

    data class Up(override val move: Int) : Instruction() {
        override fun resolve1(x: Int, y: Int) = x to (y - move)
        override fun resolve2(x: Int, y: Int, aim: Int) = Triple(x, y, aim + move)
    }

    data class Down(override val move: Int) : Instruction() {
        override fun resolve1(x: Int, y: Int) = x to (y + move)
        override fun resolve2(x: Int, y: Int, aim: Int) = Triple(x, y, aim - move)
    }

}

fun main() {
    processStars(Day2::star1, Day2::star2)
}