package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day18.star1(Day18.data)
        .apply { println("res: $this") }

    Day18.star2(Day18.data)
        .apply { println("res: $this") }

}

object Day18 {

    fun star1(data: String): Long {

        return data.lines().map { Calc(it, Calc::arithmeticRule1).compute() }.sum()
    }

    fun star2(data: String): Long {

        return data.lines().map { Calc(it, Calc::arithmeticRule2).compute() }.sum()
    }

    class Calc(private val input: String, private val arithmeticRule: Calc.(Operation, List<String>)->Long) {

        fun compute(): Long = input.replace(" ", "")
            .toList()
            .map { it.toString() }
            .compute()

        fun List<String>.compute(): Long {

            val v1 = getVal()
            val op = v1.second.first()
            val v2 = v1.second.drop(1).getVal()

            return Operation(v1.first, v2.first, op).let { it ->
                if (v2.second.isNotEmpty()) arithmeticRule(it, v2.second)
                else it.compute()
            }
        }

        fun arithmeticRule1(operation: Operation, rest: List<String>) = (mutableListOf(operation.compute().toString()) + rest).compute()

        fun arithmeticRule2(operation: Operation, rest: List<String>) =
            if (operation.operator == "+" || rest.first() == "*") (mutableListOf(operation.compute().toString()) + rest).compute()
            else operation.copy(operand2 = (mutableListOf(operation.operand2.toString()) + rest).compute()).compute()

        private fun List<String>.getVal(): Pair<Long, List<String>> = if (first() == "(") {
            getGroup().let { it.first.compute() to it.second }
        } else first().toLong() to drop(1)

        private fun List<String>.getGroup(): Pair<List<String>, List<String>> {
            var nbP = 0
            return takeWhile {
                if (it == "(") nbP ++
                if (it == ")") nbP --
                nbP > 0
            }.drop(1) to dropWhile {
                if (it == "(") nbP ++
                if (it == ")") nbP --
                nbP > 0
            }.drop(1)
        }
    }

    data class Operation(val operand1: Long, val operand2: Long, val operator: String) {

        fun compute() = when (operator) {
            "+" -> operand1 + operand2
            "*" -> operand1 * operand2
            else -> error("unknown operand: $operator")
        }
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day18.txt").readAllBytes().toString(Charset.defaultCharset()) }
}
