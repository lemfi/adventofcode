package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day2 {

    private fun String?.toData() = this ?: data(2)

    private fun String?.prepare() = toData()
        .lines()
        .map {
            it.split(" ")
        }

    fun star1(input: String?) = input
        .prepare()
        .map { (player1, player2) ->
            toPlayed(player1) to toPlayed(player2)
        }.sumOf { (player1, player2) ->
            player2.score + result(player1, player2).score
        }

    fun star2(input: String?) = input
        .prepare()
        .map { (player1, result) ->
            toPlayed(player1) to toResult(result)
        }.sumOf { (player1, result) ->
            result.score + played(player1, result).score
        }

    private fun toResult(result: String) = when (result) {
        "X" -> Lose
        "Y" -> Draw
        "Z" -> Win
        else -> error("...")
    }

    private fun toPlayed(player1: String) = when (player1) {
        "A", "X" -> Rock
        "B", "Y" -> Paper
        "C", "Z" -> Scissors
        else -> error("...")
    }

    fun result(p1: Play, p2: Play) =
        when (p1) {
            is Rock -> when (p2) {
                is Rock -> Draw
                is Paper -> Win
                is Scissors -> Lose
            }

            is Paper -> when (p2) {
                is Rock -> Lose
                is Paper -> Draw
                is Scissors -> Win
            }

            is Scissors -> when (p2) {
                is Rock -> Win
                is Paper -> Lose
                is Scissors -> Draw
            }
        }

    fun played(p1: Play, p2: Result) =
        when (p1) {
            is Rock -> when (p2) {
                is Lose -> Scissors
                is Draw -> Rock
                is Win -> Paper
            }

            is Paper -> when (p2) {
                is Lose -> Rock
                is Draw -> Paper
                is Win -> Scissors
            }

            is Scissors -> when (p2) {
                is Lose -> Paper
                is Draw -> Scissors
                is Win -> Rock
            }
        }

    sealed interface Score {
        val score: Int
    }

    sealed interface Play : Score

    object Rock : Play {
        override val score: Int = 1
    }

    object Paper : Play {
        override val score: Int = 2
    }

    object Scissors : Play {
        override val score: Int = 3
    }

    sealed interface Result : Score

    object Win : Result {
        override val score: Int = 6
    }

    object Lose : Result {
        override val score: Int = 0
    }

    object Draw : Result {
        override val score: Int = 3
    }
}

fun main() {
    processStars(Day2::star1, Day2::star2)
}