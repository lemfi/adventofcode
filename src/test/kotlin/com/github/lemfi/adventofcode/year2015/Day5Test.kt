package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(
            2, Day5.star1(
                """ugknbfddgicrmopn
            |aaa
            |jchzalrnumimnmhp
            |haegwjzuvuyypxyu
            |dvszwmarrgswjxmb
        """.trimMargin()
            )
        )
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(
            2, Day5.star2(
                """qjhvhtzxzqqjkmpb
            |xxyxx
            |uurcxstgmygtbstg
            |ieodomkazucvgmuy
        """.trimMargin()
            )
        )
    }

    @Test
    fun overlap() {
        Assertions.assertEquals(
            2, Day5.star2(
                """aaa
            |aaaa
            |aabaa
        """.trimMargin()
            )
        )
    }

    @Test
    fun star1() {
        Assertions.assertEquals(258, Day5.star1(null))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(53, Day5.star2(null))
    }
}