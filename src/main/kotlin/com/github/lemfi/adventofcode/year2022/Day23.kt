package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day23 {

    private fun String?.toData() = this ?: data(23)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == '#') Elve(Position(x, y)) else null
            }
        }.let { elves ->
            val initialDirections = listOf(North, South, West, East)

            (0 until 10).forEach { round ->

                val directions =
                    initialDirections.drop(round % initialDirections.size) + initialDirections.take(round % initialDirections.size)

                val propositions = mutableSetOf<Position>()
                val shouldMove: Elve.() -> Boolean = {
                    position.around().any { around -> elves.any { it.position == around } }
                }
                val isAvailable: List<Position>.() -> Boolean = {
                    none { around -> elves.any { it.position == around } }
                }
                elves.forEach { elve ->
                    if (elve.shouldMove()) {
                        val proposal = elve.makeProposal(directions, isAvailable)
                        if (propositions.add(proposal)) {
                            elve.proposal = proposal
                        } else {
                            elve.proposal = elve.position
                            elves.first { it.proposal == proposal }.also { it.proposal = it.position }
                        }
                    } else {
                        elve.proposal = elve.position
                    }
                }
                elves.forEach { elve ->
                    elve.position = elve.proposal
                }

            }


            val minX = elves.minOf { it.position.x }
            val maxX = elves.maxOf { it.position.x }
            val minY = elves.minOf { it.position.y }
            val maxY = elves.maxOf { it.position.y }

            (maxX + 1 - minX) * (maxY + 1 - minY) - elves.size
        }

    private fun Elve.makeProposal(
        directions: List<Move>,
        isAvailable: List<Position>.() -> Boolean
    ): Position =
        directions.fold(position) { proposal, direction ->
            if (proposal == position) {
                when (direction) {
                    North -> {
                        if (listOf(
                                proposal.north(),
                                proposal.north().west(),
                                proposal.north().east()
                            ).isAvailable()
                        ) proposal.north()
                        else proposal
                    }

                    South -> {
                        if (listOf(
                                proposal.south(),
                                proposal.south().west(),
                                proposal.south().east()
                            ).isAvailable()
                        ) proposal.south()
                        else proposal
                    }

                    West -> {
                        if (listOf(
                                proposal.west(),
                                proposal.north().west(),
                                proposal.south().west()
                            ).isAvailable()
                        ) proposal.west()
                        else proposal
                    }

                    East -> {
                        if (listOf(
                                proposal.east(),
                                proposal.north().east(),
                                proposal.south().east()
                            ).isAvailable()
                        ) proposal.east()
                        else proposal
                    }
                }
            } else proposal
        }

    fun star2(input: String?) = input
        .toData()
        .lines()
        .flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == '#') Elve(Position(x, y)) else null
            }
        }.let { elves ->
            val initialDirections = listOf(North, South, West, East)

            var round = 0

            do {
                
                val directions =
                    initialDirections.drop(round % initialDirections.size) + initialDirections.take(round % initialDirections.size)

                val propositions = mutableSetOf<Position>()
                val shouldMove: Elve.() -> Boolean = {
                    position.around().any { around -> elves.any { it.position == around } }
                }
                val isAvailable: List<Position>.() -> Boolean = {
                    none { around -> elves.any { it.position == around } }
                }
                elves.forEach { elve ->
                    if (elve.shouldMove()) {
                        val proposal = elve.makeProposal(directions, isAvailable)
                        if (propositions.add(proposal)) {
                            elve.proposal = proposal
                        } else {
                            elve.proposal = elve.position
                            elves.first { it.proposal == proposal }.also { it.proposal = it.position }
                        }
                    } else {
                        elve.proposal = elve.position
                    }
                }
                val movingElves = elves.any { it.position != it.proposal }
                elves.forEach { elve ->
                    elve.position = elve.proposal
                }

                round++
            } while (movingElves)

            round
        }

    private data class Elve(var position: Position, var proposal: Position = position)

    sealed interface Move
    object North : Move {
        override fun toString() = "N"
    }

    object South : Move {
        override fun toString() = "S"
    }

    object West : Move {
        override fun toString() = "W"
    }

    object East : Move {
        override fun toString() = "E"
    }

    private data class Position(val x: Int, val y: Int) {

        fun north() = Position(x, y - 1)
        fun south() = Position(x, y + 1)
        fun east() = Position(x + 1, y)
        fun west() = Position(x - 1, y)

        fun around() = listOf(
            north(), north().east(), east(), south().east(), south(), south().west(), west(), north().west()
        )
    }
}

fun main() {
    processStars(Day23::star1, Day23::star2)
}