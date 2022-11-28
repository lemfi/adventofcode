package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day4Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(609043, Day4.star1("abcdef"))
        Assertions.assertEquals(1048970, Day4.star1("pqrstuv"))
    }

    @Test
    fun star1() {
        Assertions.assertEquals(254575, Day4.star1(null))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(1038736, Day4.star2(null))
    }
}