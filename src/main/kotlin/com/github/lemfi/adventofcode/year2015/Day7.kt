package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day7 {

    private fun String?.toData() = this ?: data(7)

    sealed class Operation {
        abstract fun isResolvable(): Boolean
        abstract fun replace(ref: String, value: Int)
        abstract fun resolve(): Int

    }

    data class IntValue(val value: Int) : Operation() {
        override fun isResolvable() = true
        override fun replace(ref: String, value: Int) {}
        override fun resolve(): Int = value
    }

    data class RefValue(var value: String) : Operation() {
        override fun isResolvable(): Boolean = runCatching { value.toInt() }.isSuccess

        override fun replace(ref: String, value: Int) {
            if (this.value == ref) this.value = value.toString()
        }

        override fun resolve(): Int = value.toInt()
    }

    data class And(var first: String, var second: String) : Operation() {
        override fun isResolvable() =
            runCatching { first.toInt() }.isSuccess && runCatching { second.toInt() }.isSuccess

        override fun replace(ref: String, value: Int) {
            if (first == ref) first = value.toString()
            if (second == ref) second = value.toString()
        }

        override fun resolve(): Int = first.toInt() and second.toInt()
    }

    data class Or(var first: String, var second: String) : Operation() {
        override fun isResolvable() =
            runCatching { first.toInt() }.isSuccess && runCatching { second.toInt() }.isSuccess

        override fun replace(ref: String, value: Int) {
            if (first == ref) first = value.toString()
            if (second == ref) second = value.toString()
        }

        override fun resolve(): Int = first.toInt() or second.toInt()
    }

    data class LeftShift(var first: String, val shift: Int) : Operation() {
        override fun isResolvable() = runCatching { first.toInt() }.isSuccess

        override fun replace(ref: String, value: Int) {
            if (first == ref) first = value.toString()
        }

        override fun resolve(): Int = first.toInt() shl shift
    }

    data class RightShift(var first: String, val shift: Int) : Operation() {
        override fun isResolvable() = runCatching { first.toInt() }.isSuccess

        override fun replace(ref: String, value: Int) {
            if (first == ref) first = value.toString()
        }

        override fun resolve(): Int = first.toInt() shr shift
    }

    data class Not(var first: String) : Operation() {
        override fun isResolvable() = runCatching { first.toInt() }.isSuccess

        override fun replace(ref: String, value: Int) {
            if (first == ref) first = value.toString()
        }

        override fun resolve(): Int = 65535 - first.toInt()
    }

    data class Action(val source: Operation, val destination: String)

    private fun String.toOperation(): Operation = when {
        contains("AND") -> And(substringBefore(" AND "), substringAfter(" AND "))
        contains("OR") -> Or(substringBefore(" OR "), substringAfter(" OR "))
        contains("LSHIFT") -> LeftShift(substringBefore(" LSHIFT "), substringAfter(" LSHIFT ").toInt())
        contains("RSHIFT") -> RightShift(substringBefore(" RSHIFT "), substringAfter(" RSHIFT ").toInt())
        contains("NOT") -> Not(substringAfter("NOT "))
        else -> runCatching { IntValue(toInt()) }.recoverCatching { RefValue(this) }.getOrThrow()
    }

    private fun List<Action>.resolveA() = also { actions ->
        while (actions.any { !it.source.isResolvable() }) {
            actions.filter { it.source.isResolvable() }.apply {
                forEach { action ->
                    val value = action.source.resolve()
                    actions.forEach { it.source.replace(action.destination, value) }
                }
            }
        }
    }.first { it.destination == "a" }.source.resolve()

    fun star1(input: String?) = input.toData().lines()
        .map { Action(it.substringBefore(" ->").toOperation(), it.substringAfter("-> ")) }
        .resolveA()

    fun star2(input: String?) = (input.toData().lines()
        .map { Action(it.substringBefore(" ->").toOperation(), it.substringAfter("-> ")) }
        .filterNot { it.destination == "b" } + listOf(Action(IntValue(star1(input)), "b")))
        .resolveA()
}

fun main() {
    processStars(Day7::star1, Day7::star2)
}
