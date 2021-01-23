package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun solve() {
        Assertions.assertEquals(5, Day18.star1("2 + 3"))
        Assertions.assertEquals(6, Day18.star1("2 * 3"))
        Assertions.assertEquals(14, Day18.star1("2 * (3 + 4)"))
        Assertions.assertEquals(10, Day18.star1("(2 * 3) + 4"))
        Assertions.assertEquals(60, Day18.star1("2 * 3 + 4 * 6"))
        Assertions.assertEquals(30, Day18.star1("2 * 3 + (4 * 6)"))
        Assertions.assertEquals(84, Day18.star1("2 * (3 + 4) * 6"))

    }

    @Test
    fun sample11() {

        Assertions.assertEquals(26, Day18.star1("""2 * 3 + (4 * 5)"""))
    }

    @Test
    fun sample12() {

        Assertions.assertEquals(437, Day18.star1("""5 + (8 * 3 + 9 + 3 * 4 * 3)"""))
    }

    @Test
    fun sample13() {

        Assertions.assertEquals(12240, Day18.star1("""5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"""))
    }

    @Test
    fun sample14() {

        Assertions.assertEquals(13632, Day18.star1("""((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"""))
    }

    @Test
    fun sample21() {

        Assertions.assertEquals(51, Day18.star2("""1 + (2 * 3) + (4 * (5 + 6))"""))
    }

    @Test
    fun sample22() {

        Assertions.assertEquals(46, Day18.star2("""2 * 3 + (4 * 5)"""))
    }

    @Test
    fun sample23() {

        Assertions.assertEquals(1445, Day18.star2("""5 + (8 * 3 + 9 + 3 * 4 * 3)"""))
    }

    @Test
    fun sample24() {

        Assertions.assertEquals(669060, Day18.star2("""5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"""))
    }

    @Test
    fun sample25() {

        Assertions.assertEquals(23340, Day18.star2("""((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(3348222486398, Day18.star1(Day18.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(43423343619505, Day18.star2(Day18.data))
    }

}