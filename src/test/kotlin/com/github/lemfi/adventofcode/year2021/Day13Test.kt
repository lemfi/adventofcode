package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.year2021.Day13.Day13
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day13Test {

    @Test
    fun star1() {
        Assertions.assertEquals(
            17, Day13.star1(
                """6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5"""
            )
        )
    }

    @Test
    fun star2() {
        Assertions.assertEquals(
            """
#####
#   #
#   #
#   #
#####""", Day13.star2(
                """6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5"""
            )
        )
    }

    @Test
    fun puzzle1() {
        Assertions.assertEquals(666, Day13.star1(null))
    }

    @Test
    fun puzzle2() {
        Assertions.assertEquals(
            """
 ##    ## #  #  ##  #### #  # #  # #  #
#  #    # #  # #  #    # #  # # #  #  #
#       # #### #  #   #  #### ##   #  #
#       # #  # ####  #   #  # # #  #  #
#  # #  # #  # #  # #    #  # # #  #  #
 ##   ##  #  # #  # #### #  # #  #  ## """, Day13.star2(null)
        )
    }
}