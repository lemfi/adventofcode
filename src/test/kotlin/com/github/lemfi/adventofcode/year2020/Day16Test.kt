package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day16Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(71, Day16.star1("""class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(20058, Day16.star1(Day16.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(366871907221, Day16.star2(Day16.data))
    }

}