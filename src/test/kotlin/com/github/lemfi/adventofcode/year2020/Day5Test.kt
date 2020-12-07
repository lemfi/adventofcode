package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(567, day5GetSeat("BFFFBBFRRR"))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(119, day5GetSeat("FFFBBBFRRR"))
    }

    @Test
    fun sample3() {

        Assertions.assertEquals(820, day5GetSeat("BBFFBBFRLL"))
    }

    @Test
    fun sample4() {

        Assertions.assertEquals(1023, day5GetSeat("BBBBBBBRRR"))
    }

    @Test
    fun sample5() {

        Assertions.assertEquals(0, day5GetSeat("FFFFFFFLLL"))
    }
}