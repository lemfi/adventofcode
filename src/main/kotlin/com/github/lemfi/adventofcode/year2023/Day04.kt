package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars
import kotlin.math.pow

object Day04 {

    private fun String?.toData() = this ?: data(4)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .toCards()
        .sumOf { if (it.toCopy.isEmpty()) 0.0 else 2.0.pow(it.toCopy.size - 1) }
        .toInt()

    fun star2(input: String?) = input
        .toData()
        .lines()
        .toCards()
        .let {
            buildMap {
                putAll(it.map { it.name to it })
            }
        }
        .let { cards ->
            cards
                .values
                .onEach { card ->
                    card.toCopy.forEach {
                        cards.getValue(it).occurrences += card.occurrences
                    }
                }
                .sumOf { it.occurrences }

        }

    private fun List<String>.toCards() = mapIndexed { index, it ->
        it
            .substringAfter(": ")
            .split("|")
            .let { (win, cards) ->
                Regex("""\d+""").findAll(win).map { it.value.toLong() }.toList() to Regex("""\d+""").findAll(cards)
                    .map { it.value.toLong() }.toList()
            }
            .let { (win, match) ->
                Card(index + 1L, win, match)
            }
    }

    private data class Card(val name: Long, val win: List<Long>, val match: List<Long>, var occurrences: Int = 1) {
        val toCopy = List(match.filter { win.contains(it) }.size) { index -> index + name + 1 }
    }

}

fun main() {
    processStars(Day04::star1, Day04::star2)
}
