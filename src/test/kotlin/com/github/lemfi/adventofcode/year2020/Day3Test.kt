package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day3Test {

    @Test
    fun sample1() {
        Assertions.assertEquals(7, Day3.star1("""..##.......
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
        Assertions.assertEquals(336, Day3.star2("""..##.......
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
    fun real1() {
        Assertions.assertEquals(193, Day3.star1(Day3.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(1355323200, Day3.star2(Day3.data))
    }
}