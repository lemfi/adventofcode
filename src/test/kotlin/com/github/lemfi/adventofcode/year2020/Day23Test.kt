package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day23Test {

    @Test
    fun sample100() {
        Assertions.assertEquals("25467389", Day23.star1(0, """389125467"""))
    }

    @Test
    fun sample101() {
        Assertions.assertEquals("54673289", Day23.star1(1, """389125467"""))

    }

    @Test
    fun sample102() {
        Assertions.assertEquals("32546789", Day23.star1(2, """389125467"""))
    }

    @Test
    fun sample103() {
        Assertions.assertEquals("34672589", Day23.star1(3, """389125467"""))

    }

    @Test
    fun sample104() {

        Assertions.assertEquals("32584679", Day23.star1(4, """389125467"""))
    }

    @Test
    fun sample105() {

        Assertions.assertEquals("36792584", Day23.star1(5, """389125467"""))
    }

    @Test
    fun sample106() {

        Assertions.assertEquals("93672584", Day23.star1(6, """389125467"""))
    }

    @Test
    fun sample107() {

        Assertions.assertEquals("92583674", Day23.star1(7, """389125467"""))
    }

    @Test
    fun sample108() {

        Assertions.assertEquals("58392674", Day23.star1(8, """389125467"""))
    }

    @Test
    fun sample109() {

        Assertions.assertEquals("83926574", Day23.star1(9, """389125467"""))
    }

    @Test
    fun sample110() {

        Assertions.assertEquals("92658374", Day23.star1(10, """389125467"""))
    }

    @Test
    fun sample10() {

        Assertions.assertEquals("92658374", Day23.star1(10, """389125467"""))
    }

    @Test
    fun move1() {

        Assertions.assertEquals("56789234", Day23.star1(1, """123456789"""))
    }

    @Test
    fun move2() {

        Assertions.assertEquals("59234678", Day23.star1(2, """123456789"""))
    }

    @Test
    fun move3() {

        Assertions.assertEquals("59678234", Day23.star1(3, """123456789"""))
    }

    @Test
    fun move4() {

        Assertions.assertEquals("57829634", Day23.star1(4, """123456789"""))
    }

    @Test
    fun sample11() {

        Assertions.assertEquals("67384529", Day23.star1(100, """389125467"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(149245887792, Day23.star2(10_000_000, """389125467"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals("25468379", Day23.star1(100, Day23.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(474747880250, Day23.star2(10_000_000, Day23.data))
    }

}