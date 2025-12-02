package com.github.lemfi.adventofcode.year2025

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun star1() {
        Assertions.assertEquals(1227775554, Day02.star1("""11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(4174379265L, Day02.star2("""11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""))
    }

    @Test
    fun star21() {
        Assertions.assertEquals(33L, Day02.star2("""11-22"""))
    }

    @Test
    fun star22() {
        Assertions.assertEquals(210L, Day02.star2("""95-115"""))
    }

    @Test
    fun star23() {
        Assertions.assertEquals(999L + 1010L, Day02.star2("""998-1012"""))
    }
}