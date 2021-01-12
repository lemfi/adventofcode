package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset

fun main() {

    Day7.star1(Day7.data)
        .apply { println(this) }

    Day7.star2(Day7.data)
        .apply { println(this) }

}

object Day7 {

    data class Bag(
        val color: String,
        val containedBags: List<Pair<Int, String>>
    )

    fun star1(data: String): Int {

        return data
            .toBags().let { bags ->
                bags `which contain` "shiny gold"
            }.size
    }

    fun star2(data: String): Int {

        return data
            .toBags()
            .let { bags ->
                "shiny gold" `contains how much` bags
            }
    }

    infix fun String.`contains how much`(bags: List<Bag>): Int {
        return bags.firstOrNull {
            it.color == this
        }?.let {
            it.containedBags.fold(it.containedBags.map { it.first }.sum()) { containedBags, current ->
                containedBags + current.first * ( current.second `contains how much` bags )
            }
        } ?: 0
    }

    infix fun List<Bag>.`which contain`(color: String): Set<String> {
        return filter { it.containedBags.map { it.second }.contains(color) }
            .map { it.color }
            .let {
                if (it.isEmpty()) setOf()
                else it.fold(it.toSet()) { res, current -> res.union(`which contain`(current)) }
            }
    }

    private fun String.toBags(): List<Bag> = lines().map {
        it.split(" bags contain ")
            .let { (color, bags) ->
                bags.replace(".", "").replace("bags", "").replace("bag", "").trim().split(", ").let {
                    if (it.size == 1 && it.first() == "no other") Bag(color, emptyList())
                    else Bag(color, it.map { it.substringBefore(" ").trim().toInt() to it.substringAfter(" ").trim() })
                }
            }
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day7.txt").readAllBytes().toString(Charset.defaultCharset()) }
}