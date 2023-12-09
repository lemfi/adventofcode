package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day08 {

    private fun String?.toData() = this ?: data(8)

    fun star1(input: String?) = input
        .toData()
        .split("\n\n")
        .toDirectionsAndNodes()
        .let { (directions, nodes) ->
            var next = nodes
                .first { (source, _, _) -> source == "AAA" }

            var nbStep = 0L
            do {
                next =
                    nodes.first { it.source == if (directions.next(nbStep) == Direction.LEFT) next.left else next.right }
                nbStep++
            } while (next.source != "ZZZ")
            nbStep
        }


    fun star2(input: String?) = input
        .toData()
        .split("\n\n")
        .toDirectionsAndNodes()
        .let { (directions, nodes) ->

            // solution obtained after searching a pattern between iteration of getting on a Z for each start
            // it appears that the pattern repeats exactly at same interval for any start, so just have to find
            // the closest interval that will match for each one

            // find the interval for each start (first time Z is reached is so the interval for this start after observation)
            val stepsForEachNode = nodes
                .filter { it.source.endsWith("A") }
                .map {
                    var next = it

                    var nbStep = 0L
                    do {
                        next =
                            nodes.first { it.source == if (directions.next(nbStep) == Direction.LEFT) next.left else next.right }
                        nbStep++
                    } while (!next.source.endsWith("Z"))
                    nbStep
                }.sorted()

            // find the closest common time where each start ends with a Z
            var index = 0L
            val first = stepsForEachNode.first()
            val rest = stepsForEachNode.drop(1)
            do {
                index++
                val mult = first * index
            } while (rest.any { mult % it != 0L })

            index * first

        }
}

private fun List<String>.toDirectionsAndNodes() = let { (rawDirections, rawNodes) ->
    val directions = Directions(rawDirections.map { if (it == 'L') Direction.LEFT else Direction.RIGHT })

    val nodes = rawNodes.lines().map {
        it.split(" = (")
            .let { (source, destinations) ->
                destinations.removeSuffix(")").split(", ").let { (left, right) ->
                    Node(source, left, right)
                }
            }
    }
    directions to nodes
}

data class Node(val source: String, val left: String, val right: String)


data class Directions(val directions: List<Direction>) {

    val size = directions.size
    fun next(index: Long) = directions[(index % size).toInt()]
}

enum class Direction {
    LEFT, RIGHT

}

fun main() {
    processStars(Day08::star1, Day08::star2)
}
