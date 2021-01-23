package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.system.measureTimeMillis

fun main() {

    measureTimeMillis {
        Day23.star1(100, Day23.data)
            .apply { println("res: $this") }
    }.apply { println("   in ${this / 1000}s") }

    measureTimeMillis {

        Day23.star2(10_000_000, Day23.data)
            .apply { println("res: $this") }
    }.apply { println("   in ${this / 1000}s") }

}

object Day23 {

    fun star1(moves: Int, data: String): String {

        return data.toList().map { it.toString().toLong() }.let { input ->
            Circle(input.first()) {
                (1..input.size-2).forEach { add(input[it], input[it - 1], input[it + 1]) }
                add(input.first(), input.last(), input[1])
                add(input.last(), input[input.size-2], input.first())
            }
                .let { circle ->
                    (1..moves).forEach { _ ->
                        circle.move()
                    }
                    circle.print()
                }
        }
    }

    fun star2(moves: Int, data: String): Long {

        return data.toList().map { it.toString().toLong() }.let { input ->
            Circle(input.first()) {
                (1..input.size-2).forEach { add(input[it], input[it - 1], input[it + 1]) }
                (11L..999_999L).forEach { add(it) }
                add(input.first(), 1_000_000, input[1])
                add(input.last(), input[input.size-2], 10)
                add(10, input.last(), 11)
                add(1_000_000, 999_999, input.first())
            }
                .let { circle ->
                    (1..moves).forEach { it ->
                        circle.move()
                    }
                    circle.star2()
                }
        }
    }

    class Circle(current: Long, init: Circle.()->Unit) {

        private val circle = HashMap<Long, Link>()

        var current = -1L
        var keys = listOf<Long>()

        init {
            init()
            this.current = current
            this.keys = circle.keys.sortedDescending()
        }

        fun add(value: Long, before: Long = value-1, after: Long = value+1) {
            circle.put(value, Link(before, after))
        }

        private fun move(values: List<Long>, destination: Long) {

            val before =  circle[values.first()]!!.before
            val after =  circle[values.last()]!!.after

            circle[values.last()]!!.after = circle[destination]!!.after.also {
                circle[it]!!.before = values.last()
            }
            circle[destination]!!.after = values.first()
            circle[values.first()]!!.before = destination

            circle[after]!!.before = before
            circle[before]!!.after = after

        }

        private fun nextDestination(value: Long = current - 1): Long {
            next3().let { next3 ->
                return if (value == 0L) keys.first { !next3.contains(it) }
                else if (next3.contains(value)) nextDestination(value - 1)
                else value
            }
        }

        fun move() {
            move(next3(), nextDestination())
            current = circle[current]!!.after
        }

        fun next(value: Long = current) = circle[value]!!.after
        fun next3() = listOf(next(), next(next()), next(next(next())))

        fun print(): String {
            current = circle[1]!!.after
            return mutableListOf<Long>().also {
                while (current != 1L) {
                    it.add(current)
                    current = circle[current]!!.after
                }
            }.joinToString("")
        }

        fun star2() = circle[1]!!.after * circle[circle[1]!!.after]!!.after

    }

    data class Link(
        var before: Long,
        var after: Long
    )
    val data: String by lazy { javaClass.getResourceAsStream("/2020/day23.txt").readAllBytes().toString(Charset.defaultCharset()) }

}

