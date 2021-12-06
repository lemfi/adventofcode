package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day6 {

    private fun String?.toData() = this ?: data(6)

    private fun String?.prepareData() = toData().split(",").map { it.toInt() }

    fun star1(input: String?) =
        input
            .prepareData()
            .letNatureDoItsMagic(80)

    fun star2(input: String?) =
        input
            .prepareData()
            .letNatureDoItsMagic(256)

    private fun List<Int>.letNatureDoItsMagic(magicTime: Int) =
        mutableMapOf(
            0 to count { it == 0 }.toLong(),
            1 to count { it == 1 }.toLong(),
            2 to count { it == 2 }.toLong(),
            3 to count { it == 3 }.toLong(),
            4 to count { it == 4 }.toLong(),
            5 to count { it == 5 }.toLong(),
            6 to count { it == 6 }.toLong(),
            7 to count { it == 7 }.toLong(),
            8 to count { it == 8 }.toLong(),
        ).apply {

            (1..magicTime).forEach { _ ->

                this[6].let { six ->

                    this[6] = this[7]!! + this[0]!!
                    this[7] = this[8]!!
                    this[8] = this[0]!!
                    this[0] = this[1]!!
                    this[1] = this[2]!!
                    this[2] = this[3]!!
                    this[3] = this[4]!!
                    this[4] = this[5]!!
                    this[5] = six!!
                }

            }
        }.map { it.value }.sum()

}

processStars(Day6::star1, Day6::star2)