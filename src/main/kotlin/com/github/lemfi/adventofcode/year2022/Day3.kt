package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day3 {

    private fun String?.toData() = this ?: data(3)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .map { it.toList() }
        .flatMap { rucksack ->
            rucksack
                .windowed(rucksack.size / 2, rucksack.size / 2)
                .map { it.toSet() }
                .let { (compartment1, compartment2) ->
                    compartment1 intersect compartment2
                }
        }.sumOf { dictionary[it.toString()]!! }

    fun star2(input: String?) = input
        .toData()
        .lines()
        .windowed(3, 3)
        .map { it.toList() }
        .flatMap { (e1, e2, e3) ->
            e1.toSet() intersect e2.toSet() intersect e3.toSet()
        }.sumOf { dictionary[it.toString()]!! }


    val alphabet = listOf(
        "a",
        "b",
        "c",
        "d",
        "e",
        "f",
        "g",
        "h",
        "i",
        "j",
        "k",
        "l",
        "m",
        "n",
        "o",
        "p",
        "q",
        "r",
        "s",
        "t",
        "u",
        "v",
        "w",
        "x",
        "y",
        "z"
    )

    private val dictionary =
        (alphabet + alphabet.map { it.uppercase() })
            .mapIndexed { index, s -> s to (index + 1) }
            .toMap()

}

fun main() {
    processStars(Day3::star1, Day3::star2)
}