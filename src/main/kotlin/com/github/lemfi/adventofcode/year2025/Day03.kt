package com.github.lemfi.adventofcode.year2025

import com.github.lemfi.adventofcode.processStars
import kotlin.collections.mapIndexed

object Day03 {

    private fun String?.toData() = this ?: data(3)

    fun star1(input: String?) = input.toData().lines().sumOf {
        val jolts = it.map { "$it".toLong() }
        jolts.foldIndexed(0L to 0L) { i, (j1, j2), jolt ->
            when {
                jolt > j1 && i < jolts.size - 1 -> jolt to 0L
                jolt > j2 -> j1 to jolt
                else -> j1 to j2
            }
        }.let { (j1, j2) -> "$j1$j2".toLong() }
    }

    fun star2(input: String?) = input.toData().lines().sumOf { line ->
        line.map { "$it".toLong() }.let { jolts ->
            jolts.foldIndexed((1..12).map { 0L }) { joltIndex, battery, jolt ->
                val cond: (Int) -> Boolean = { batteryIndex -> jolt > battery[batteryIndex] && joltIndex < jolts.size - (11 - batteryIndex) }
                val apply: (Int) -> List<Long> = { batteryIndex -> battery.mapIndexed { i, v -> if (i < batteryIndex) v else if (i == batteryIndex) jolt else 0L } }
                when {
                    cond(0) -> apply(0)
                    cond(1) -> apply(1)
                    cond(2) -> apply(2)
                    cond(3) -> apply(3)
                    cond(4) -> apply(4)
                    cond(5) -> apply(5)
                    cond(6) -> apply(6)
                    cond(7) -> apply(7)
                    cond(8) -> apply(8)
                    cond(9) -> apply(9)
                    cond(10) -> apply(10)
                    cond(11) -> apply(11)
                    else -> battery
                }
            }.joinToString("").toLong()
        }
    }
}

fun main() {
    processStars(Day03::star1, Day03::star2)
}
