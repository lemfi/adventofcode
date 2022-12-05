package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day5 {

    private fun String?.toData() = this ?: data(5)

    fun star1(input: String?) = input
        .moveCrates { (amount, origin, destination), stacks ->
            repeat(amount) {
                stacks[origin]
                    .removeFirst()
                    .also { crate -> stacks[destination].add(0, crate) }
            }
        }


    fun star2(input: String?) = input
        .moveCrates { (amount, origin, destination), stacks ->
            (amount - 1 downTo 0).forEach { y ->
                stacks[origin]
                    .removeAt(y)
                    .also { crate -> stacks[destination].add(0, crate) }
            }
        }

    private fun String?.moveCrates(mover: (Triple<Int, Int, Int>, List<MutableList<Char>>) -> Unit) =
        prepareData()
            .let { (stacks, moves) ->
                moves.forEach { move -> mover(move, stacks) }
                stacks.tops()
            }

    private fun String?.prepareData() =
        toData()
            .split("\n\n")
            .let { (crates, moves) ->
                computeStacks(crates) to computeMoves(moves)
            }

    private fun computeStacks(stacks: String) =
        stacks
            .lines()
            .dropLast(1)
            .let { ords ->

                (1..(ords.first().length + 1) / 4)
                    .map { mutableListOf<Char>() } // create empty stacks

                    .also { stacks ->
                        ords.forEach { abs ->
                            abs.forEachIndexed { x, crate ->
                                if (crate != ' ' && (x - 1) % 4 == 0) { // crates are at positions 1, 5, 9, ...
                                    stacks[(x - 1) / 4].add(crate) // fill stack if needed
                                }
                            }
                        }
                    }
            }

    private fun computeMoves(moves: String) = moves.lines()
        .map {
            it
                .split(" ")
                .mapNotNull { runCatching { it.toInt() }.getOrNull() }
                .let { (amount, origin, destination) ->
                    Triple(amount, origin - 1, destination - 1)
                }
        }

    private fun List<List<Char>>.tops() =
        joinToString("") { it.first().toString() }

}

fun main() {
    processStars(Day5::star1, Day5::star2)
}