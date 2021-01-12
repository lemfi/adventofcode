package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day8.star1(Day8.data)
        .apply { println(this) }

    Day8.star2(Day8.data)
        .apply { println(this) }

}

object Day8 {

    fun star1(data: String): Int {

        return data
            .toOperations()
            .play()
    }

    fun star2(data: String): Int {

        return data
            .toOperations()
            .repair()
            .play()
    }

    sealed class Operation(var executed: Boolean = false) {
        abstract val value: Int

        fun next(index: Int) = when (this) {
            is Acc, is Nop -> index + 1
            is Jmp -> index + value
        }
        fun execute(acc: Int) = when (this) {
            is Jmp, is Nop -> acc
            is Acc -> acc + value
        }

        data class Acc(
            override val value: Int
        ): Operation()

        data class Jmp(
            override val value: Int
        ): Operation()

        data class Nop(
            override val value: Int
        ): Operation()
    }

    fun String.toOperations() = lines().map {
        it.split(" ").let {
            when (it.first()) {
                "acc" -> Operation.Acc(it.last().toInt())
                "jmp" -> Operation.Jmp(it.last().toInt())
                "nop" -> Operation.Nop(it.last().toInt())
                else -> Operation.Nop(-1)
            }
        }
    }

    fun List<Operation>.play(acc: Int = 0, index: Int = 0): Int {
        if (index >= size) return acc

        val op = this[index]
        if (op.executed) return acc

        op.executed = true
        return play(op.execute(acc), op.next(index))
    }

    fun List<Operation>.isValid(index: Int = 0): Boolean {

        if (index >= size) return true

        val op = this[index]
        if (op.executed) return false

        op.executed = true
        return isValid(op.next(index))
    }

    fun List<Operation>.copy() = map {
        when (it) {
            is Operation.Acc -> it.copy()
            is Operation.Nop -> it.copy()
            is Operation.Jmp -> it.copy()
        }
    }

    fun List<Operation>.repair(): List<Operation> {

        var operations = this
        var index = 0
        while (!operations.isValid()) {
            operations = copy().toMutableList().apply {
                set(index, this[index].let {
                    when (it) {
                        is Operation.Nop -> Operation.Jmp(it.value)
                        is Operation.Jmp -> Operation.Nop(it.value)
                        is Operation.Acc -> it.copy()
                    }
                })
            }
            index ++
        }
        return operations.copy()
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day8.txt").readAllBytes().toString(Charset.defaultCharset()) }

}