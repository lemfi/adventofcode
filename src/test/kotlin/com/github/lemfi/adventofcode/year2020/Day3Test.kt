package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day3Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(7, day3_1("""..##.......
            |#...#...#..
            |.#....#..#.
            |..#.#...#.#
            |.#...##..#.
            |..#.##.....
            |.#.#.#....#
            |.#........#
            |#.##...#...
            |#...##....#
            |.#..#...#.#""".trimMargin())
        )
    }

    @Test
    fun sample2() {
        Assertions.assertEquals(336, day3_2("""..##.......
            |#...#...#..
            |.#....#..#.
            |..#.#...#.#
            |.#...##..#.
            |..#.##.....
            |.#.#.#....#
            |.#........#
            |#.##...#...
            |#...##....#
            |.#..#...#.#""".trimMargin())
        )
    }
}