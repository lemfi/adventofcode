package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day7.Day7
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun star1() {
        Assertions.assertEquals(37, Day7.star1("""16,1,2,0,4,2,7,1,2,14"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(168, Day7.star2("""16,1,2,0,4,2,7,1,2,14"""))
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(341558, Day7.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(93214037, Day7.star2(null))
    }
}