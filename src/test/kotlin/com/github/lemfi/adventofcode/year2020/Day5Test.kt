package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(567, Day5.getSeat("BFFFBBFRRR"))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(119, Day5.getSeat("FFFBBBFRRR"))
    }

    @Test
    fun sample3() {

        Assertions.assertEquals(820, Day5.getSeat("BBFFBBFRLL"))
    }

    @Test
    fun sample4() {

        Assertions.assertEquals(1023, Day5.getSeat("BBBBBBBRRR"))
    }

    @Test
    fun sample5() {

        Assertions.assertEquals(0, Day5.getSeat("FFFFFFFLLL"))
    }

    @Test
    fun real1() {

        Assertions.assertEquals(908, Day5.star1(Day5.data))
    }

    @Test
    fun real2() {

        Assertions.assertEquals(619, Day5.star2(Day5.data))
    }
}