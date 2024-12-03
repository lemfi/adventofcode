package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day03 {

    private fun String?.toData() = this ?: data(3)

    fun star1(input: String?) = input.toData()
        .toMul()
        .sumOf { (o1, o2) -> o1 * o2 }


    fun star2(input: String?) = input.toData()
        .clean()
        .toMul()
        .sumOf { (o1, o2) -> o1 * o2 }

    private fun String.toMul(): List<Pair<Int, Int>> = generateSequence(this) { remaining ->
        if (remaining.indexOfMul() < 0) null
        else remaining.drop(remaining.indexOfMul())
    }
        .mapNotNull { line ->
            runCatching {
                val ip1 = line.indexOf(",")
                val ip2 = line.indexOf(")")

                val a = line.take(ip1).toInt()
                val b = line.substring(ip1 + 1, ip2).toInt()

                a to b
            }.getOrNull()
        }
        .toList()

    private fun String.clean(): String =
        generateSequence(this) { remaining ->
            if (remaining.indexOfStartIgnore() < 0) null
            else {
                remaining
                    .take(remaining.indexOfStartIgnore()) +
                        remaining
                            .drop(remaining.indexOfStartIgnore())
                            .run { drop(indexOfStopIgnore()) }
            }
        }.last()

    private fun String.indexOfMul() = if (contains("mul(")) indexOf("mul(") + 4 else -1
    private fun String.indexOfStartIgnore() = if (contains("don't()")) indexOf("don't()") else -1
    private fun String.indexOfStopIgnore() = if (contains("do()")) indexOf("do()") + 4 else Int.MAX_VALUE
}

fun main() {
    processStars(Day03::star1, Day03::star2)
}
