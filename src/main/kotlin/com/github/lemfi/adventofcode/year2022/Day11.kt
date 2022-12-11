package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day11 {

    private fun String?.toData() = this ?: data(11)

    fun star1(input: String?) = input
        .toMonkeys()
        .observeMonkeys(1..20) { it / 3 }

    fun star2(input: String?) = input
        .toMonkeys()
        .let { monkeys ->
            val modulo = monkeys.fold(1L) { acc, m -> acc * m.div }
            monkeys.observeMonkeys(1..10000) { it % modulo }
        }

    private fun List<Monkey>.observeMonkeys(turns: IntRange, worryDiminution: (Long) -> Long) = apply {
        turns
            .forEach { _ ->
                forEach { monkey ->
                    monkey.items.forEach { item ->
                        val newWorry = worryDiminution(monkey.operation.compute(item))
                        val newMonkey = monkey.test(newWorry)
                        get(newMonkey).items.add(newWorry)
                    }
                    monkey.inspected += monkey.items.size
                    monkey.items.clear()
                }
            }
    }
        .sortedByDescending { it.inspected }
        .take(2)
        .let { (m1, m2) -> m1.inspected * m2.inspected }

    data class Monkey(
        val name: String,
        val items: MutableList<Long>,
        val div: Long,
        val operation: Operation,
        val trueMonkey: String,
        val falseMonkey: String,
        var inspected: Long = 0,
    ) {

        fun test(item: Long) = if (item % div == 0L) trueMonkey else falseMonkey
    }

    fun List<Monkey>.get(name: String) = first { it.name == name }

    sealed interface Operation {
        fun compute(old: Long): Long
    }

    data class Plus(val operand: Long?) : Operation {
        override fun compute(old: Long) = (operand?.let { old + operand } ?: (old + old))
    }

    data class Mult(val operand: Long?) : Operation {
        override fun compute(old: Long) = (operand?.let { old * operand } ?: (old * old))
    }

    private fun String?.toMonkeys() =
        toData()
            .split("\n\n").map {
                it.lines().let { (name, starting, operation, test, ifTrue, ifFalse) ->

                    Monkey(
                        name = name.substringBefore(":").uppercase(),
                        items = starting.substringAfter("Starting items: ").split(", ").map { it.toLong() }
                            .toMutableList(),
                        operation = operation.substringAfter("old ").let {
                            when (it.first()) {
                                '*' -> Mult(runCatching { it.substringAfter("* ").toLong() }.getOrNull())
                                '+' -> Plus(runCatching { it.substringAfter("+ ").toLong() }.getOrNull())
                                else -> error("wtf is that operation?")
                            }
                        },
                        div = test.substringAfter("Test: divisible by ").toLong(),
                        trueMonkey = ifTrue.substringAfter("throw to ").uppercase(),
                        falseMonkey = ifFalse.substringAfter("throw to ").uppercase(),
                    )
                }
            }

    private operator fun <E> List<E>.component6(): E = get(5)
}


fun main() {
    processStars(Day11::star1, Day11::star2)
}