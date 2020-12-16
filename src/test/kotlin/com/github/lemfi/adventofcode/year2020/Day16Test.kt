package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day16Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(71, day16_1("""class: 1-3 or 5-7
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

//    @Test
//    fun sample2() {
//
//        Assertions.assertEquals(-1, day16_2(""""""))
//    }
//
    @Test
    fun real1() {
        Assertions.assertEquals(20058, day16_1(day16data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(366871907221, day16_2(day16data))
    }

}