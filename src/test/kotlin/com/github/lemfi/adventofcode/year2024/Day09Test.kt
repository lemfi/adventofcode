package com.github.lemfi.adventofcode.year2024

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day09Test {

    @Test
    fun star1() {
        Assertions.assertEquals(1928, Day09.star1("""2333133121414131402"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(2858, Day09.star2("""2333133121414131402"""))
    }
}