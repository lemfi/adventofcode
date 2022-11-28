package com.github.lemfi.adventofcode.year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun star11() {
        Assertions.assertEquals(
            10, Day12.star1(
                """start-A
start-b
A-c
A-b
b-d
A-end
b-end"""
            )
        )
    }

    @Test
    fun star12() {
        Assertions.assertEquals(
            226, Day12.star1(
                """fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW
"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            36, Day12.star2(
                """start-A
start-b
A-c
A-b
b-d
A-end
b-end"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(5756, Day12.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(144603, Day12.star2(null))
    }
}