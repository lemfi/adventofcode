package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day16.star1(Day16.data)
        .apply { println("res: $this") }

    Day16.star2(Day16.data)
        .apply { println("res: $this") }

}

object Day16 {

    fun star1(data: String): Int {
        return data.toData()
            .let { tickets ->
                tickets.them
                    .flatMap { it.getInvalids(tickets.rules) }
                    .sum()
            }
    }

    fun star2(data: String): Long {
        return data.toData()
            .let { tickets ->
                tickets.determineRuleIndex()
                    .filter { it.first.name.startsWith("departure") }
                    .map { tickets.me[it.second].toLong() }
                    .reduce { acc, i -> acc * i }
            }

    }

    class Data(
        val rules: List<Rule>,
        val me: List<Int>,
        val them: List<List<Int>>
    ) {
        var validTickets: List<List<Int>> = them.filter { it.getInvalids(rules).isEmpty() }

        private var matrix: List<List<Int>> = mutableListOf<List<Int>>().apply {
            (me.indices).forEach { indice ->
                add(listOf(me[indice]) + validTickets.map { it[indice] })
            }
        }.toList()



        fun determineRuleIndex(): List<Pair<Rule, Int>> {

            return mutableListOf<Pair<Rule, List<Int>>>().also { rulesPossibilities ->

                rules.forEach { rule ->
                    matrix.foldIndexed((0..matrix.size).toList()) { index, acc, list ->

                        when {
                            list.getInvalids(listOf(rule)).isNotEmpty() -> acc.toMutableList().apply { remove(index) }.toList()
                            else -> acc
                        }
                    }
                        .also {
                            rulesPossibilities.add(rule to it)
                        }
                }

            }.let { possibilities ->

                mutableListOf<Pair<Rule, Int>>().apply {

                    while (size < rules.size) {

                        possibilities.flatMap { it.second }
                            .let { flattened ->
                                flattened.first { value -> flattened.count { it == value } == 1 }
                            }.let { value ->
                                possibilities.first { it.second.contains(value) }
                                    .also {
                                        add(it.first to value)
                                        possibilities.remove(it)
                                    }
                            }
                    }
                }

            }
        }
    }

    fun List<Int>.getInvalids(rules: List<Rule>): List<Int> {
        return map { number -> if (rules.none { it.range1.contains(number) || it.range2.contains(number) }) number else null }
            .filterNotNull()
    }

    fun String.toData() = split("\n\n")
        .let {
            Data(
                rules = it.toRules(),
                me = it.drop(1).first().toTicket(),
                them = it.last().toTickets()
            )
        }

    fun String.toTicket() = lines().last().split(",").map { it.toInt() }

    fun String.toTickets() = lines().drop(1).map { it.toTicket() }

    fun List<String>.toRules(): List<Rule> =
        first().lines().map {
            it.split(": ").let {
                Rule(
                    it.first(),
                    it.last().split(" or ").first().let { it.split("-").let { it.first().toInt()..it.last().toInt() } },
                    it.last().split(" or ").last().let { it.split("-").let { it.first().toInt()..it.last().toInt() } },
                )
            }
        }


    data class Rule(
        val name: String,
        val range1: IntRange,
        val range2: IntRange
    )

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day16.txt").readAllBytes().toString(Charset.defaultCharset()) }

}
