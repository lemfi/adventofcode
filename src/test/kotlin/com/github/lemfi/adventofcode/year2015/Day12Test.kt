package com.github.lemfi.adventofcode.year2015

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun star1() {
        Assertions.assertEquals(6, Day12.star1("""[1,2,3]"""))
        Assertions.assertEquals(6, Day12.star1("""{"a":2,"b":4}"""))
        Assertions.assertEquals(3, Day12.star1("""[[[3]]]"""))
        Assertions.assertEquals(3, Day12.star1("""{"a":{"b":4},"c":-1}"""))
        Assertions.assertEquals(0, Day12.star1("""{"a":[-1,1]}"""))
        Assertions.assertEquals(0, Day12.star1("""[-1,{"a":1}]"""))
        Assertions.assertEquals(0, Day12.star1("""[]"""))
        Assertions.assertEquals(0, Day12.star1("""{}"""))
    }

    @Test
    fun star2() {
        Assertions.assertEquals(6, Day12.star2("""[1,2,3]"""))
        Assertions.assertEquals(4, Day12.star2("""[1,{"c":"red","b":2},3]"""))
        Assertions.assertEquals(0, Day12.star2("""{"d":"red","e":[1,2,3,4],"f":5}"""))
        Assertions.assertEquals(6, Day12.star2("""[1,"red",5]"""))
    }
}