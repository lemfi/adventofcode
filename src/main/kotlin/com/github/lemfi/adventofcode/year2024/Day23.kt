package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day23 {

    private fun String?.toData() = this ?: data(23)

    fun star1(input: String?) = input.parse()
        .let { map ->
            map.keys
                .flatMap { it groupsStar1 map }
                .toSet()
                .filter { it.any { computer -> computer.startsWith("t") } }
                .size
        }

    fun star2(input: String?) = input.parse()
        .let { map ->
            map.keys
                .flatMap { setOf(it) groupsStar2 map }
                .maxBy { it.size }
                .sorted()
                .joinToString(",")
        }

    private infix fun String.groupsStar1(map: Map<String, List<String>>): List<List<String>> {
        val values = map[this]!!
        return values.flatMap { v ->
            val nexts = map[v]!!
            val filtered = nexts.filter { it in values }
            if (filtered.size == 1) listOf((listOf(this, v) + filtered).sorted())
            else filtered.map { listOf(this, v, it).sorted() }
        }.toSet().toList()
    }

    private val cache = mutableMapOf<Set<String>, Set<Set<String>>>()
    private infix fun Set<String>.groupsStar2(map: Map<String, List<String>>): Set<Set<String>> {
        return cache[this] ?: (setOf(this) + map.entries
            .filter { it.value.containsAll(this) }
            .flatMap {
                setOf(*this.toTypedArray(), it.key).groupsStar2(map)
            }
            .toSet()
            .also {
                cache[this] = it
            })
    }

    private fun String?.parse() = toData().lines()
        .map { it.split("-") }
        .flatMap { listOf(it[0] to it[1], it[1] to it[0]) }
        .groupBy { (c1, _) -> c1 }.map { (k, v) -> k to v.map { (_, c2) -> c2 } }
        .toMap()

}

fun main() {
    processStars(Day23::star1, Day23::star2)
}
