package com.github.lemfi.adventofcode.year2020

import com.github.lemfi.adventofcode.year2020.Day20.rotations
import com.github.lemfi.adventofcode.year2020.Day20.sideDown
import com.github.lemfi.adventofcode.year2020.Day20.sideLeft
import com.github.lemfi.adventofcode.year2020.Day20.sideRight
import com.github.lemfi.adventofcode.year2020.Day20.sideUp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day20Test {

    val sample = """Tile 2311:
..##.#..#.
##..#.....
#...##..#.
####.#...#
##.##.###.
##...#.###
.#.#.#..##
..#....#..
###...#.#.
..###..###

Tile 1951:
#.##...##.
#.####...#
.....#..##
#...######
.##.#....#
.###.#####
###.##.##.
.###....#.
..#.#..#.#
#...##.#..

Tile 1171:
####...##.
#..##.#..#
##.#..#.#.
.###.####.
..###.####
.##....##.
.#...####.
#.##.####.
####..#...
.....##...

Tile 1427:
###.##.#..
.#..#.##..
.#.##.#..#
#.#.#.##.#
....#...##
...##..##.
...#.#####
.#.####.#.
..#..###.#
..##.#..#.

Tile 1489:
##.#.#....
..##...#..
.##..##...
..#...#...
#####...#.
#..#.#.#.#
...#.#.#..
##.#...##.
..##.##.##
###.##.#..

Tile 2473:
#....####.
#..#.##...
#.##..#...
######.#.#
.#...#.#.#
.#########
.###.#..#.
########.#
##...##.#.
..###.#.#.

Tile 2971:
..#.#....#
#...###...
#.#.###...
##.##..#..
.#####..##
.#..####.#
#..#.#..#.
..####.###
..#.#.###.
...#.#.#.#

Tile 2729:
...#.#.#.#
####.#....
..#.#.....
....#..#.#
.##..##.#.
.#.####...
####.#.#..
##.####...
##..#.##..
#.##...##.

Tile 3079:
#.#.#####.
.#..######
..#.......
######....
####.#..#.
.#...#.##.
#.#####.##
..#.###...
..#.......
..#.###..."""

    @Test
    fun testRotations() {
        Assertions.assertEquals(listOf("""789
456
123""","""369
258
147""","""741
852
963""","""321
654
987""","""123
456
789""","""963
852
741""","""987
654
321""","""147
258
369"""), """123
    |456
    |789
""".trimMargin().rotations())
    }

    @Test
    fun sideup() {
        Assertions.assertEquals("123", """123
            |456
            |789""".trimMargin().sideUp())
    }

    @Test
    fun sidedown() {
        Assertions.assertEquals("789", """123
            |456
            |789""".trimMargin().sideDown())
    }

    @Test
    fun sidedright() {
        Assertions.assertEquals("369", """123
            |456
            |789""".trimMargin().sideRight())
    }

    @Test
    fun sidedleft() {
        Assertions.assertEquals("147", """123
            |456
            |789""".trimMargin().sideLeft())
    }

    @Test
    fun sample1() {

        Assertions.assertEquals(20899048083289L, Day20.star1(sample))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(273, Day20.star2(sample))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(23386616781851, Day20.star1(Day20.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(2376, Day20.star2(Day20.data))
    }

}