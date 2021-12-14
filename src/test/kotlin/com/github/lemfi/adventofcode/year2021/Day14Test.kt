package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day14.Day14
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            1588, Day14.star1(
                """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            2188189693529L, Day14.star2(
                """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(3143, Day14.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(4110215602456L, Day14.star2(null))
    }
}