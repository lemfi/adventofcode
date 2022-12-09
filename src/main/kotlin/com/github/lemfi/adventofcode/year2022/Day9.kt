package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import kotlin.math.abs

object Day9 {

    private fun String?.toData() = this ?: data(9)

    fun star1(input: String?) = input
        .readInstruction()
        .fold(mutableListOf(Position(0, 0) to Position(0, 0))) { ropeStates, movementInstruction ->
            ropeStates.apply {
                add(
                    movementInstruction.move(ropeStates.last().first)
                        .let { head -> head to ropeStates.last().second.move(head) }
                )
            }
        }.map { (_, tail) -> tail }.toSet().size

    fun star2(input: String?) = input
        .readInstruction()
        .fold(mutableListOf((1..10).map { Position(0, 0) })) { ropeStates, movementInstruction ->

            ropeStates.apply {
                add(
                    movementInstruction.move(ropeStates.last().first()).let { H ->
                        mutableListOf(H).apply {
                            ropeStates.last().drop(1).forEach { add(it.move(last())) }
                        }
                    })
            }

        }.map { it.last() }.toSet().size

    fun Position.move(head: Position): Position {

        return when {
            isTouching(head) -> this
            isOnTopOf(head) -> Down.move()
            isOnBottomOf(head) -> Up.move()
            isOnLeftOf(head) -> Right.move()
            isOnRightOf(head) -> Left.move()
            isDiagDownLeftOf(head) -> DiagUpRight.move()
            isDiagUpLeftOf(head) -> DiagDownRight.move()
            isDiagUpRightOf(head) -> DiagDownLeft.move()
            isDiagDownRightOf(head) -> DiagUpLeft.move()
            else -> error("did I forget one case?")
        }

    }
    
    data class Position(val x: Int, val y: Int) {
        fun Move.move() = move(this@Position)
        fun isTouching(head: Position) = abs(x - head.x) <= 1 && abs(y - head.y) <= 1
        fun isOnTopOf(head: Position) = x == head.x && y > head.y
        fun isOnBottomOf(head: Position) = x == head.x && y < head.y
        fun isOnLeftOf(head: Position) = x < head.x && y == head.y
        fun isOnRightOf(head: Position) = x > head.x && y == head.y
        fun isDiagDownLeftOf(head: Position) = x < head.x && y < head.y
        fun isDiagUpLeftOf(head: Position) = x < head.x && y > head.y
        fun isDiagUpRightOf(head: Position) = x > head.x && y > head.y
        fun isDiagDownRightOf(head: Position) = x > head.x && y < head.y
    }

    sealed interface Move {
        fun move(position: Position): Position
    }

    object Right : Move {
        override fun move(position: Position) = Position(position.x + 1, position.y)
    }

    object Left : Move {
        override fun move(position: Position) = Position(position.x - 1, position.y)
    }

    object Up : Move {
        override fun move(position: Position) = Position(position.x, position.y + 1)
    }

    object Down : Move {
        override fun move(position: Position) = Position(position.x, position.y - 1)
    }

    object DiagUpRight : Move {
        override fun move(position: Position) = Position(position.x + 1, position.y + 1)
    }

    object DiagUpLeft : Move {
        override fun move(position: Position) = Position(position.x - 1, position.y + 1)
    }

    object DiagDownRight : Move {
        override fun move(position: Position) = Position(position.x + 1, position.y - 1)
    }

    object DiagDownLeft : Move {
        override fun move(position: Position) = Position(position.x - 1, position.y - 1)
    }

    private fun String?.readInstruction() =
        toData()
            .lines()
            .flatMap {
                it.split(" ").let { (m, n) ->
                    (1..n.toInt()).map {
                        when (m) {
                            "L" -> Left
                            "D" -> Down
                            "R" -> Right
                            "U" -> Up
                            else -> error("wtf is that instruction?")
                        }
                    }
                }
            }
}

fun main() {
    processStars(Day9::star1, Day9::star2)
}