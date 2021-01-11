package com.github.lemfi.adventofcode.year2020

import com.github.lemfi.adventofcode.year2020.Day3.walk
import java.nio.charset.Charset
import kotlin.math.max

fun main() {

    Day3.star1(Day3.data).apply { println(this) }

    Day3.star2(Day3.data).apply { println(this) }
}

object Day3 {

    fun star1(data: String) = data.lines()
            .map { it.toCharArray().map { if (it == '.') 0L else 1L } }
            .walk(3)

    fun star2(data: String) = data.lines()
            .map { it.toCharArray().map { if (it == '.') 0L else 1L } }
            .let { it.walk(1) * it.walk(3) * it.walk(5) * it.walk(7) * it.walk(1, 2) }

    private fun List<List<Long>>.walk(stepX: Int, stepY: Int = 1) =
            filterIndexed { index, line -> index % stepY == 0 }
                    .fold(0L to 0) { (sum, x), line -> sum + line.drop(x).first() to (x + stepX) % line.size }.first

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day3.txt").readAllBytes().toString(Charset.defaultCharset()) }

}