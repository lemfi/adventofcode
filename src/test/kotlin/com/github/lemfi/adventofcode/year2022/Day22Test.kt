package com.github.lemfi.adventofcode.year2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day22Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            6032, Day22.star1(
                """        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            5031, Day22.star2(
                """        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5"""
            )
        )
    }

    private val cube = """
                        ...#
                        .#..
                        #...
                        ....
                        
        ...#    ....    ...#
        ....    ....    #...
        ..#.    ...#    ....
        ....    ....    ..#.
        
                        ...#    ....
                        ....    .#..
                        .#..    ....
                        ....    ..#.
                     
                
    """.trimIndent()
}
