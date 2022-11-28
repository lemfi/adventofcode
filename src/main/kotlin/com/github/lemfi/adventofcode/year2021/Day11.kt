package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day11 {

    private fun String?.toData() = this ?: data(11)

    private fun String?.prepareData() = toData().lines().let { lines ->
        (lines.indices).flatMap { y ->
            (lines.first().indices).map { x ->
                Octopus(x, y, lines[y][x].toString().toInt())
            }
        }
    }

    fun star1(input: String?) = input
        .prepareData()
        .let { octopuses ->
            (0 until 100).sumOf {
                octopuses.increaseEnergyAndDoTheMagic()
            }
        }

    fun star2(input: String?) = input
        .prepareData()
        .let { octopuses ->
            var iter = 0
            while (octopuses.any { it.value != 0 }) {
                octopuses.increaseEnergyAndDoTheMagic()
                iter++
            }
            iter
        }

    private fun List<Octopus>.increaseEnergyAndDoTheMagic() =
        with(toMutableList()) {
            val flashed = increaseEnergy().flashing()
            while (flashed.isNotEmpty()) {
                flashPropagation(flashed)
            }
            this@increaseEnergyAndDoTheMagic.size - size
        }

    data class Octopus(val x: Int, val y: Int, var value: Int)

    private fun Collection<Octopus>.increaseEnergy() = onEach { it.value += 1 }

    private fun Collection<Octopus>.flashing() =
        filter { it.value > 9 }
            .onEach { it.value = 0 }
            .toMutableList()

    private fun MutableList<Octopus>.flashPropagation(flashed: MutableList<Octopus>) {
        removeAll(flashed)
        increaseNeighborsEnergy(flashed)
            .flashing()
            .also { justFlashed ->
                flashed.clear()
                flashed.addAll(justFlashed)
            }
    }

    private fun Collection<Octopus>.increaseNeighborsEnergy(octopus: Collection<Octopus>) = apply {
        octopus.onEach { neighbors(it).increaseEnergy() }
    }

    private fun Collection<Octopus>.neighbors(octopus: Octopus) = filter {
        (it.x == octopus.x + 1 && it.y == octopus.y)
                || (it.x == octopus.x - 1 && it.y == octopus.y)
                || (it.x == octopus.x && it.y == octopus.y + 1)
                || (it.x == octopus.x && it.y == octopus.y - 1)
                || (it.x == octopus.x + 1 && it.y == octopus.y - 1)
                || (it.x == octopus.x + 1 && it.y == octopus.y + 1)
                || (it.x == octopus.x - 1 && it.y == octopus.y - 1)
                || (it.x == octopus.x - 1 && it.y == octopus.y + 1)
    }
}

fun main() {
    processStars(Day11::star1, Day11::star2)
}
