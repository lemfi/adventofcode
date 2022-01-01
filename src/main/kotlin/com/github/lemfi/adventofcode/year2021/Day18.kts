package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars
import kotlin.math.max

object Day18 {

    private fun String?.toData() = this ?: data(18)

    private fun String?.prepareData() = toData().lines().map { line ->
        line.toList().filterNot { it == ',' }.toElement()
    }

    fun star1(input: String?) = input
        .prepareData()
        .reduce { acc, element -> PairElement(acc, element).reduce() }
        .magnitude()

    fun star2(input: String?) = input
        .prepareData()
        .let { elements ->

            elements.fold(Long.MIN_VALUE) { max, element1 ->
                elements.fold(max) { tryMax, element2 ->
                    max(
                        max(
                            PairElement(element1.clone(), element2.clone()).reduce().magnitude(),
                            PairElement(element2.clone(), element1.clone()).reduce().magnitude()
                        ), tryMax
                    )
                }
            }
        }

    sealed class Element {

        abstract fun isReduced(): Boolean
        abstract fun depth(): Int
        abstract fun reduce(): Element
        abstract fun shouldSplit(): Boolean
        abstract fun shouldExplode(): Boolean

        abstract fun getRight(): NumberElement
        abstract fun getLeft(): NumberElement
        abstract fun magnitude(): Long

        abstract fun clone(): Element
    }

    data class NumberElement(var value: Long) : Element() {
        override fun isReduced() = true
        override fun depth(): Int = 0
        override fun reduce() = this
        override fun shouldSplit() = value >= 10
        override fun shouldExplode() = false

        override fun toString() = "$value"

        override fun getRight() = this
        override fun getLeft() = this
        override fun magnitude() = value

        override fun clone() = copy()
    }

    data class PairElement(var left: Element, var right: Element) : Element() {
        override fun clone() = PairElement(left.clone(), right.clone())

        override fun isReduced() = (!shouldExplode() && !shouldSplit())

        override fun shouldExplode() = depth() >= 4

        override fun shouldSplit() = left.shouldSplit() || right.shouldSplit()

        override fun magnitude() = left.magnitude() * 3 + right.magnitude() * 2

        override fun depth() =
            max(
                if (left is NumberElement) 0 else 1 + left.depth(),
                if (right is NumberElement) 0 else 1 + right.depth()
            )

        override fun toString() = "[${left},${right}]"

        override fun reduce(): PairElement {
            while (!isReduced()) {
                if (shouldExplode()) explode(depth())
                else if (shouldSplit()) split()
            }
            return this
        }

        override fun getRight() = right.getRight()
        override fun getLeft() = left.getLeft()
    }

    fun PairElement.split() {
        let { (left, right) ->

            if (left.shouldSplit()) {
                when (left) {
                    is NumberElement -> this@split.left =
                        PairElement(
                            NumberElement(left.value / 2),
                            NumberElement(left.value / 2 + left.value % 2)
                        )
                    is PairElement -> left.split()
                }
            } else {
                when (right) {
                    is NumberElement -> this@split.right = PairElement(
                        NumberElement(right.value / 2),
                        NumberElement(right.value / 2 + right.value % 2)
                    )
                    is PairElement -> right.split()
                }
            }
        }
    }

    fun PairElement.explode(
        level: Int,
        firstLeftRegularNumber: NumberElement? = null,
        firstRightRegularNumber: NumberElement? = null,
        parent: PairElement? = null,
    ) {
        let { (left, right) ->

            if (level == 0) {
                apply {
                    firstRightRegularNumber?.let { it.value += (right as NumberElement).value }
                    firstLeftRegularNumber?.let { it.value += (left as NumberElement).value }

                    parent?.also { parent ->
                        if (parent.left === this) parent.left = NumberElement(0)
                        if (parent.right === this) parent.right = NumberElement(0)
                    }
                }
            } else if (left.depth() >= level - 1 && left is PairElement) {
                left.explode(
                    level = level - 1,
                    firstLeftRegularNumber = firstLeftRegularNumber,
                    firstRightRegularNumber = right.getLeft(),
                    parent = this,
                )
            } else if (right.depth() >= level - 1 && right is PairElement) {
                right.explode(
                    level = level - 1,
                    firstLeftRegularNumber = left.getRight(),
                    firstRightRegularNumber = firstRightRegularNumber,
                    parent = this,
                )
            } else {
                explode(
                    level = 0,
                    firstLeftRegularNumber = firstLeftRegularNumber,
                    firstRightRegularNumber = firstRightRegularNumber,
                    parent = this,
                )
            }
        }
    }

    private fun List<Char>.toElement(): Element =
        if (first() == '[') toPair()
        else if (size == 1) NumberElement(first().digitToInt().toLong())
        else PairElement(take(1).toElement(), drop(1).toElement())

    private fun List<Char>.toPair(): Element {
        return run {
            if (first() == '[') {
                var nbOpeningPair = 0
                val firstPart = takeWhile {
                    if (it == '[') nbOpeningPair++
                    if (it == ']') nbOpeningPair--
                    nbOpeningPair > 0
                }.drop(1) // closing ']' is not taken, just remove opening '['
                val secondPart = drop(2).drop(firstPart.size) // 2 = opening / closing []

                if (secondPart.isEmpty()) firstPart.toElement()
                else PairElement(firstPart.toElement(), secondPart.toElement())

            } else {
                PairElement(take(1).toElement(), drop(1).toElement())
            }
        }
    }
}


processStars(Day18::star1, Day18::star2)