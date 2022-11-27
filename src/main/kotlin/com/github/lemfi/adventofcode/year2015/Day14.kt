package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day14 {

    private fun String?.toData() = (this ?: data(14))
        .lines()
        .map {
            Reindeer(
                name = it.substringBefore(" can fly "),
                kms = it.substringAfter(" can fly ").substringBefore(" km/s for ").toInt(),
                time = it.substringAfter(" km/s for ").substringBefore(" seconds, but then must rest for ").toInt(),
                rest = it.substringAfter(" seconds, but then must rest for ").substringBefore(" seconds.").toInt(),
            )
        }

    fun star1(input: String?) = input
        .toData()
        .also { reindeers ->
            (1..2503).forEach { _ ->
                reindeers.onEach {
                    if (it.moves > 0) {
                        it.position += it.kms
                        it.moves--
                        if (it.moves == 0) it.resting = it.rest
                    } else if (it.resting > 0) {
                        it.resting--
                        if (it.resting == 0) it.moves = it.time
                    }
                }
            }
        }
        .maxOf { it.position }

    fun star2(input: String?) = input
        .toData()
        .also { reindeers ->
            (1..2503).forEach { _ ->
                reindeers.onEach {
                    if (it.moves > 0) {
                        it.position += it.kms
                        it.moves--
                        if (it.moves == 0) it.resting = it.rest
                    } else if (it.resting > 0) {
                        it.resting--
                        if (it.resting == 0) it.moves = it.time
                    }
                }
                    .filter { it.position == reindeers.maxOf { it.position } }
                    .onEach { it.points += 1 }
            }
        }
        .maxOf { it.points }

    data class Reindeer(val name: String, val kms: Int, val time: Int, val rest: Int, var position: Int = 0) {
        var moves = time
        var resting = 0
        var points = 0
    }
}

fun main() {
    processStars(Day14::star1, Day14::star2)
}

