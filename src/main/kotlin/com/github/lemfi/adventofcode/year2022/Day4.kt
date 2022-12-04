package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day4 {

    private fun String?.toData() = this ?: data(4)

    fun star1(input: String?) = input
        .search { section1, section2 ->
            section1.all { section2.contains(it) } || section2.all { section1.contains(it) }
        }

    fun star2(input: String?) = input
        .search { section1, section2 ->
            section1.any { section2.contains(it) } || section2.any { section1.contains(it) }
        }

    private fun String?.search(predicate: (IntRange, IntRange) -> Boolean) = toData()
        .lines()
        .count { line ->
            line
                .split(",")
                .map { assignment ->
                    assignment
                        .split("-")
                        .let { (start, end) ->
                            (start.toInt()..end.toInt())
                        }
                }
                .let { (section1, section2) ->
                    predicate(section1, section2)
                }
        }
}

fun main() {
    processStars(Day4::star1, Day4::star2)
}