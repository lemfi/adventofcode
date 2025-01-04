package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day25 {

    private fun String?.toData() = this ?: data(25)

    fun star1(input: String?) = input.toData().split("\n\n")
        .map {
            it.lines()
                .let { lines ->
                    when {
                        lines.first().all { c -> c == '#' } -> Lock(lines)
                        else -> Key(lines)
                    }
                }
        }
        .run { filterIsInstance<Lock>() to filterIsInstance<Key>() }
        .let { (locks, keys) ->
            locks.sumOf { lock ->
                keys.count { key ->
                    key.h1 + lock.h1 < 6 && key.h2 + lock.h2 < 6 && key.h3 + lock.h3 < 6 && key.h4 + lock.h4 < 6 && key.h5 + lock.h5 < 6
                }
            }
        }

    fun star2(input: String?) = 0

    sealed interface Device {
        val h1: Int
        val h2: Int
        val h3: Int
        val h4: Int
        val h5: Int
    }

    data class Lock(
        override val h1: Int,
        override val h2: Int,
        override val h3: Int,
        override val h4: Int,
        override val h5: Int,
    ) : Device {
        constructor(lines: List<String>) : this(
            lines.indexOfFirst { it[0] == '.' } - 1,
            lines.indexOfFirst { it[1] == '.' } - 1,
            lines.indexOfFirst { it[2] == '.' } - 1,
            lines.indexOfFirst { it[3] == '.' } - 1,
            lines.indexOfFirst { it[4] == '.' } - 1,
        )
    }

    data class Key(
        override val h1: Int,
        override val h2: Int,
        override val h3: Int,
        override val h4: Int,
        override val h5: Int,
    ) : Device {
        constructor(lines: List<String>) : this(
            6 - lines.indexOfFirst { it[0] == '#' },
            6 - lines.indexOfFirst { it[1] == '#' },
            6 - lines.indexOfFirst { it[2] == '#' },
            6 - lines.indexOfFirst { it[3] == '#' },
            6 - lines.indexOfFirst { it[4] == '#' },
        )
    }
}

fun main() {
    processStars(Day25::star1, Day25::star2)
}
