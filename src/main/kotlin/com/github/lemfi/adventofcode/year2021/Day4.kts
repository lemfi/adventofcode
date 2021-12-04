package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day4 {

    private fun String?.toData() = this ?: data(4)

    private fun String?.prepareData() = toData().split("\n\n").let {
        Game(
            it.first().split(",").map { it.toInt() },
            it.drop(1).map { Grid(it) }.toMutableList()
        )
    }

    fun star1(input: String?) = input.prepareData().play { first().winningValue!! }

    fun star2(input: String?) = input.prepareData().play { last().winningValue!! }


    data class Game(val inputs: List<Int>, val grids: MutableList<Grid>) {

        val wons = mutableSetOf<Grid>()

        fun play(cond: MutableSet<Grid>.() -> Int): Int {
            with(inputs.iterator()) {
                while (wons.size != grids.size && hasNext()) {
                    next().run {
                        grids.filter { it.winningValue == null }
                            .forEach {
                                it.mark(this)
                                if (it.winningValue != null) wons.add(it)
                            }
                    }
                }
            }
            return wons.cond()
        }
    }

    data class Grid(private val input: String) {

        var winningValue: Int? = null

        private val grid: List<Element> = input.lines().flatMapIndexed { y: Int, line: String ->
            line.trim().replace("  ", " ").split(" ").mapIndexed { x, c ->
                Element(x, y, c.toInt())
            }
        }

        fun mark(input: Int) {
            grid.filter { it.value == input }.forEach { it.checked = true }

            if (won()) {
                winningValue = input * uncheckedSum()
            }
        }

        fun byX() = grid.groupBy { it.x }.values
        fun byY() = grid.groupBy { it.y }.values

        private fun won() = byX().any { it.all { it.checked } } || byY().any { it.all { it.checked } }

        private fun uncheckedSum() = grid.filterNot { it.checked }.map { it.value }.sum()
    }

    data class Element(val x: Int, val y: Int, val value: Int, var checked: Boolean = false)

}

processStars(Day4::star1, Day4::star2)