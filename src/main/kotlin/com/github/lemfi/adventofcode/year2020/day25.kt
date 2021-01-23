package com.github.lemfi.adventofcode.year2020

import java.nio.charset.Charset
import kotlin.system.measureTimeMillis

fun main() {

    measureTimeMillis {
        Day25.star(Day25.data)
            .apply { println("res: $this") }
    }.apply { println("   in ${this / 1000}s") }
}

object Day25 {

    fun star(data: String): Long {

        return data.lines().let {
            it.first().toLong() to it.last().toLong()
        }.let {
                (cardPublicKey, doorPublicKey) ->
            var tryingCardPublicKey = 1L
            var encryptionKey = 1.0

            while(tryingCardPublicKey != cardPublicKey) {
                tryingCardPublicKey = (tryingCardPublicKey * 7L) % 20201227L
                encryptionKey = (encryptionKey * doorPublicKey) % 20201227L
            }

            encryptionKey
        }.toLong()
    }

    val data: String by lazy { javaClass.getResourceAsStream("/2020/day25.txt").readAllBytes().toString(Charset.defaultCharset()) }

}

