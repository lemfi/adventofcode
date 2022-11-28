package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs

object Day7 {

    private fun String?.toData() = this ?: data(7)

    fun star1(input: String?) = input.toData().split(",").map { it.toInt() }
        .minimalFuelConsumption { crabPosition, destination -> abs(crabPosition - destination) }

    fun star2(input: String?) = input.toData().split(",").map { it.toInt() }
        .minimalFuelConsumption { crabPosition, destination ->
            abs(crabPosition - destination).let {
                it * (it + 1) / 2
            }
        }

    private fun List<Int>.minimalFuelConsumption(computeFuelConsumption: (Int, Int) -> Int) =
        (minOf { it }..maxOf { it })
            .minOf { point ->
                fold(0) { acc, destination ->
                    (acc + computeFuelConsumption(point, destination))

                }
            }

}

fun main() {
    processStars(Day7::star1, Day7::star2)
}
