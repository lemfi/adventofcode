package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            "CMZ", Day5.star1(
                """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            "MCD", Day5.star2(
                """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"""
            )
        )
    }
}