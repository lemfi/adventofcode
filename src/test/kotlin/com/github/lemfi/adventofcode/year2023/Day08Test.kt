package com.github.lemfi.adventofcode.year2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day08Test {

    @Test
    fun star11() {
        Assertions.assertEquals(
            2, Day08.star1(
                """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)"""
            )
        )
    }

    @Test
    fun star12() {
        Assertions.assertEquals(
            6, Day08.star1(
                """LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            6, Day08.star2(
                """LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)"""
            )
        )
    }
}