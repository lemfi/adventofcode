package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(2, day2_1("""1-3 a: abcde
            |1-3 b: cdefg
            |2-9 c: ccccccccc""".trimMargin())
        )
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(1, day2_2("""1-3 a: abcde
            |1-3 b: cdefg
            |2-9 c: ccccccccc""".trimMargin())
        )
    }
}