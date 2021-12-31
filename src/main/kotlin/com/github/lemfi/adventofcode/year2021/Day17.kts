package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs
import kotlin.math.max

object Day17 {

    private fun String?.toData() = this ?: data(17)

    private fun String?.prepareData() = toData().let {
        TargetArea(
            minX = it.substringAfter("target area: x=").substringBefore("..").toInt(),
            maxX = it.substringAfter("..").substringBefore(",").toInt(),
            minY = it.substringAfter(", y=").substringBefore("..").toInt(),
            maxY = it.substringAfter(", y=").substringAfter("..").toInt(),
        )
    }

    fun star1(input: String?) = input
        .toData()
        .prepareData()
        .run {
            var highestYPosition = Integer.MIN_VALUE
            tryLaunchProbe { probe -> highestYPosition = max(highestYPosition, probe.highestY) }
            highestYPosition
        }


    fun star2(input: String?) = input
        .toData()
        .prepareData()
        .run {
            mutableListOf<Pair<Int, Int>>().apply {
                tryLaunchProbe { probe -> add(probe.xVelocity to probe.yVelocity) }
            }.size
        }

    private fun TargetArea.tryLaunchProbe(successfulLaunch: (Probe) -> Unit) {
        (-2 * minX..maxX).forEach { xv ->
            (minY until abs(minY)).forEach { yv ->

                val probe = Probe(0, 0, xv, yv)
                var tempY = 0
                while (!probe.isOverTarget() && !probe.isInTarget()) {
                    probe.move()
                    tempY = max(tempY, probe.y)
                    probe.computeVelocity()

                    if (probe.isInTarget()) successfulLaunch(probe)
                }
            }
        }
    }

    data class Probe(
        var x: Int, var y: Int, var xVelocity: Int, var yVelocity: Int,
        var highestY: Int = y
    ) {
        fun move() {
            x += xVelocity
            y += yVelocity
            highestY = max(highestY, y)
        }

        fun computeVelocity() {
            xVelocity = if (xVelocity > 0) xVelocity - 1 else if (xVelocity < 0) xVelocity + 1 else xVelocity
            yVelocity--
        }
    }

    data class TargetArea(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
        fun Probe.isInTarget() = x in minX..maxX && y in minY..maxY
        fun Probe.isOverTarget() = x > maxX && y < minY || (xVelocity == 0 && y < minY)
    }
}

processStars(Day17::star1, Day17::star2)