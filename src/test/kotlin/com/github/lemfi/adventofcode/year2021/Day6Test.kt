package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day6.Day6
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun star1() {
        Assertions.assertEquals(5934, Day6.star1("""3,4,3,1,2"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(26984457539, Day6.star2("""3,4,3,1,2"""))
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(359999, Day6.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(1631647919273, Day6.star2(null))
    }
}