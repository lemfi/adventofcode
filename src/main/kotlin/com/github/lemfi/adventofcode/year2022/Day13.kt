package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day13 {

    private fun String?.toData() = this ?: data(13)

    fun star1(input: String?) = input
        .toData()
        .split("\n\n")
        .mapIndexed { index, pairs ->
            pairs
                .lines()
                .let { (left, right) ->
                    if (left.toElement() < right.toElement()) index + 1 else 0
                }
        }.sum()

    fun star2(input: String?) = input
        .toData()
        .replace("\n\n", "\n")
        .let { "$it\n[[6]]\n[[2]]" }
        .lines()
        .map { it.toElement() }
        .sorted()
        .mapIndexed { index, element ->
            when (element.toString()) {
                "[[6]]", "[[2]]" -> index + 1
                else -> 1
            }
        }.reduce { acc, index -> acc * index }


    private fun String.toElement(): Element =
        when {
            startsWith("[") -> getGroup().toListElement()
            else -> NumberElement(getNumber())
        }

    private fun String.toListElement(): ListElement = ListElement(
        mutableListOf<Element>()
            .apply {

                var toRead = this@toListElement
                while (toRead.isNotBlank()) {
                    when {
                        toRead.startsWith("[") -> toRead.getGroup()
                            .toListElement()
                            .also {
                                add(it)
                                toRead = toRead.substringAfter(it.toString())
                            }
                        toRead.startsWithNumber() -> NumberElement(toRead.getNumber()).let {
                            add(it)
                            toRead = toRead.substringAfter(it.toString())
                        }
                        else -> toRead = toRead.drop(1)
                    }
                }
            }
    )

    private fun String.startsWithNumber() = runCatching { first().digitToInt() }.getOrNull() != null
    private fun Char.isNumber() = runCatching { digitToInt() }.getOrNull() != null


    private sealed interface Element: Comparable<Element> {

        override fun compareTo(other: Element): Int
    }

    private data class NumberElement(val value: Int) : Element {
        override fun toString() = "$value"

        override fun compareTo(other: Element): Int =
            if (other is NumberElement) value.compareTo(other.value)
            else ListElement(listOf(this)).compareTo(other)
    }

    private data class ListElement(val value: List<Element>) : Element {
        override fun toString() = "[${value.joinToString(",") { it.toString() }}]"

        override fun compareTo(other: Element): Int =
            when(other) {
                is NumberElement -> compareTo(ListElement(listOf(other)))
                is ListElement -> {
                    value.foldIndexed(0) { index, acc, subElement ->
                        when (acc) {
                            1, -1 -> acc
                            else -> runCatching { subElement.compareTo(other.value[index]) }.getOrNull() ?: 1
                        }
                    }.let { comparison ->
                        if (comparison == 0) value.size.compareTo(other.value.size) else comparison
                    }
                }
            }
    }

    private fun String.getGroup(open: Char = '[', close: Char = ']'): String {
        if (open == close) return drop(1).takeWhile { it != close }
        var nbP = 0
        return with(toList()) {
            takeWhile {
                if (it == open) nbP++
                if (it == close) nbP--
                nbP > 0
            }.drop(1)
        }.joinToString("")
    }

    private fun String.getNumber() = with(toList()) {
        takeWhile {
            it.isNumber()
        }
    }.joinToString("").toInt()

}

fun main() {
    processStars(Day13::star1, Day13::star2)
}