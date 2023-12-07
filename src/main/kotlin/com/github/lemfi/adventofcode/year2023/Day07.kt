package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day07 {

    private fun String?.toData() = this ?: data(7)

    fun star1(input: String?) = input
        .totalWinnings { buildHandStar1() }

    fun star2(input: String?) = input
        .totalWinnings { buildHandStar2() }

    private fun String?.totalWinnings(handBuilder: List<String>.() -> List<Hand>) = toData()
        .lines()
        .handBuilder()
        .sorted()
        .mapIndexed { index, hand -> hand.bid * (index + 1) }
        .sum()

    private fun List<String>.buildHandStar1() = map {
        it
            .split(" ")
            .let { (hand, bid) -> hand.buildHand(bid.toLong()) }
    }

    private fun List<String>.buildHandStar2() = map {
        it
            .split(" ")
            .let { (hand, bid) -> hand.buildHandStar2(bid.toLong()) }
    }

    private fun String.buildHand(bid: Long) =
        map { it.toCard(11) }
            .let { cards ->
                cards.groupBy { it }
                    .map { it.key to it.value.size }
                    .buildHand(cards, bid)
            }

    private fun String.buildHandStar2(bid: Long) =
        map { it.toCard(0) }
            .let { cards ->
                cards.groupBy { it }
                    .map { it.key to it.value.size }
                    .buildHandStar2(cards, bid)
            }

    private fun List<Pair<Card, Int>>.buildHandStar2(cards: List<Card>, bid: Long): Hand =

        cards
            .count { it is JokerCard }
            .let { jokers ->

                when (jokers) {
                    0 -> buildHand(cards, bid)
                    5 -> Five(cards, bid)
                    4 -> Five(cards, bid)
                    else ->
                        when (filterNot { it.first is JokerCard }.buildHand(cards, bid)) {
                            is Four -> Five(cards, bid)
                            is Three -> when (jokers) {
                                2 -> Five(cards, bid)
                                1 -> Four(cards, bid)
                                else -> error("... wtf ...")
                            }

                            is TwoPair -> Full(cards, bid)
                            is OnePair -> when (jokers) {
                                3 -> Five(cards, bid)
                                2 -> Four(cards, bid)
                                1 -> Three(cards, bid)
                                else -> error("... wtf ...")
                            }

                            is HighCard -> when (jokers) {
                                3 -> Four(cards, bid)
                                2 -> Three(cards, bid)
                                1 -> OnePair(cards, bid)
                                else -> error("... wtf ...")
                            }

                            else -> error("... wtf ...")
                        }
                }
            }

    private fun List<Pair<Card, Int>>.buildHand(cards: List<Card>, bid: Long): Hand =
        map { (_, occurrences) -> occurrences }
            .let { occurrences ->
                when {
                    occurrences.any { it == 5 } -> Five(cards, bid)
                    occurrences.any { it == 4 } -> Four(cards, bid)
                    occurrences.any { it == 3 } && occurrences.any { it == 2 } -> Full(cards, bid)
                    occurrences.any { it == 3 } -> Three(cards, bid)
                    occurrences.count { it == 2 } == 2 -> TwoPair(cards, bid)
                    occurrences.any { it == 2 } -> OnePair(cards, bid)
                    else -> HighCard(cards, bid)
                }
            }


    private fun Char.toCard(jCardWeight: Int) =
        when (this) {
            'A' -> RegularCard(14)
            'K' -> RegularCard(13)
            'Q' -> RegularCard(12)
            'J' -> JokerCard(jCardWeight)
            'T' -> RegularCard(10)
            '9' -> RegularCard(9)
            '8' -> RegularCard(8)
            '7' -> RegularCard(7)
            '6' -> RegularCard(6)
            '5' -> RegularCard(5)
            '4' -> RegularCard(4)
            '3' -> RegularCard(3)
            '2' -> RegularCard(2)
            else -> error("... wrong card ...")
        }

    sealed interface Hand : Comparable<Hand> {
        val weight: Int
        val cards: List<Card>
        val bid: Long

        override fun compareTo(other: Hand): Int =
            if (weight == other.weight) cards.compareTo(other.cards)
            else weight - other.weight

        private fun List<Card>.compareTo(cards: List<Card>): Int {

            indices.forEach { index ->
                get(index).weight
                    .compareTo(cards[index].weight)
                    .also { if (it != 0) return it }
            }
            return 0
        }
    }

    data class Five(
        override val cards: List<Card>,
        override val bid: Long,
    ) : Hand {
        override val weight = 7
    }

    data class Four(
        override val cards: List<Card>,
        override val bid: Long,
    ) : Hand {
        override val weight = 6
    }

    data class Full(
        override val cards: List<Card>,
        override val bid: Long,
    ) : Hand {
        override val weight = 5
    }

    data class Three(
        override val cards: List<Card>,
        override val bid: Long,
    ) : Hand {
        override val weight = 4
    }

    data class TwoPair(
        override val cards: List<Card>,
        override val bid: Long,
    ) : Hand {
        override val weight = 3
    }

    data class OnePair(
        override val cards: List<Card>,
        override val bid: Long,
    ) : Hand {
        override val weight = 2
    }

    data class HighCard(
        override val cards: List<Card>,
        override val bid: Long,
    ) : Hand {
        override val weight = 1
    }

    sealed interface Card {
        val weight: Int
    }

    @JvmInline
    value class RegularCard(
        override val weight: Int,
    ) : Card

    @JvmInline
    value class JokerCard(
        override val weight: Int,
    ) : Card
}

fun main() {
    processStars(Day07::star1, Day07::star2)
}