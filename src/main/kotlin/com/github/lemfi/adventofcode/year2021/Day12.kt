package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day12 {

    private fun String?.toData() = this ?: data(12)

    private fun String?.prepareData() = toData()
        .lines()
        .map {
            it.split("-").run { first() to last() }
        }

    fun star1(input: String?) = input
        .prepareData()
        .visit("start") { cave ->
            !containsKey(cave)
        }

    fun star2(input: String?) = input
        .prepareData()
        .visit("start") { cave ->
            !containsKey(cave) || none { (_, nbVisit) -> nbVisit == 2 }
        }

    private fun List<Pair<String, String>>.visit(
        cave: String,
        visitedSmallCaves: MutableMap<String, Int> = mutableMapOf(),
        caveVisitable: MutableMap<String, Int>.(String) -> Boolean
    ): Int {

        return if (cave.isEndCave()) 1
        else {
            if (cave.isSmallCave()) visitedSmallCaves[cave] = visitedSmallCaves.getOrDefault(cave, 0) + 1

            val nextCaves = explore(cave, visitedSmallCaves, caveVisitable)
            if (nextCaves.isEmpty()) 0
            else {
                nextCaves.sumOf { nextCave ->
                    visit(nextCave, visitedSmallCaves.toMutableMap(), caveVisitable)
                }
            }
        }
    }

    private fun List<Pair<String, String>>.explore(
        cave: String,
        visitedCaves: MutableMap<String, Int>,
        smallCaveVisitable: MutableMap<String, Int>.(String) -> Boolean
    ) =
        mapNotNull {
            if (it.first == cave && visitedCaves.isCaveVisitable(it.second, smallCaveVisitable)) it.second
            else if (it.second == cave && visitedCaves.isCaveVisitable(it.first, smallCaveVisitable)) it.first
            else null
        }.toSet()

    private fun MutableMap<String, Int>.isCaveVisitable(
        caveName: String,
        smallCaveVisitable: MutableMap<String, Int>.(String) -> Boolean
    ) =
        when {
            caveName.isStartCave() -> false
            caveName.isEndCave() -> true
            caveName.isBigCave() -> true
            else -> smallCaveVisitable(caveName)

        }

    private fun String.isBigCave() = this == uppercase()
    private fun String.isStartCave() = this == "start"
    private fun String.isEndCave() = this == "end"
    private fun String.isSmallCave() = !isStartCave() && !isEndCave() && !isBigCave()

}

fun main() {
    processStars(Day12::star1, Day12::star2)
}
