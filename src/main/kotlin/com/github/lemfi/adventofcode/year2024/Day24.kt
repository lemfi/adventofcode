package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

/*

        FULL ADDER
                    _____
       A -+---------\    \              _____
          |         | xor >--+----------\    \
       B -----+-----/____/   |          | xor >-------------------------------------- SUM
          |   |              |       +--/____/
          |   |              |       |
     Cin ------------------------+---+
          |   |              |   |                  +----\
          |   |              |   +------------------+     \
          |   |              |                      | and  )----+
          |   |              +----------------------+     /     |
          |   |                                     +----/      |       _____
          |   |                                                 +-------\    \
          |   |                                                         | xor >------ Cout
          |   |                                                 +-------/____/
          |   |                                     +----\      |
          |   +-------------------------------------+     \     |
          |                                         | and  )----+
          +-----------------------------------------+     /
                                                    +----/
 */

object Day24 {

    private fun String?.toData() = this ?: data(24)

    fun star1(input: String?) = input.parse()
        .let { (starts, connections) ->
            (connections compute starts)
                .joinToString("") { "${it.second ?: 0}" }
                .toLong(2)
        }

    fun star2(input: String?) = input.parse()
        .let { (starts, connections) ->

            var fixedConnections = connections
            var inError = true

            val switchedOutputs = mutableListOf<Pair<String, String>>()

            while (inError) {
                runCatching {
                    val xs = starts.filter { it.first.startsWith("x") }.sortedByDescending { it.first }

                    val nbx = xs.size
                    var index = 0

                    var rest: String? = null
                    var A = "x00"
                    var B = "y00"

                    while (index < nbx) {

                        val add = fixedConnections
                            .first { it.matches(A, B, Logic.XOR) }

                        if (rest != null) {
                            fixedConnections
                                .firstOrNull { it.matches(rest!!, add.output, Logic.XOR) }
                                ?: run {
                                    fixedConnections =
                                        fixedConnections.fix(add.output, rest!!, Logic.XOR, switchedOutputs)
                                    error("No XOR between $rest and ${add.output}")
                                }

                            val rest1 = fixedConnections
                                .firstOrNull { it.matches(add.output, rest!!, Logic.AND) }
                                ?: run {
                                    fixedConnections =
                                        fixedConnections.fix(add.output, rest!!, Logic.AND, switchedOutputs)
                                    error("No AND between $rest and ${add.output}")
                                }

                            val rest2 = fixedConnections
                                .firstOrNull { it.matches(A, B, Logic.AND) }
                                ?: run {
                                    fixedConnections = fixedConnections.fix(A, B, Logic.AND, switchedOutputs)
                                    error("No AND between $A and $B")
                                }

                            val restConnection = fixedConnections
                                .firstOrNull { it.matches(rest1.output, rest2.output, Logic.OR) }
                                ?: run {
                                    fixedConnections =
                                        fixedConnections.fix(rest1.output, rest2.output, Logic.OR, switchedOutputs)
                                    error("No OR between ${rest1.output} and ${rest2.output}")
                                }

                            rest = restConnection.output

                        } else {
                            rest = fixedConnections.first { it.matches(A, B, Logic.AND) }.output
                        }

                        index++
                        A = "x${index.toString().padStart(2, '0')}"
                        B = "y${index.toString().padStart(2, '0')}"
                    }

                    inError = false
                }
            }
            switchedOutputs.flatMap { listOf(it.first, it.second) }.sorted().joinToString(",")
        }

    private fun List<Connection>.fix(
        wire1: String,
        wire2: String,
        logic: Logic,
        switchedOutputs: MutableList<Pair<String, String>>,
    ): List<Connection> {
        var c1 = wire1
        var c2 = wire2
        val connection =
            firstOrNull { (it.wire1 == wire1 || it.wire2 == wire1) && it.logic == logic }
                ?: first { (it.wire1 == wire2 || it.wire2 == wire2) && it.logic == logic }
                    .apply { c1 = wire2; c2 = wire1 }

        val wireToChange =
            if (connection.wire1 == c1) connection.wire2 else connection.wire1

        val connection1ToSwitch = first { it.output == wireToChange }
        val connection2ToSwitch = first { it.output == c2 }

        switchedOutputs.add(connection1ToSwitch.output to connection2ToSwitch.output)

        return map {
            when (it) {
                connection1ToSwitch -> connection1ToSwitch.copy(output = connection2ToSwitch.output)
                connection2ToSwitch -> connection2ToSwitch.copy(output = connection1ToSwitch.output)
                else -> it
            }
        }
    }

    private infix fun List<Connection>.compute(
        starts: List<Pair<String, Int>>
    ): List<Pair<String, Int?>> {
        val wires = mutableMapOf<String, Int?>()
        onEach {
            wires[it.wire1] = null
            wires[it.wire2] = null
            wires[it.output] = null
        }
        starts.onEach { wires[it.first] = it.second }

        val connections = toMutableList()
        val zs = mutableListOf<Connection>()

        while (wires.any { it.value == null }) {
            val treated = mutableSetOf<Connection>()
            connections.forEach { connection ->
                if (wires[connection.wire1] != null && wires[connection.wire2] != null) {
                    treated.add(Connection(connection.wire1, connection.wire2, connection.logic, connection.output))
                    if (connection.output.startsWith("z")) {
                        zs.add(Connection(connection.wire1, connection.wire2, connection.logic, connection.output))
                    }

                    wires[connection.output] = when (connection.logic) {
                        Logic.AND -> wires[connection.wire1]!! and wires[connection.wire2]!!
                        Logic.OR -> wires[connection.wire1]!! or wires[connection.wire2]!!
                        Logic.XOR -> wires[connection.wire1]!! xor wires[connection.wire2]!!
                    }
                }
            }
            connections.removeAll(treated)
        }
        return wires.filter { it.key.startsWith("z") }
            .toList()
            .sortedByDescending { it.first }
    }

    private fun String?.parse() = toData().split("\n\n")
        .let { (starts, connections) ->
            starts.lines().map { it.split(": ") }.map { (wire, value) -> wire to value.toInt() } to
                    connections.lines().map {
                        val wire1 = it.substringBefore(" ")
                        val logic = it.substringAfter(" ").substringBefore(" ")
                        val wire2 = it.substringBefore(" -> ").substringAfterLast(" ")
                        val output = it.substringAfter(" -> ")
                        Connection(listOf(wire1, wire2), Logic.valueOf(logic), output)
                    }
        }

    enum class Logic {
        AND, OR, XOR
    }

    data class Connection(
        val wires: List<String>,
        val logic: Logic,
        val output: String
    ) {

        constructor(wire1: String, wire2: String, logic: Logic, output: String)
                : this(listOf(wire1, wire2), logic, output)

        fun matches(wire1: String, wire2: String, logic: Logic) =
            this.logic == logic && wires.toSet() == setOf(wire1, wire2)

        val wire1: String get() = wires[0]
        val wire2: String get() = wires[1]
    }
}

fun main() {
    processStars(Day24::star1, Day24::star2)
}
