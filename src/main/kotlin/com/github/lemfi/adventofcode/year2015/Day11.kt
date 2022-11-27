package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day11 {

    private fun String?.toData() = this ?: data(11)

    fun star1(input: String?) = input.toData().findNextValidPassword()

    fun star2(input: String?) = input.toData().findNextValidPassword().findNextValidPassword()

    private fun String.next(index: Int): String =
        take(index) + this[index].next() + (1 until length - index).joinToString("") { "_" }

    private fun Char.next(excludeForbiddenChars: Boolean = true): Char {
        var next = alphabet[this]
        while (excludeForbiddenChars && forbiddenChars.contains(next)) {
            next = alphabet[next]
        }
        return next ?: error("no more letter available")
    }

    private val forbiddenChars = listOf('i', 'o', 'l')

    private fun String.findNextValidPassword(): String {
        val index = forbiddenChars
            .map { this.indexOf(it) }
            .filterNot { it == -1 }
            .minOrNull()
        var nextPassword =
            if (index != null) take(index) + this[index].next() + (1 until length - index).joinToString("") { "a" } else this
        do {
            nextPassword = level0(nextPassword)
        } while (!nextPassword.isValidPassword())
        return nextPassword
    }

    private fun changePassword(password: String, index: Int): String {
        var nextPassword = password
        runCatching {
            do {
                nextPassword = m[index]!!.invoke(nextPassword)
            } while (!nextPassword.isValidPassword())
        }.onFailure {
            nextPassword = nextPassword.next(index)
        }
        return nextPassword
    }

    private fun level0(password: String) =
        changePassword(password, 0)

    private fun level1(password: String) =
        changePassword(password, 1)

    private fun level2(password: String) =
        changePassword(password, 2)

    private fun level3(password: String) =
        changePassword(password, 3)

    private fun level4(password: String) =
        changePassword(password, 4)

    private fun level5(password: String) =
        changePassword(password, 5)

    private fun level6(password: String) =
        changePassword(password, 6)

    private fun level7(password: String) = password.next(7)

    private val m =
        mapOf(6 to ::level7, 5 to ::level6, 4 to ::level5, 3 to ::level4, 2 to ::level3, 1 to ::level2, 0 to ::level1)

    private fun String.isValidPassword() =
        has3ConscutiveChars()
                && doesNotContainForbiddenChar()
                && hasDoubledChar()

    private fun String.has3ConscutiveChars() =
        toList().windowed(3, 1).any { it[2] == it[1].next(false) && it[1] == it[0].next(false) }

    private fun String.doesNotContainForbiddenChar() =
        toList().none { it == 'o' || it == 'i' || it == 'l' || it == '_' }

    private fun String.hasDoubledChar(): Boolean {
        val i1 = toList().windowed(2, 1).indexOfFirst { it.first() == it.last() }
        val i2 = toList().windowed(2, 1).indexOfLast { it.first() == it.last() }

        return i2 - i1 > 1
    }

    private val alphabet = listOf(
        '_',
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z',
    )
        .windowed(2, 1)
        .associate { it.first() to it.last() }
}

fun main() {
    processStars(Day11::star1, Day11::star2)
}
