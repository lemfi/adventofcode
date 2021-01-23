package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.system.measureTimeMillis


fun main() {

    measureTimeMillis {
        Day22.star1(Day22.data)
            .apply { println("res: $this") }
    }.apply { println("   in ${this / 1000}s") }

    measureTimeMillis {

        Day22.star2(Day22.data)
            .apply { println("res: $this") }
    }.apply { println("   in ${this / 1000}s") }

}
object Day22 {


    fun star1(data: String): Long {

        return Game1(data.parse()).let {
            it.play()
            it.score()
        }
    }

    fun star2(data: String): Long {

        return Game2(gameIndex, data.parse()).let {
            it.play()
            it.score()
        }
    }

    var gameIndex = 0

    fun String.parse() =
        split("\n\n").let {
            val player1 = Player(it.first().lines().drop(1).map { it.toInt() }.toMutableList())
            val player2 = Player(it.last().lines().drop(1).map { it.toInt() }.toMutableList())
            listOf(player1, player2)
        }

    data class Game1(
        val players: List<Player>
    ) {
        val nbCards = players.fold(0) {res, player -> res + player.deck.size}

        fun play(): Player {
            while (!end()) round()
            return winner()
        }

        fun round(previousTurns: List<Pair<Int, Int>> = listOf()) = players.map { it to it.deck.first() }.sortedByDescending { it.second }.let {
            it.first().first.deck.removeFirst()
            it.first().first.deck += it.map { it.second }.sortedDescending()
            it.drop(1).forEach {
                it.first.deck.removeFirst()
            }
        }

        fun end() = players.any { it.deck.size == nbCards }
        fun winner() = players.first { it.deck.size == nbCards }

        fun score() = winner().deck.reversed().foldIndexed(0) { index: Int, acc: Long, i: Int -> (index + 1) * i + acc }

    }


    data class Game2(
        val name: Int = 0,
        val players: List<Player>
    ) {
        val nbCards = players.fold(0) { res, player -> res + player.deck.size }

        var recurseFound = false

        fun play(): Int {
            var previousTurns: MutableList<List<List<Int>>> = mutableListOf()
            while (!end()) previousTurns = round(previousTurns)
            return winnerIndex()
        }

        fun round(previousTurns: MutableList<List<List<Int>>>): MutableList<List<List<Int>>> {

            return players.map { it to it.deck.first() }.let {

                if (previousTurns.contains(listOf(it.first().first.deck, it.last().first.deck))) recurseFound = true
                else {

                    previousTurns.add(listOf(it.first().first.deck.toList(), it.last().first.deck.toList()))

                    lateinit var roundWinner: Player
                    lateinit var roundLooser: Player
                    if (it.first().second < it.first().first.deck.size && it.last().second < it.last().first.deck.size) {
                        gameIndex ++
                        val winnerIndex = Game2(gameIndex,
                            listOf(
                                players.first().copy(deck = players.first().deck.drop(1).take(it.first().second).toMutableList()),
                                players.last().copy(deck = players.last().deck.drop(1).take(it.last().second).toMutableList()))
                        ).play()
                        roundWinner = players[winnerIndex]
                        roundLooser = it.filterIndexed { index, _ -> index != winnerIndex }.first().first
                    }
                    else
                        it.sortedByDescending { it.second }.let {
                            roundWinner = it.first().first
                            roundLooser = it.last().first
                        }

                    roundWinner.deck += roundWinner.deck.removeFirst()
                    roundWinner.deck += roundLooser.deck.removeFirst()
                }

                previousTurns
            }
        }

        fun end() =  recurseFound || players.any { it.deck.size == nbCards }
        fun winnerIndex() = if (recurseFound) 0 else players.indexOfFirst { it.deck.size == nbCards }
        fun winner() = if (recurseFound) players.first() else players.first { it.deck.size == nbCards }

        fun score() = winner().deck.reversed().foldIndexed(0) { index: Int, acc: Long, i: Int -> (index + 1) * i + acc }

    }

    data class Player(
        val deck: MutableList<Int>
    )

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day22.txt").readAllBytes().toString(Charset.defaultCharset()) }

}