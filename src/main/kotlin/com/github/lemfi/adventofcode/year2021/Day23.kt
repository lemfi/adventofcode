package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.min

object Day23 {

    private fun String?.toData() = this ?: data(23)

    fun star1(input: String?): Int = input
        .toData()
        .lines()
        .prepareData()
        .getMinimalCost()

    fun star2(input: String?): Int = input
        .toData()
        .lines()
        .let {
            it.take(3) + listOf("  #D#C#B#A#", "  #D#B#A#C#") + it.drop(3)
        }
        .prepareData()
        .getMinimalCost()

    private fun List<String>.prepareData() = drop(1)
        .dropLast(1)
        .let { lines ->
            val roomsXPosition = (lines[2].indexOf('#') + 1 until lines[2].lastIndexOf('#') step 2)
            val hallway = (1 until lines.first().length - 1).map { Hallway(it, 0, !roomsXPosition.contains(it)) }
            val roomsSlots =
                (1..lines.drop(1).size).flatMap { y ->
                    roomsXPosition.map { x ->
                        Room(x, y)
                    }
                }

            val rooms = roomsXPosition.mapIndexed { index, x ->
                when (index) {
                    0 -> RoomType.A to roomsSlots.filter { it.x == x }
                    1 -> RoomType.B to roomsSlots.filter { it.x == x }
                    2 -> RoomType.C to roomsSlots.filter { it.x == x }
                    3 -> RoomType.D to roomsSlots.filter { it.x == x }
                    else -> error("hmm ...")
                }
            }.toMap()

            val amphipods = lines.flatMapIndexed { y, line ->
                line.toList().mapIndexedNotNull { x, c ->
                    when (c) {
                        ' ', '#', '.' -> null
                        'A' -> AmphipodA(x, y)
                        'B' -> AmphipodB(x, y)
                        'C' -> AmphipodC(x, y)
                        'D' -> AmphipodD(x, y)
                        else -> error("hmm...")
                    }
                }
            }

            Cave(
                (hallway + roomsSlots).associateBy { (it.x to it.y) },
                amphipods,
                rooms,
                lines.size
            )
        }

    private fun Cave.getMinimalCost() = let { initialCave ->

        val caves = mutableListOf(initialCave)
        var cost: Int = Int.MAX_VALUE
        while (caves.isNotEmpty()) {
            val cave = caves.removeLast()
            if (cave.ok()) {
                cost = min(cost, cave.cost())
            } else if (cave.cost() < cost) {
                with(cave) {
                    moveableAmphipods()
                        .forEach { (amphipod, destinations) ->
                            val (x, y) = amphipod.x to amphipod.y
                            amphipod.moves += 1
                            destinations
                                .forEach { (destination, steps) ->
                                    amphipod.x = destination.x
                                    amphipod.y = destination.y
                                    amphipod.steps += steps
                                    caves.add(
                                        cave.copy(amphipods = cave.amphipods.map { it.clone() })
                                    )
                                    amphipod.steps -= steps
                                }
                            amphipod.moves -= 1
                            amphipod.x = x
                            amphipod.y = y
                        }

                }
            }
        }

        cost
    }

