package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day8Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(5, day8_1("""nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6""".trimMargin()))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(8, day8_2("""nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6""".trimMargin()))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(1137, day8_1(day8data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(1125, day8_2(day8data))
    }

}