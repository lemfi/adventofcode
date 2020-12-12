package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(37, day11_1("""L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(26, day11_2("""L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(2406, day11_1(day11data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(2149, day11_2(day11data))
    }

}