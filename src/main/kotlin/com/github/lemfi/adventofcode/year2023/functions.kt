package com.github.lemfi.adventofcode.year2023

import java.nio.charset.Charset

fun data(day: Int): String = runCatching {
    object {}.javaClass.getResourceAsStream("/2023/day${day.toString().padStart(2, '0')}.txt")?.readAllBytes()
        ?.toString(Charset.defaultCharset())
}.getOrNull() ?: throw IllegalAccessError("no data for day $day")