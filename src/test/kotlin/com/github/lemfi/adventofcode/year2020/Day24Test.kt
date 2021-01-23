package com.github.lemfi.adventofcode.year2020

import com.github.lemfi.adventofcode.year2020.Day24.toDirections
import com.github.lemfi.adventofcode.year2020.Day24.toSmallestPath
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day24Test {

    @Test
    fun directions() {
        Assertions.assertEquals(listOf(listOf(
                Day24.Direction.NW,
                Day24.Direction.W,
                Day24.Direction.SW,
                Day24.Direction.E,
                Day24.Direction.E,
        )), listOf("nwwswee").toDirections())
    }

    @Test
    fun smallestPath() {
        Assertions.assertEquals(listOf(emptyList<Day24.Direction>()), listOf("nwwswee").toDirections().map { it.toSmallestPath() })
        Assertions.assertEquals(listOf(listOf(Day24.Direction.SE)), listOf("esew").toDirections().map { it.toSmallestPath() })
        Assertions.assertEquals(listOf(listOf(Day24.Direction.E, Day24.Direction.E, Day24.Direction.E)), listOf("esenee").toDirections().map { it.toSmallestPath() })
        Assertions.assertEquals(listOf(listOf(Day24.Direction.E, Day24.Direction.E, Day24.Direction.E)), listOf("eseene").toDirections().map { it.toSmallestPath() })
        Assertions.assertEquals(listOf(listOf(Day24.Direction.E)), listOf("nwsee").toDirections().map { it.toSmallestPath() })
    }

    @Test
    fun sample1() {

        Assertions.assertEquals(10, Day24.star1("""sesenwnenenewseeswwswswwnenewsewsw
neeenesenwnwwswnenewnwwsewnenwseswesw
seswneswswsenwwnwse
nwnwneseeswswnenewneswwnewseswneseene
swweswneswnenwsewnwneneseenw
eesenwseswswnenwswnwnwsewwnwsene
sewnenenenesenwsewnenwwwse
wenwwweseeeweswwwnwwe
wsweesenenewnwwnwsenewsenwwsesesenwne
neeswseenwwswnwswswnw
nenwswwsewswnenenewsenwsenwnesesenew
enewnwewneswsewnwswenweswnenwsenwsw
sweneswneswneneenwnewenewwneswswnese
swwesenesewenwneswnwwneseswwne
enesenwswwswneneswsenwnewswseenwsese
wnwnesenesenenwwnenwsewesewsesesew
nenewswnwewswnenesenwnesewesw
eneswnwswnwsenenwnwnwwseeswneewsenese
neswnwewnwnwseenwseesewsenwsweewe
wseweeenwnesenwwwswnew"""))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(2208, Day24.star2("""sesenwnenenewseeswwswswwnenewsewsw
neeenesenwnwwswnenewnwwsewnenwseswesw
seswneswswsenwwnwse
nwnwneseeswswnenewneswwnewseswneseene
swweswneswnenwsewnwneneseenw
eesenwseswswnenwswnwnwsewwnwsene
sewnenenenesenwsewnenwwwse
wenwwweseeeweswwwnwwe
wsweesenenewnwwnwsenewsenwwsesesenwne
neeswseenwwswnwswswnw
nenwswwsewswnenenewsenwsenwnesesenew
enewnwewneswsewnwswenweswnenwsenwsw
sweneswneswneneenwnewenewwneswswnese
swwesenesewenwneswnwwneseswwne
enesenwswwswneneswsenwnewswseenwsese
wnwnesenesenenwwnenwsewesewsesesew
nenewswnwewswnenesenwnesewesw
eneswnwswnwsenenwnwnwwseeswneewsenese
neswnwewnwnwseenwseesewsenwsweewe
wseweeenwnesenwwwswnew"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(360, Day24.star1(Day24.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(3924, Day24.star2(Day24.data))
    }

}