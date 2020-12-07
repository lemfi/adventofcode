package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(11, day6_1("""abc
            |
            |a
            |b
            |c
            |
            |ab
            |ac
            |
            |a
            |a
            |a
            |a
            |
            |b""".trimMargin()))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(6, day6_2("""abc
            |
            |a
            |b
            |c
            |
            |ab
            |ac
            |
            |a
            |a
            |a
            |a
            |
            |b""".trimMargin()))
    }

    @Test
    fun real1() {

        Assertions.assertEquals(7120, day6_1(day6data))
    }

    @Test
    fun real2() {

        Assertions.assertEquals(3570, day6_2(day6data))
    }

}