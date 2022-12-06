package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun star1() {
        Assertions.assertEquals(7, Day6.star1("""mjqjpqmgbljsphdztnvjfqwrcgsmlb"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(19, Day6.star2("""mjqjpqmgbljsphdztnvjfqwrcgsmlb"""))
    }
}