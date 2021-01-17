package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(37, Day11.star1("""L.LL.LL.LL
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

        Assertions.assertEquals(26, Day11.star2("""L.LL.LL.LL
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
        Assertions.assertEquals(2406, Day11.star1(Day11.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(2149, Day11.star2(Day11.data))
    }

}