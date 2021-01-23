package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day19.star1(Day19.data)
        .apply { println("res: $this") }

    Day19.star2(Day19.data)
        .apply { println("res: $this") }

}

object Day19 {

    fun star1(data: String): Long {
        return data.split("\n\n").let {
            Data(it.first().lines(), it.last().lines())
        }.countMatch().toLong()

    }

    fun star2(data: String): Long {

        return data.lines().map {
            if(it.startsWith("8:")) "8: ( 42 )+"
            else if(it.startsWith("11:")) "11: 42 31 | 42 42 31 31 | 42 42 42 31 31 31 | 42 42 42 42 31 31 31 31"
            else it
        }
            .joinToString("\n")
            .split("\n\n").let {
                Data(it.first().lines(), it.last().lines())
            }
            .countMatch().toLong()
    }

    data class Data(
        private val init: List<String>,
        val data: List<String>
    ) {
        val rules = init.map { it.split(": ").let {
            Rule(it.first(), if (it.last().length == 3 && it.last().startsWith("\"") && it.last().endsWith("\"")) it.last() else " ( ${it.last()} ) ")
        } }.toMutableList()

        private var rule: String
        private var regex: Regex
        init {
            while (rules.getNotFinals().isNotEmpty()) {

                val finals = rules.getFinals()
                val notfinals = rules.getNotFinals()

                rules.clear()
                notfinals.let {
                    it.map { it.transform(finals) }
                        .let {
                            rules.clear()
                            rules.addAll(it)
                        }
                }
                rules.addAll(finals)

            }
            rule = rules.first { it.name == "0" }.rule.replace(" | ", "|").replace("\" \"", "").replace(" ", "").replace("\"", "")
            regex = rule.toRegex()
        }

        fun countMatch() = data.count { regex.matches(it) }

        override fun toString(): String {
            return "rule=$rule, data=$data"
        }
    }

    fun Rule.transform(finals: List<Rule>): Rule {
        val names = finals.map { it.name }
        var newRule = rule
        names.forEach { name -> newRule = newRule.replace(" $name ", " " + finals.first {it.name == name}.rule + " ") }
        return Rule(name, newRule)
    }

    fun List<Rule>.getFinals() = filterNot {
        it.rule.toList().map { it.toString() }
            .map { it.toIntOrNull() }
            .filterNotNull()
            .isNotEmpty()
    }

    fun List<Rule>.getNotFinals() = filter {
        it.rule.toList().map { it.toString() }
            .map { it.toIntOrNull() }
            .filterNotNull()
            .isNotEmpty()
    }

    data class Rule(
        val name: String,
        private val init: String
    ) {
        val rule = init.split("|")
            .filterNot { it.isBlank() }
            .joinToString("|")

        override fun toString(): String {
            return "name=$name, rule=$rule"
        }
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day19.txt").readAllBytes().toString(Charset.defaultCharset()) }
}

