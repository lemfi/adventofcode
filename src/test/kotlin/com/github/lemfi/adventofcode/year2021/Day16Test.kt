package com.github.lemfi.adventofcode.year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    fun star11() {
        Assertions.assertEquals(16, Day16.star1("""8A004A801A8002F478"""))
    }

    @Test
    fun star12() {
        Assertions.assertEquals(12, Day16.star1("""620080001611562C8802118E34"""))
    }

    @Test
    fun star13() {
        Assertions.assertEquals(23, Day16.star1("""C0015000016115A2E0802F182340"""))
    }

    @Test
    fun star14() {
        Assertions.assertEquals(31, Day16.star1("""A0016C880162017C3686B18A3D4780"""))
    }

    @Test
    fun star21() {
        Assertions.assertEquals(3, Day16.star2("""C200B40A82"""))
    }

    @Test
    fun star22() {
        Assertions.assertEquals(54, Day16.star2("""04005AC33890"""))
    }

    @Test
    fun star23() {
        Assertions.assertEquals(7, Day16.star2("""880086C3E88112"""))
    }

    @Test
    fun star24() {
        Assertions.assertEquals(9, Day16.star2("""CE00C43D881120"""))
    }

    @Test
    fun star25() {
        Assertions.assertEquals(1, Day16.star2("""D8005AC2A8F0"""))
    }

    @Test
    fun star26() {
        Assertions.assertEquals(0, Day16.star2("""F600BC2D8F"""))
    }

    @Test
    fun star27() {
        Assertions.assertEquals(0, Day16.star2("""9C005AC2F8F0"""))
    }

    @Test
    fun star28() {
        Assertions.assertEquals(1, Day16.star2("""9C0141080250320F1802104A08"""))
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(1002, Day16.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(1673210814091, Day16.star2(null))
    }

}