package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day9 {

    private fun String?.toData() = this ?: data(9)

    data class Element(val x: Int, val y: Int, val height: Int)

    private fun String?.prepareData() = toData().lines().let { lines ->
        (lines.indices).flatMap { y ->
            (lines.first().indices).map { x ->
                Element(x, y, lines[y].toList()[x].toString().toInt())
            }
        }
    }

    fun star1(input: String?) = input
        .prepareData()
        .findLowPoints()
        .sumOf { it.height + 1 }


    fun star2(input: String?) = input.prepareData()
        .run {
            findLowPoints()
                .map { `basin size of`(it) }
                .sorted()
                .takeLast(3)
                .reduce { acc, size -> acc * size }
        }

    private infix fun List<Element>.of(element: Element) = filter {
        (it.x == element.x + 1 && it.y == element.y)
                || (it.x == element.x - 1 && it.y == element.y)
                || (it.x == element.x && it.y == element.y + 1)
                || (it.x == element.x && it.y == element.y - 1)
    }

    private infix fun List<Element>.`have a greater height than`(element: Element) = all { it.height > element.height }

    private fun List<Element>.findLowPoints() = let { neighbors ->
        fold(mutableListOf<Element>()) { acc, element ->
            acc.apply {

                if (neighbors of element `have a greater height than` element) add(element)

            }
        }
    }

    private fun List<Element>.`basin size of`(element: Element): Int = let { neighbors ->
        mutableSetOf<Element>().apply {
            mutableListOf(element).also { checkNeighbors ->
                while (checkNeighbors.isNotEmpty()) {
                    (neighbors of checkNeighbors.removeFirst())
                        .filter { it.height != 9 && add(it) }
                        .apply { checkNeighbors.addAll(this) }
                }
            }
        }.size
    }
}

processStars(Day9::star1, Day9::star2)