package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars
import com.github.lemfi.adventofcode.year2022.Day21.toOperation

object Day21 {

    private fun String?.toData() = this ?: data(21)

    fun star1(input: String?) = input.toData().lines().let { lines ->
        lines
            .first { it.startsWith("root") }.substringAfter(": ")
            .toOperation(lines)
            .resolve()
    }

//        fun star2(input: String?) = input.toData().lines().let { lines ->
//            lines
//                .first { it.startsWith("root") }.substringAfter(": ")
//                .replace("+", "-")
//                .replace("*", "-")
//                .replace("/", "-").let { root ->
////                    (3239999999999..3239999999999).first { guess ->
//                    (3243420789720..3243420789728).first { guess ->
//                        root.toOperation(lines.map { if (it.startsWith("humn")) "humn: $guess" else it }).resolve().let {
//                            println("$guess => $it")
//                            it == 0L
//                        }
//                    }
//
//                }
//        }

    fun star2(input: String?) = input.toData().lines().let { lines ->
        var linesToCompute = lines.map {
            if (it.startsWith("root")) it.replace("+", "-").replace("*", "-").replace("/", "-")
            else if (it.startsWith("humn")) "humn: XXXX"
            else it
        }
        var res = 0L
        while (linesToCompute.isNotEmpty()) {

            val size = linesToCompute.size
            val numbersOnlyPredicate: (String)->Boolean = { runCatching { it.substringAfter(": ").toLong() }.getOrNull() != null }
            val numbersReplacements = linesToCompute.filter(numbersOnlyPredicate).map { it.split(": ") }
            linesToCompute = linesToCompute.filterNot(numbersOnlyPredicate).map {
                var newLine = it
                numbersReplacements.forEach { replacement ->
                    newLine = newLine.replace(replacement.first(), replacement.last())
                }
                newLine
            }
            val sums = linesToCompute.filter { it.contains("+") }.map { line ->
                line.substringAfter(": ").split(" + ").let { (o1, o2) ->
                    if (o1.isNumber() && o2.isNumber()) "${line.substringBefore(":")}: ${o1.toNumber() + o2.toNumber()}" else line
                }
            }
            val subs = linesToCompute.filter { it.contains("-") }.map { line ->
                line.substringAfter(": ").split(" - ").let { (o1, o2) ->
                    if (o1.isNumber() && o2.isNumber()) "${line.substringBefore(":")}: ${o1.toNumber() - o2.toNumber()}" else line
                }
            }
            val mul = linesToCompute.filter { it.contains("*") }.map { line ->
                line.substringAfter(": ").split(" * ").let { (o1, o2) ->
                    if (o1.isNumber() && o2.isNumber()) "${line.substringBefore(":")}: ${o1.toNumber() * o2.toNumber()}" else line
                }
            }
            val div = linesToCompute.filter { it.contains("/") }.map { line ->
                line.substringAfter(": ").split(" / ").let { (o1, o2) ->
                    if (o1.isNumber() && o2.isNumber()) "${line.substringBefore(":")}: ${o1.toNumber() / o2.toNumber()}" else line
                }
            }
            val humn = linesToCompute.filter { it.startsWith("humn") }

            linesToCompute = sums + subs + mul + div + humn
            if (linesToCompute.size == size) {
                break
            }
        }

        var known: Pair<String, Long> = linesToCompute.first { it.startsWith("root") }.substringAfter(": ").split(" - ").let { (o1, o2) ->
            if (o1.isNumber()) o2 to o1.toNumber() else o1 to o2.toNumber()
        }
        while (known.first != "humn") {
            linesToCompute.first { it.startsWith(known.first) }.let {
                val operation = it.substringAfter(": ")
                if (operation.contains("*")) {
                    operation.split(" * ").let { (o1, o2) ->
                        known = if (o1.isNumber()) o2 to (known.second / o1.toNumber())
                        else o1 to (known.second / o2.toNumber())
                    }
                }
                if (operation.contains("+")) {
                    operation.split(" + ").let { (o1, o2) ->
                        known = if (o1.isNumber()) o2 to (known.second - o1.toNumber())
                        else o1 to (known.second - o2.toNumber())
                    }
                }
                if (operation.contains("/")) {
                    operation.split(" / ").let { (o1, o2) ->
                        known = if (o1.isNumber()) o2 to (o1.toNumber() / known.second)
                        else o1 to (known.second * o2.toNumber())
                    }
                }
                if (operation.contains("-")) {
                    operation.split(" - ").let { (o1, o2) ->
                        known = if (o1.isNumber()) o2 to (o1.toNumber() - known.second)
                        else o1 to (known.second + o2.toNumber())
                    }
                }
            }
        }
        known.second
    }

    private fun String.isNumber() = runCatching { toLong() }.getOrNull() != null
    private fun String.toNumber() = runCatching { toLong() }.getOrNull()!!

    private fun String.toOperation(lines: List<String>): Operation =
        when {
            contains("+") -> split(" + ").let { (o1, o2) -> (o1 to o2).toSumOperation(lines) }
            contains("-") -> split(" - ").let { (o1, o2) -> (o1 to o2).toSubOperation(lines) }
            contains("*") -> split(" * ").let { (o1, o2) -> (o1 to o2).toMulOperation(lines) }
            contains("/") -> split(" / ").let { (o1, o2) -> (o1 to o2).toDivOperation(lines) }
            runCatching { toLong() }.getOrNull() == null -> lines.first { it.startsWith(this) }.substringAfter(": ").toOperation(lines)
            else -> NumberOperation(toLong())
        }

    private fun Pair<String, String>.toSumOperation(lines: List<String>): SumOperation =
        SumOperation(first.toOperation(lines), second.toOperation(lines))
    private fun Pair<String, String>.toSubOperation(lines: List<String>): SubOperation =
        SubOperation(first.toOperation(lines), second.toOperation(lines))
    private fun Pair<String, String>.toMulOperation(lines: List<String>): MulOperation =
        MulOperation(first.toOperation(lines), second.toOperation(lines))
    private fun Pair<String, String>.toDivOperation(lines: List<String>): DivOperation =
        DivOperation(first.toOperation(lines), second.toOperation(lines))



    private sealed interface Operation {
        fun resolve(): Long
    }

    private data class NumberOperation(val value: Long):  Operation {
        override fun resolve(): Long = value
    }

    private data class SumOperation(val value1: Operation, val value2: Operation): Operation {
        override fun resolve(): Long = value1.resolve() + value2.resolve()
    }
    private data class DivOperation(val value1: Operation, val value2: Operation): Operation {
        override fun resolve(): Long = value1.resolve() / value2.resolve()
    }
    private data class MulOperation(val value1: Operation, val value2: Operation): Operation {
        override fun resolve(): Long  = value1.resolve() * value2.resolve()
    }
    private data class SubOperation(val value1: Operation, val value2: Operation): Operation {
        override fun resolve(): Long = value1.resolve() - value2.resolve()
    }
}

fun main() {
    processStars(Day21::star1, Day21::star2)
}
// 3243420786087 too low