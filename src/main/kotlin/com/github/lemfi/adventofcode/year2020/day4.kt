package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day4.star1(Day4.data).apply { println(this) }
    Day4.star2(Day4.data).apply { println(this) }
}

object Day4 {

    fun star1(data: String) = data
            .split("\n\n")
            .map { it.split(" ").map { it.lines() }.flatten().map { it.split(":").let { it.first() to it.last() } } }
            .let {
                Rules().countValid(it, Rules::listSize, Rules::expectedEntries)
            }

    fun star2(data: String) = data
            .split("\n\n")
            .map { it.split(" ").map { it.lines() }.flatten().map { it.split(":").let { it.first() to it.last() } } }
            .let {
                Rules().countValid(it, Rules::listSize, Rules::expectedEntries, Rules::checkEntries)
            }

    class Rules() {

        fun listSize(list: List<Pair<String, String>>) = list.size in 7..8
        fun expectedEntries(list: List<Pair<String, String>>) = list.map { it.first }.containsAll(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))
        fun checkEntries(list: List<Pair<String, String>>) = list.all { checkEntry(it) }

        private fun checkEntry(entry: Pair<String, String>) = when (entry.first) {
            "byr" -> checkByr(entry.second)
            "iyr" -> checkIyr(entry.second)
            "eyr" -> checkEyr(entry.second)
            "hgt" -> checkHgt(entry.second)
            "hcl" -> checkHcl(entry.second)
            "ecl" -> checkEcl(entry.second)
            "pid" -> checkPid(entry.second)
            "cid" -> checkCid(entry.second)
            else -> false
        }

        private fun checkByr(value: String) = value.let { it.length == 4 && it.toIntOrNull()?.let { it in 1920..2002 } ?: false }
        private fun checkIyr(value: String) = value.let { it.length == 4 && it.toIntOrNull()?.let { it in 2010..2020 } ?: false }
        private fun checkEyr(value: String) = value.let { it.length == 4 && it.toIntOrNull()?.let { it in 2020..2030 } ?: false }
        private fun checkHgt(value: String) = value.let {
            when {
                it.endsWith("cm") -> it.substringBefore("cm").toIntOrNull()?.let { it in 150..193 } ?: false
                it.endsWith("in") -> it.substringBefore("in").toIntOrNull()?.let { it in 59..76 } ?: false
                else -> false
            }
        }
        private fun checkHcl(value: String) = value.length == 7
                && value.startsWith("#")
                && value.substringAfter("#").toList().all { listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f').contains(it) }
        private fun checkEcl(value: String) = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)
        private fun checkPid(value: String) = value.length == 9 && value.toIntOrNull() != null
        private fun checkCid(value: String) = true

        fun countValid(list: List<List<Pair<String, String>>>, vararg rules: Rules.(List<Pair<String, String>>)->Boolean) =
                list.fold(0) {
                    res, entry -> if (rules.fold(true) { valid, rule -> valid && rule(entry) }) res + 1 else res
                }
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day4.txt").readAllBytes().toString(Charset.defaultCharset()) }

}
