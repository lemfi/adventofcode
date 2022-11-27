package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            605, Day9.star1(
                """London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
""".trimMargin()
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            982, Day9.star2(
                """London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141
""".trimMargin()
            )
        )
    }
}