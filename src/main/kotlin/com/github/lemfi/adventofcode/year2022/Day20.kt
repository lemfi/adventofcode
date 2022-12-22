package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day20 {

    private fun String?.toData() = this ?: data(20)

    fun star1(input: String?) = input.toData().lines().mapIndexed { index, s ->
        Number(s.toLong(), index.toLong())
    }.let { numbers ->
        val position: (Number) -> Long = {
            ((it.position + it.value) % (numbers.size - 1)).let { position ->
                if (position <= 0) numbers.size - 1 + position else position
            }
        }
        numbers.forEach { number ->
            val newPosition = position(number)
            if (newPosition > number.position) {
                numbers.filter { it.position > number.position && it.position <= newPosition }.onEach {
                    it.position -= 1
                }
            } else {
                numbers.filter { it.position < number.position && it.position >= newPosition }.onEach {
                    it.position += 1
                }
            }
            number.position = newPosition
        }
        val zeroPosition = numbers.first { it.value == 0L }.position

        numbers.sortedBy { it.position }
            .filter { it.position == (1000 + zeroPosition) % (numbers.size) || it.position == (2000 + zeroPosition) % (numbers.size) || it.position == (3000 + zeroPosition) % (numbers.size) }
            .sumOf { it.value }
    }

    fun star2(input: String?) = input.toData().lines().mapIndexed { index, s ->
        Number(s.toLong() * 811589153, index.toLong())
    }.let { numbers ->
        val position: (Number) -> Long = {
            ((it.position + it.value) % (numbers.size - 1)).let { position ->
                if (position <= 0) numbers.size - 1 + position else position
            }
        }
        (1..10).forEach { _ ->

            numbers.forEach { number ->
                val newPosition = position(number)
                if (newPosition > number.position) {
                    numbers.filter { it.position > number.position && it.position <= newPosition }.onEach {
                        it.position -= 1
                    }
                } else {
                    numbers.filter { it.position < number.position && it.position >= newPosition }.onEach {
                        it.position += 1
                    }
                }
                number.position = newPosition
            }
        }
        val zeroPosition = numbers.first { it.value == 0L }.position

        numbers.sortedBy { it.position }
            .filter { it.position == (1000 + zeroPosition) % (numbers.size) || it.position == (2000 + zeroPosition) % (numbers.size) || it.position == (3000 + zeroPosition) % (numbers.size) }
            .sumOf { it.value }
    }

    private data class Number(val value: Long, var position: Long) {
        override fun toString(): String = "$value"
    }
}

fun main() {
    processStars(Day20::star1, Day20::star2)
}