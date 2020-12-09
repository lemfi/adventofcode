package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(127, day9_1(5, """35
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

        Assertions.assertEquals(62, day9_2(5, """35
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
        Assertions.assertEquals(466456641, day9_1(25, day9data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(55732936, day9_2(25, day9data))
    }

}