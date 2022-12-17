package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import kotlin.math.max

object Day16 {

    private fun String?.toData() = this ?: data(16)

    fun star1(input: String?) = input
        .parse()
        .let {
            with(Cave(it)) {
                start().visit()
            }

        }

    fun star2(input: String?) = input
        .parse()
        .let {
            with(Cave2(it)) {
                (start() to start()).visit()
            }

        }

    private fun String?.parse() = toData()
        .replace("tunnel leads to valve", "tunnels lead to valves")
        .lines()
        .map {
            Valve(
                name = it.substringAfter("Valve ").substringBefore(" has flow rate="),
                flowRate = it.substringAfter(" has flow rate=").substringBefore("; tunnels lead to valves ").toInt(),
                leadsTo = it.substringAfter("; tunnels lead to valves ").split(", ")
            )
        }

    data class Cave(val valves: List<Valve>, var time: Int = 30) {

        fun start() = valves.first { it.name == "AA" }

        fun Valve.visit(origin: String? = null, bestKnownRes: Int = Int.MIN_VALUE): Int {

            return if (time > 0 && bestPossibleRes() > bestKnownRes) {

                val nextPossibleValveDestination = next(origin)

                if (nextPossibleValveDestination.isEmpty()) {
                    time = 0
                    released()
                } else nextPossibleValveDestination.fold(bestKnownRes) { released, (action, valveName) ->
                    max(
                        released, this@Cave.copy(valves = valves.map { it.copy() })
                            .run {
                                time--
                                if (action == "open") {
                                    valves.first { it.name == valveName }.let {
                                        it.opened = time
                                        it.visit(it.name, released)
                                    }
                                } else {
                                    valves.first { it.name == valveName }
                                        .visit(this@visit.name, released)
                                }
                            }
                    )
                }
            } else {
                time = 0
                this@Cave.released()
            }
        }

        private fun bestPossibleRes() =
            valves
                .filterNot { it.opened != 0 }
                .sortedByDescending { it.flowRate }
                .mapIndexed { index, valve ->
                    max(0, valve.flowRate * (time - 2 * index))
                }.sum() + released()

        private fun released() = valves.sumOf { it.opened * it.flowRate }

        private fun Valve.next(origin: String?) =
            if (time == 0) emptyList()
            else valves
                .filter { it.name != origin && it.name in leadsTo && it.availableNextSteps(emptyList(), time - 1) }
                .map { "move" to it.name }
                .toMutableList()
                .apply {
                    if (opened == 0) {
                        add("open" to name)
                    }
                }

        private fun Valve.availableNextSteps(visited: List<String>, time: Int): Boolean =
            time != 0 && (
                    opened == 0 || valves
                        .filter { it.name in leadsTo && it.name !in visited }
                        .any { it.availableNextSteps(visited + listOf(it.name), time - 1) }
                    )
    }

    data class Cave2(val valves: List<Valve>, var time: Int = 26) {

        fun start() = valves.first { it.name == "AA" }

        fun Pair<Valve, Valve>.visit(
            origin: Pair<String?, String?> = null to null,
            bestKnownRes: Int = Int.MIN_VALUE
        ): Int {

            val (v1, v2) = this

            return if (time > 0 && bestPossibleRes() > bestKnownRes) {

                val nextPossibleValveDestination1 = v1.next(origin.first, time)
                val nextPossibleValveDestination2 = v2.next(origin.second, time).reversed()

                val nextPossibleValveDestination = mutableListOf<Pair<Pair<String, String>, Pair<String, String>>>()

                if (nextPossibleValveDestination1.size == 1 && nextPossibleValveDestination2.size == 1) {
                    nextPossibleValveDestination.add(nextPossibleValveDestination1.first() to nextPossibleValveDestination2.first())
                } else if (nextPossibleValveDestination1.isEmpty()) {
                    nextPossibleValveDestination2.forEach { p2 ->
                        nextPossibleValveDestination.add(("move" to v1.name) to p2)
                    }
                } else {
                    nextPossibleValveDestination1.forEach { p1 ->
                        nextPossibleValveDestination2.forEach { p2 ->
                            if (!(p1.first == "open" && p2.first == "open" && p1.second == p2.second)) {
                                if (nextPossibleValveDestination.none { it.first == p1 && it.second == p2 || it.first == p2 && it.second == p1 })
                                    nextPossibleValveDestination.add(p1 to p2)
                            }
                        }
                    }
                }

                if (nextPossibleValveDestination.isEmpty()) {
                    time = 0
                    if (released() == 2729) {
                        println("toto")
                    }
                    released()
                } else nextPossibleValveDestination.fold(bestKnownRes) { released, (p1, p2) ->
                    max(
                        released,
                        this@Cave2.copy(valves = valves.map { it.copy() }).run {
                            val (action1, valveName1) = p1
                            val (action2, valveName2) = p2

                            time--
                            if (action1 == "open") {
                                valveName1.valve().opened = time
                            }
                            if (action2 == "open") {
                                valveName2.valve().opened = time
                            }
                            (valveName1.valve() to valveName2.valve()).visit(
                                (if (action1 == "open") valveName1 else v1.name) to if (action2 == "open") valveName2 else v2.name,
                                released
                            )

                        })
                }
            } else {
                time = 0
                if (released() == 2729) {
                    println("toto")
                }
                this@Cave2.released()
            }
        }

        private fun String.valve() = valves.first { it.name == this }

        private fun bestPossibleRes() =
            valves
                .asSequence()
                .filter { it.opened == 0 }
                .sortedByDescending { it.flowRate }
                .windowed(2, 2, true)
                //                .take(time / 2)
                .mapIndexed { index, valves ->
                    valves.sumOf {
                        max(0, it.flowRate * (time - index * 2))
                    }
                }
                .sum() + released()

        private fun released() = valves.sumOf { it.opened * it.flowRate }

        private fun Valve.next(origin: String?, time: Int) =
            if (time == 0) emptyList()
            else valves
                .filter { it.name != origin && it.name in leadsTo && it.availableNextSteps(emptyList(), time - 1) }
                .map { "move" to it.name }
                .toMutableList()
                .apply {
                    if (opened == 0) {
                        add(0, "open" to name)
                    }
                }

        private fun Valve.availableNextSteps(visited: List<String>, time: Int): Boolean =
            time != 0 && (
                    opened == 0 || valves
                        .filter { it.name in leadsTo && it.name !in visited }
                        .any { it.availableNextSteps(visited + listOf(it.name), time - 1) }
                    )
    }

    data class Valve(val name: String, val flowRate: Int, val leadsTo: List<String>, var opened: Int = 0) {
        init {
            if (flowRate == 0) opened = Int.MAX_VALUE
        }
    }
}

fun main() {
    processStars(Day16::star1, Day16::star2)
}