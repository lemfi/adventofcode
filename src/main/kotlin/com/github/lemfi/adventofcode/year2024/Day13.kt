package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day13 {

    private fun String?.toData() = this ?: data(13)

    fun star1(input: String?) = input
        .toMachines()
        .solve()


    fun star2(input: String?) = input
        .toMachines(10000000000000L)
        .solve()

    private fun List<Machine>.solve() = map { machine -> machine.solve().let { (a, b) -> Triple(machine, a, b) } }
        .filter { (machine, a, b) -> Solver(a, b).isValid(machine) }
        .sumOf { (_, a, b) ->
            3 * a + b
        }

    private fun String?.toMachines(addition: Long = 0L) = toData().split("\n\n")
        .map { it.lines() }
        .map { (ba, bb, prize) ->
            Machine(
                Button(
                    x = ba.substringAfter("Button A: X+").substringBefore(",").toInt(),
                    y = ba.substringAfter(", Y+").toInt(),
                ),
                Button(
                    x = bb.substringAfter("Button B: X+").substringBefore(",").toInt(),
                    y = bb.substringAfter(", Y+").toInt(),
                ),
                Coordinate(
                    x = prize.substringAfter("Prize: X=").substringBefore(",").toLong() + addition,
                    y = prize.substringAfter(", Y=").toLong() + addition,
                )
            )
        }

    private fun Machine.solve(): Pair<Long, Long> = run {

        val b = (prize.x * a.y - prize.y * a.x) / (b.x * a.y - b.y * a.x)
        val a = (prize.x - b * this.b.x) / a.x

        a to b
    }

    data class Solver(
        val pressedA: Long,
        val pressedB: Long,
        val multiplier: Long = 1,
    ) {
        fun isValid(machine: Machine): Boolean =
            isValidX(machine) and isValidY(machine)

        private fun isValidX(machine: Machine): Boolean =
            (machine.prize.x == x(machine))

        private fun isValidY(machine: Machine): Boolean =
            (machine.prize.y == y(machine))

        fun x(machine: Machine) = multiplier * (pressedA * machine.a.x + pressedB * machine.b.x)
        fun y(machine: Machine) = multiplier * (pressedA * machine.a.y + pressedB * machine.b.y)

    }

    data class Machine(
        val a: Button,
        val b: Button,
        val prize: Coordinate
    )

    data class Button(
        val x: Int,
        val y: Int
    )

    data class Coordinate(
        val x: Long,
        val y: Long,
    )
}

fun main() {
    processStars(Day13::star1, Day13::star2)
}
