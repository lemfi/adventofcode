package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(514579, day1_1("""1721
            |979
            |366
            |299
            |675
            |1456""".trimMargin()))
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(241861950, day1_2("""1721
            |979
            |366
            |299
            |675
            |1456""".trimMargin())
        )
    }

    @Test
    fun real1() {
        Assertions.assertEquals(646779, day1_1(data1))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(246191688, day1_2(data1))
    }
}