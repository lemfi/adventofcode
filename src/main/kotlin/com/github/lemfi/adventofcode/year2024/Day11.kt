package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day11 {

    private fun String?.toData() = this ?: data(11)

    fun star1(input: String?) = input.parse().solve(25)

    fun star2(input: String?) = input.parse().solve(75)

    private fun String?.parse() = toData().split(" ").map { it.toLong() }

    private fun List<Long>.solve(blinks: Int): Long =
        associateWith { 1L }
            .toMutableMap()
            .apply {
                repeat(blinks) {
                    val newStones = mutableMapOf<Long, Long>()
                    entries.onEach { (stone, times) ->
                        when {
                            stone == 0L -> newStones.add(1, times)

                            "$stone".length % 2 == 0 -> "$stone".also { stoneAsString ->
                                stoneAsString
                                    .windowed(stoneAsString.length / 2, stoneAsString.length / 2)
                                    .map { it.toLong() }
                                    .forEach { newValue -> newStones.add(newValue, times) }
                            }

                            else -> newStones.add(stone * 2024, times)
                        }
                    }
                    clear()
                    putAll(newStones)
                }
            }.values.sum()

    private fun MutableMap<Long, Long>.add(newValue: Long, number: Long) {
        this[newValue] = (this[newValue] ?: 0) + number
    }
}

fun main() {
    processStars(Day11::star1, Day11::star2)
}