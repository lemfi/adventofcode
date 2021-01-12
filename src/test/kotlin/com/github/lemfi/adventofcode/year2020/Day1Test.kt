package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(514579, Day1.star1("""1721
            |979
            |366
            |299
            |675
            |1456""".trimMargin()))
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(241861950, Day1.star2("""1721
            |979
            |366
            |299
            |675
            |1456""".trimMargin())
        )
    }

    @Test
    fun real1() {
        Assertions.assertEquals(646779, Day1.star1(Day1.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(246191688, Day1.star2(Day1.data))
    }
}