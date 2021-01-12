package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(127, Day9.star1(5, """35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576""".trimMargin()))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(62, Day9.star2(5, """35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576""".trimMargin()))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(466456641, Day9.star1(25, Day9.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(55732936, Day9.star2(25, Day9.data))
    }

}