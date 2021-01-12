package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(2, Day2.star1("""1-3 a: abcde
            |1-3 b: cdefg
            |2-9 c: ccccccccc""".trimMargin())
        )
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(1, Day2.star2("""1-3 a: abcde
            |1-3 b: cdefg
            |2-9 c: ccccccccc""".trimMargin())
        )
    }

    @Test
    fun real1() {
        Assertions.assertEquals(580, Day2.star1(Day2.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(611, Day2.star2(Day2.data)
        )
    }
}