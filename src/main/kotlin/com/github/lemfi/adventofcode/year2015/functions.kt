package com.github.lemfi.adventofcode.year2015

import java.nio.charset.Charset

fun data(day: Int) = runCatching {
    object {}.javaClass.getResourceAsStream("/2015/day$day.txt")?.readAllBytes()
        ?.toString(Charset.defaultCharset())
}
    .getOrNull() ?: throw IllegalAccessError("no data for day $day")