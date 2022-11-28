package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day10 {

    private fun String?.toData() = this ?: data(10)

    fun star1(input: String?) = input.toData().lines().sumOf {

        it.toMutableList().let { chars ->
            var errorScore = 0
            val queue = mutableListOf<Char>()
            while (chars.isNotEmpty() && errorScore == 0) {
                chars.removeFirst().also { char ->
                    if (char.isOpening()) queue.add(char)
                    else if (!char.closes(queue.removeLast())) errorScore = char.err()
                }
            }
            errorScore
        }
    }

    fun star2(input: String?) = input
        .toData()
        .lines()
        .map {

            it.toMutableList().let { chars ->

                var lineToComplete = true
                val queue = mutableListOf<Char>()
                while (chars.isNotEmpty() && lineToComplete) {
                    chars.removeFirst().also { char ->
                        if (char.isOpening()) queue.add(char)
                        else if (!char.closes(queue.removeLast())) lineToComplete = false
                    }
                }

                if (lineToComplete) {
                    queue
                        .reversed()
                        .map { it.close() }
                        .fold(0L) { acc, c ->
                            5 * acc + c.value()
                        }
                } else 0
            }
        }.let { completeScore ->
            completeScore
                .filter { it != 0L }
                .let {
                    it.sorted()[it.size / 2]
                }
        }


    private fun Char.isOpening() = this == '('
            || this == '['
            || this == '{'
            || this == '<'

    private fun Char.err() =
        if (this == ')') 3
        else if (this == ']') 57
        else if (this == '}') 1197
        else 25137


    private fun Char.value() =
        if (this == ')') 1
        else if (this == ']') 2
        else if (this == '}') 3
        else 4

    private fun Char.closes(c: Char?) =
        c != null && (
                (this == ')' && c == '(')
                        || (this == ']' && c == '[')
                        || (this == '}' && c == '{')
                        || (this == '>' && c == '<')
                )

    private fun Char.close() =
        if (this == '(') ')'
        else if (this == '[') ']'
        else if (this == '{') '}'
        else '>'

}

fun main() {
    processStars(Day10::star1, Day10::star2)
}
