package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day5 {

    private fun String?.toData() = this ?: data(5)

    private fun String.vowel() = count { it == 'a' || it == 'e' || it == 'i' || it == 'o' || it == 'u' } >= 3

    private fun String.repeatedChar() = (windowed(2, 1).count { s -> s[0] == s[1] }) > 0
    private fun String.tripledChars() = windowed(3, 1).filter { s -> s[0] == s[1] && s[1] == s[2] }
    private fun String.excludedString() = !contains("ab") && !contains("cd") && !contains("pq") && !contains("xy")

    class StringIterator(val string: String) {
        private var index = -1
        fun next() = (index++).let { string.drop(index).take(2) }
        fun tail() = string.drop(index + 2)
        fun hasNext() = string.length > index + 2
    }

    fun String.repeatedWithoutOverlap() = StringIterator(this).let { iterator ->
        var found = false
        while (!found && iterator.hasNext()) {
            found = iterator.next().let { iterator.tail().contains(it) }
        }
        found
    }


    private fun String.repeatedBetween() =
        filterIndexed { index, _ -> index % 2 == 0 }.repeatedChar() || filterIndexed { index, _ -> index % 2 != 0 }.repeatedChar()


    fun star1(input: String?) = input.toData().lines()
        .count { it.vowel() && it.repeatedChar() && it.excludedString() }

    fun star2(input: String?) = input.toData().lines()
        .count { it.repeatedWithoutOverlap() && it.repeatedBetween() }
}

processStars(Day5::star1, Day5::star2)

