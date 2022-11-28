package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day21 {

    private fun String?.toData() = this ?: data(21)

    private fun String?.prepareData() = toData().lines().let { (player1, player2) ->
        Player(
            "player1", player1.substringAfter("starting position: ").toInt()
        ) to Player(
            "player2", player2.substringAfter("starting position: ").toInt()
        )
    }

    fun star1(input: String?) = input
        .prepareData()
        .let { (player1, player2) -> Universe1().play(player1, player2) }

    fun star2(input: String?) = input
        .prepareData()
        .let { (player1, player2) -> Universe2().play(player1, player2) }


    class Universe1 {
        fun play(player1: Player, player2: Player) = Game(player1, player2, Dice(1, 100), 1000).play()

        data class Dice(var value: Int, val faces: Int, var rolls: Long = 0) {
            private fun Int.mod() = (this % faces).let { if (it == 0) faces else it }
            fun roll(): Int = ((value).mod() + (value + 1).mod() + (value + 2).mod()).apply {
                value = (value + 3) % 100
                rolls += 3
            }
        }

        data class Game(val player1: Player, val player2: Player, val dice: Dice, val pointsToEnd: Int) {

            fun play(): Long {
                while (player1.points < pointsToEnd && player2.points < pointsToEnd) {
                    player1.turn(dice.roll())
                    if (player1.points < pointsToEnd) player2.turn(dice.roll())
                }
                val loser = if (player1.points >= pointsToEnd) player2 else player1
                return loser.points * dice.rolls
            }
        }
    }

    class Universe2 {

        private val pointsToEnd = 21L
        private val games = mutableMapOf<Game, Long>()

        val quantumDice = (1..3)
            .flatMap { a ->
                (1..3).flatMap { b ->
                    (1..3).map { c ->
                        Triple(a, b, c)
                    }
                }
            }
            .map { (a, b, c) -> Triple(a, b, c) to (a + b + c) }
            .groupBy { (_, diceValue) -> diceValue }
            .map { it.key to it.value.size.toLong() } // dice value => occurrences for this value

        private fun addGame(game: Game, occurrences: Long = 1L) {
            games[game] = (games[game] ?: 0L) + occurrences
        }

        fun play(player1: Player, player2: Player): Long {

            addGame(Game(listOf(player1, player2)))

            var res = 0L to 0L
            while (games.isNotEmpty()) {
                games.asIterable().first().also { (game, occurrences) ->
                    games.remove(game)
                    res += game.play(this, occurrences)
                }
            }
            return if (res.first > res.second) res.first else res.second
        }

        private fun Game.play(universe: Universe2, occurrences: Long): Pair<Long, Long> {
            while (player1.points < pointsToEnd && player2.points < pointsToEnd) {
                val nextPlayer = (currentPlayer + 1) % 2
                universe.quantumDice.drop(1).forEach { (diceValue, newOccurrence) ->
                    universe.addGame(
                        Game(
                            players.map { it.copy() }.apply { get(currentPlayer).turn(diceValue) },
                            nextPlayer,
                        ),
                        occurrences * newOccurrence
                    )
                }
                players[currentPlayer].turn(universe.quantumDice.first().first)
                currentPlayer = nextPlayer
            }
            return if (player1.points >= pointsToEnd) occurrences to 0L else 0L to occurrences
        }

        data class Game(
            val players: List<Player>,
            var currentPlayer: Int = 0,
        ) {
            val player1 = players[0]
            val player2 = players[1]
        }

        private operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) =
            (first + other.first) to (second + other.second)

    }

    data class Player(val name: String, var position: Int, var points: Long = 0) {
        private fun Int.mod() = (this % 10).let { if (it == 0) 10 else it }

        fun turn(value: Int) {
            position = (position + value).mod()
            points += position
        }
    }
}

fun main() {
    processStars(Day21::star1, Day21::star2)
}
