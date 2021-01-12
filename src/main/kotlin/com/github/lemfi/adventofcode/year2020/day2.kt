package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day2.star1(Day2.data).apply { println(this) }
    Day2.star2(Day2.data).apply { println(this) }
}

object Day2 {

    fun rule1(min: Int, max: Int): (Char, String) -> Boolean = { letter, password ->
        password.count { it == letter } in min..max
    }

    fun rule2(i1: Int, i2: Int): (Char, String) -> Boolean = { letter, password ->
        ((letterOk(letter, i1, password) + letterOk(letter, i2, password)) == 1)
    }

    fun letterOk(letter: Char, position: Int, data: String) = if (data.getOrNull(position - 1) == letter) 1 else 0

    fun ruleChecker(data: String, ruleBuilder: (Int, Int)->((Char, String)->Boolean)) = data
            .lines().map { it.split(": ").let { it.first() to it.last() } }
            .map { (rule, password) ->
                Triple(
                        rule.substringAfter(" ")[0], // letter
                        rule.substringBefore(" ").split("-")
                                .map { it.toInt() }
                                .let { ruleBuilder(it.first(), it.last()) }, // rule
                        password
                )
            }
            .count { (letter, rule, password) -> rule(letter, password) }

    fun star1(data: String) = ruleChecker(data) { min, max -> rule1(min, max) }
    fun star2(data: String) = ruleChecker(data) { min, max -> rule2(min, max) }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day2.txt").readAllBytes().toString(Charset.defaultCharset()) }

}
