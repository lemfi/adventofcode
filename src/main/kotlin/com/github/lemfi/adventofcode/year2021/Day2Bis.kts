package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day2Bis {

    private fun String?.toData() = this ?: data(2)

    private fun String?.prepareData() = toData()
        .lines()
        .map {
            it.split(" ").let { (instruction, value) ->
                when (instruction) {
                    "forward" -> Forward(value.toInt())
                    "up" -> Up(value.toInt())
                    "down" -> Down(value.toInt())
                    else -> error("hmm...")
                }
            }
        }

    fun star1(input: String?) =
        ResultHolderStar1().resolve(input)

    fun star2(input: String?) =
        ResultHolderStar2().resolve(input)

    private fun ResultHolder.resolve(input: String?) = input
        .prepareData()
        .fold(this) { resultHolder, instruction ->
            with(instruction) { resultHolder.resolve() }
        }.run { x * y }

    sealed class ResultHolder {
        abstract val x: Int
        abstract val y: Int
    }

    data class ResultHolderStar1(
        override val x: Int = 0,
        override val y: Int = 0,
    ) : ResultHolder()

    data class ResultHolderStar2(
        override val x: Int = 0,
        override val y: Int = 0,
        val aim: Int = 0,
    ) : ResultHolder()

    sealed class Instruction {
        abstract val move: Int
        abstract fun ResultHolder.resolve(): ResultHolder
    }

    data class Forward(override val move: Int) : Instruction() {

        override fun ResultHolder.resolve(): ResultHolder {
            return when (this) {
                is ResultHolderStar1 -> ResultHolderStar1(x + move, y)
                is ResultHolderStar2 -> ResultHolderStar2(x + move, y - aim * move, aim)
                else -> error("Hmm")
            }
        }
    }

    data class Up(override val move: Int) : Instruction() {

        override fun ResultHolder.resolve() =
            when (this) {
                is ResultHolderStar2 -> ResultHolderStar2(x, y, aim + move)
                is ResultHolderStar1 -> ResultHolderStar1(x, y - move)
                else -> error("Hmm")
            }
    }

    data class Down(override val move: Int) : Instruction() {

        override fun ResultHolder.resolve() =
            when (this) {
                is ResultHolderStar1 -> ResultHolderStar1(x, y + move)
                is ResultHolderStar2 -> ResultHolderStar2(x, y, aim - move)
                else -> error("Hmm")
            }
    }

}

processStars(Day2Bis::star1, Day2Bis::star2)