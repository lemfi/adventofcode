package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day10 {

    private fun String?.toData() = (this ?: data(10)).toList()

    fun star1(input: String?) = iter(input, 40)

    fun star2(input: String?) = iter(input, 50)

    fun group(data: List<Char>): List<Char> {
        val res = mutableListOf<Pair<Char, Int>>()
        var index = 0
        var occ = 0
        var current: Char
        while (index < data.size) {
            do {
                current = data[index]
                occ += 1
                index += 1
            } while (index < data.size && current == data[index])
            res.add(current to occ)
            occ = 0
        }

        return res.fold(mutableListOf()) { acc, (c, n) -> acc.apply { add(n.digitToChar()); add(c) } }
    }

    private fun iter(input: String?, nbIter: Int): Int {
        var data = input.toData()
        repeat(nbIter) {
            data = group(data)
        }
        return data.size
    }
}

fun main() {
    processStars(Day10::star1, Day10::star2)
}