    data class Cave(
        val slots: Map<Pair<Int, Int>, Space>,
        val amphipods: List<Amphipod>,
        val rooms: Map<RoomType, List<Room>>,
        val maxY: Int,
    ) {
        private val amphipodsMap: Map<Pair<Int, Int>, Amphipod> by lazy { amphipods.associateBy { (it.x to it.y) } }

        private val okSlots: Map<RoomType, Map<Pair<Int, Int>, Boolean>> by lazy {
            rooms.map { (type, room) ->
                type to room.associate { r ->
                    (r.x to r.y) to (r.y until maxY).all { y -> amphipodsMap[r.x to y]?.targetRoom == type }
                }
            }.toMap()
        }

        private val availableSlots: Map<RoomType, Map<Pair<Int, Int>, Boolean>> by lazy {
            rooms.map { (type, room) ->
                type to room.associate { r ->
                    (r.x to r.y) to (
                            (amphipodsMap[r.x to r.y] == null)
                                    && ((r.y + 1)..maxY).all { y ->
                                amphipodsMap[r.x to y]?.let { it.targetRoom == type } ?: (y >= maxY)
                            })
                }
            }.toMap()
        }

        fun ok() = amphipods.all { it.isOk() }

        fun cost() = amphipods.sumOf { it.steps * it.cost }

        fun moveableAmphipods() = amphipods
            .filterNot { it.isOk() }
            .mapNotNull { amphipod ->
                amphipod
                    .availableDestinations()
                    .ifEmpty { null }
                    ?.let { destinations -> amphipod to destinations }

            }
            .sortedBy { (it, _) ->
                if (slots[it.x to it.y]!! is Hallway) -10000
                else -it.cost
            }

        private fun Space.isMovable() = amphipodsMap[x to y] == null

        private fun Space.neighbors(
            distance: Int = 1,
            exclude: Set<Space> = emptySet(),
            rule: (Space) -> Boolean,
        ): Collection<Pair<Space, Int>> =

            setOfNotNull(
                slots[x + 1 to y],
                slots[x - 1 to y],
                slots[x to y + 1],
                slots[x to y - 1],
            )
                .minus(exclude)
                .filter { it.isMovable() }
                .let { neighbors ->
                    neighbors
                        .filter { rule(it) }
                        .map { it to distance }
                        .union(
                            neighbors.flatMap {
                                it.neighbors(distance + 1, (neighbors + this).toSet(), rule)
                            }
                        )
                        .toSet()
                }

        private fun Amphipod.isOk() = okSlots[targetRoom]!![x to y] ?: false

        private fun Amphipod.availableDestinations() = slots[x to y]!!
            .neighbors { slot ->
                when (slot) {
                    is Room -> availableSlots[targetRoom]!![slot.x to slot.y] ?: false
                    is Hallway -> moves == 0 && slot.stoppable
                }
            }
            .partition { (slot, _) -> slot is Room }
            .let { (rooms, hallways) ->
                rooms.ifEmpty { hallways }
            }
    }

    sealed class Position {
        abstract val x: Int
        abstract val y: Int
    }

    enum class RoomType { A, B, C, D }

    sealed class Space : Position()

    data class Room(
        override val x: Int,
        override val y: Int,
    ) : Space()

    data class Hallway(
        override val x: Int,
        override val y: Int,
        val stoppable: Boolean,
    ) : Space()

    sealed class Amphipod : Position() {
        abstract val targetRoom: RoomType
        abstract val cost: Int
        abstract override var x: Int
        abstract override var y: Int
        abstract var moves: Int
        abstract var steps: Int
        abstract fun clone(): Amphipod
    }

    data class AmphipodA(
        override var x: Int,
        override var y: Int,
        override var moves: Int = 0,
        override var steps: Int = 0,
    ) : Amphipod() {
        override val targetRoom = RoomType.A
        override val cost = 1
        override fun clone() = copy()
    }

    data class AmphipodB(
        override var x: Int,
        override var y: Int,
        override var moves: Int = 0,
        override var steps: Int = 0,
    ) : Amphipod() {
        override val targetRoom = RoomType.B
        override val cost = 10
        override fun clone() = copy()
    }

    data class AmphipodC(
        override var x: Int,
        override var y: Int,
        override var moves: Int = 0,
        override var steps: Int = 0,
    ) : Amphipod() {
        override val targetRoom = RoomType.C
        override val cost = 100
        override fun clone() = copy()
    }

    data class AmphipodD(
        override var x: Int,
        override var y: Int,
        override var moves: Int = 0,
        override var steps: Int = 0,
    ) : Amphipod() {
        override val targetRoom = RoomType.D
        override val cost = 1000
        override fun clone() = copy()
    }
}

fun main() {
    processStars(Day23::star1, Day23::star2)
}
