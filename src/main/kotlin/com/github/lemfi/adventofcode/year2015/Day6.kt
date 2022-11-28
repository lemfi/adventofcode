package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day6 {

    private fun String?.toData() = this ?: data(6)

    data class Coordinate(val x: Int, val y: Int, var value: Int = 0)

    sealed class Action {
        abstract val from: Coordinate
        abstract val to: Coordinate
    }

    data class On(override val to: Coordinate, override val from: Coordinate) : Action()
    data class Off(override val to: Coordinate, override val from: Coordinate) : Action()
    data class Toggle(override val to: Coordinate, override val from: Coordinate) : Action()

    private fun String.toCoordinates() = split(" through ").map {
        it.split(",").let { (x, y) -> Coordinate(x.toInt(), y.toInt()) }
    }

    fun String.mapInput() = lines()
        .map {
            when {
                it.startsWith("turn on") -> it.substringAfter("turn on ").toCoordinates().let { (c1, c2) ->
                    On(c2, c1)
                }

                it.startsWith("turn off") -> it.substringAfter("turn off ").toCoordinates().let { (c1, c2) ->
                    Off(c2, c1)
                }

                it.startsWith("toggle") -> it.substringAfter("toggle ").toCoordinates().let { (c1, c2) ->
                    Toggle(c2, c1)
                }

                else -> error("hmm")
            }
        }

    fun star1(input: String?) = input.toData().mapInput().let {
        val on = mutableSetOf<Coordinate>()
        it.forEach { action ->
            when (action) {
                is On -> playAction(action.from, action.to) { x, y ->
                    on.add(Coordinate(x, y))
                }

                is Off -> playAction(action.from, action.to) { x, y ->
                    on.remove(Coordinate(x, y))
                }

                is Toggle -> playAction(action.from, action.to) { x, y ->
                    if (!on.remove(Coordinate(x, y))) on.add(Coordinate(x, y))
                }

                else -> error("hmm...")
            }
        }
        on.size
    }

    private fun playAction(
        from: Coordinate,
        to: Coordinate,
        action: (x: Int, y: Int) -> Unit
    ) {
        (from.x..to.x).forEach { x ->
            (from.y..to.y).forEach { y ->
                action(x, y)
            }
        }
    }

    private infix fun Coordinate.isConcernedBy(action: Action) =
        action.from.x <= x && action.to.x >= x && action.from.y <= y && action.to.y >= y

    fun star2(input: String?) = input.toData().mapInput().let { actions ->
        (0..999).flatMap { x ->
            (0..999).map { y ->
                Coordinate(x, y).also { coordinates ->
                    actions
                        .filter { coordinates isConcernedBy it }
                        .forEach { action ->
                            when (action) {
                                is On -> coordinates.value++
                                is Toggle -> coordinates.value += 2
                                is Off -> if (coordinates.value > 0) coordinates.value--
                                else -> {}
                            }
                        }
                }
            }
        }.sumOf { it.value }
    }
}

fun main() {
    processStars(Day6::star1, Day6::star2)
}
