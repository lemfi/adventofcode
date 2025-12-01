package com.github.lemfi.adventofcode.year2025

import com.github.lemfi.adventofcode.processStars

object Day01 {

    private fun String?.toData() = this ?: data(1)

    fun star1(input: String?) = input.toData().lines()
        .map { it.take(1) to it.drop(1).toInt() }
        .fold(50 to 0) { (dial, password), (direction, steps) ->
            when (direction) {
                "L" -> (dial - steps).let { (if (it < 0) 100 + it else it) % 100 }
                "R" -> (dial + steps) % 100
                else -> error("Unknown direction $direction")
            }
                .let { it to if (it == 0) password + 1 else password }
        }
        .second

    fun star2(input: String?) = input.toData().lines()
        .map { it.take(1) to it.drop(1).toInt() }
        .fold(50 to 0) { (dial, password), (direction, steps) ->

            when (direction) {

                "L" -> {
                    val newDial = (dial - steps % 100).let { if (it < 0) 100 + it else it }
                    val hits = ((steps / 100) + if (dial != 0 && steps % 100 >= dial) 1 else 0)
                    newDial to password + hits
                }

                "R" -> {
                    val newDial = (dial + steps % 100) % 100
                    val hits = ((steps / 100) + if (dial != 0 && steps % 100 >= 100 - dial) 1 else 0)
                    newDial to password + hits
                }

                else -> error("Unknown direction $direction")
            }
        }
        .second

}

fun main() {
    processStars(Day01::star1, Day01::star2)
}
