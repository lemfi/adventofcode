package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day10Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(22*10, Day10.star1("""28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3"""))
    }

    @Test
    fun sample21() {

        Assertions.assertEquals(8, Day10.star2("""16
10
15
5
1
11
7
19
6
12
4"""))
    }

    @Test
    fun sample22() {

        Assertions.assertEquals(19208, Day10.star2("""28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3"""))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(2030, Day10.star1(Day10.data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(42313823813632, Day10.star2(Day10.data))
    }

}