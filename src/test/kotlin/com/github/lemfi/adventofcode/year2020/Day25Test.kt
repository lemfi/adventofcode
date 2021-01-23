package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day25Test {

    @Test
    fun sample1() {

            Assertions.assertEquals(14897079, Day25.star("""5764801
            |17807724
        """.trimMargin()))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(4968512, Day25.star(Day25.data))
    }

}