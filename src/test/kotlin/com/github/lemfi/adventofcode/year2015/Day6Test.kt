package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.year2015.Day6.Day6
import com.github.lemfi.adventofcode.year2015.Day7.Day7
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(1000 * 1000, Day6.star1("turn on 0,0 through 999,999"))
        Assertions.assertEquals(
            0, Day6.star1(
                """turn on 0,0 through 999,999
            |toggle 0,0 through 999,999
        """.trimMargin()
            )
        )
        Assertions.assertEquals(1000, Day6.star1("toggle 0,0 through 999,0"))
        Assertions.assertEquals(0, Day6.star1("turn off 499,499 through 500,500"))
    }

    @Test
    fun star1() {
        Assertions.assertEquals(569999, Day6.star1(null))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(17836115, Day6.star2(null))
    }
}