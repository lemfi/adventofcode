package com.github.lemfi.adventofcode.year2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day07Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            6440, Day07.star1(
                """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            5905, Day07.star2(
                """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
            )
        )
    }
}