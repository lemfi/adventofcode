package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day15Test {


    @Test
    fun sample10() {

        Assertions.assertEquals(436, day15_1("""0,3,6"""))
    }

    @Test
    fun sample11() {

        Assertions.assertEquals(1, day15_1("""1,3,2"""))
    }

    @Test
    fun sample12() {

        Assertions.assertEquals(10, day15_1("""2,1,3"""))
    }

    @Test
    fun sample13() {

        Assertions.assertEquals(27, day15_1("""1,2,3"""))
    }

    @Test
    fun sample14() {

        Assertions.assertEquals(78, day15_1("""2,3,1"""))
    }

    @Test
    fun sample15() {

        Assertions.assertEquals(438, day15_1("""3,2,1"""))
    }

    @Test
    fun sample16() {

        Assertions.assertEquals(1836, day15_1("""3,1,2"""))
    }

    @Test
    fun sample21() {

        Assertions.assertEquals(2578, day15_2("""1,3,2"""))
    }

    @Test
    fun sample22() {

        Assertions.assertEquals(3544142, day15_2("""2,1,3"""))
    }

    @Test
    fun sample23() {

        Assertions.assertEquals(261214, day15_2("""1,2,3"""))
    }

    @Test
    fun sample24() {

        Assertions.assertEquals(6895259, day15_2("""2,3,1"""))
    }

    @Test
    fun sample25() {

        Assertions.assertEquals(18, day15_2("""3,2,1"""))
    }

    @Test
    fun sample26() {

        Assertions.assertEquals(362, day15_2("""3,1,2"""))
    }

    @Test
    fun sample27() {

        Assertions.assertEquals(175594, day15_2("""0,3,6"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(447, day15_1(day15data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(11721679, day15_2(day15data))
    }

}