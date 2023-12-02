package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day02 {

    private fun String?.toData() = this ?: data(2)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .toGames()
        .filter { game ->
            game.sets.all { it.red <= 12 && it.green <= 13 && it.blue <= 14 }
        }
        .sumOf { it.id }

    fun star2(input: String?) = input
        .toData()
        .lines()
        .toGames()
        .sumOf { game ->
            game.sets.maxOf { it.red } * game.sets.maxOf { it.green } * game.sets.maxOf { it.blue }
        }

    private fun List<String>.toGames() = map { line ->
        Game(
            id = line.substringAfter("Game ").substringBefore(":").toLong(),
            sets = line.substringAfter(":").split(";").map { set ->
                set.split(",").let { colors ->
                    RGB(
                        red = colors
                            .firstOrNull { it.contains("red") }
                            ?.substringBefore(" red")
                            ?.trim()
                            ?.toLong()
                            ?: 0,
                        blue = colors
                            .firstOrNull { it.contains("blue") }
                            ?.substringBefore(" blue")
                            ?.trim()?.toLong()
                            ?: 0,
                        green = colors
                            .firstOrNull { it.contains("green") }
                            ?.substringBefore(" green")
                            ?.trim()
                            ?.toLong()
                            ?: 0
                    )
                }
            }
        )
    }

    private data class Game(val id: Long, val sets: List<RGB>)
    private data class RGB(val red: Long, val green: Long, val blue: Long)
}

fun main() {
    processStars(Day02::star1, Day02::star2)
}
