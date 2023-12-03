package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day03 {

    private fun String?.toData() = this ?: data(3)

    fun star1(input: String?) = input
        .toData()
        .lines()
        .toEngineData { this != '.' }
        .let { (numbers, symbols) ->
            numbers
                .filter { it.touches(symbols) }
                .map { it.number }
        }
        .sum()

    fun star2(input: String?) = input
        .toData()
        .lines()
        .toEngineData { this == '*' }
        .let { (numbers, symbols) ->
            numbers
                .asSequence()
                .mapNotNull { it.gear(symbols)?.run { it.number to this } }
                .groupBy { (_, gear) -> gear }
                .filter { (_, numbers) -> numbers.size == 2 }
                .map { (_, numbers) -> numbers.map { (number, _) -> number } }
                .sumOf { (one, two) -> one * two }
        }

    private fun List<EngineData?>.analyzeEngine() =
        asSequence()
            .filterIsInstance<EngineNumber>()
            .groupBy { it.position.y }
            .map { (_, points) ->
                points.fold(mutableListOf<EngineNumber>()) { res, point ->
                    val last = res.lastOrNull()
                    if (last != null && last.position.x + 1 == point.position.x) {
                        res
                            .dropLast(1)
                            .toMutableList()
                            .apply { add(EngineNumber(point.position, last.number.concat(point.number))) }
                    } else {
                        res.apply {
                            add(point)
                        }
                    }
                }
            }
            .flatten()
            .toList() to filterIsInstance<EngineSymbol>()
            .map { it.position }

    private fun Long.concat(l: Long) = "$this$l".toLong()
    private fun Long.length() = toString().length

    private sealed interface EngineData

    private data class EngineNumber(val position: Position, val number: Long) : EngineData
    private data class EngineSymbol(val position: Position, val symbol: Char) : EngineData

    private fun List<String>.toEngineData(symbol: Char.() -> Boolean) =
        mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                c.tryLong()?.let { EngineNumber(Position(x.toLong(), y.toLong()), it) }
                    ?: if (c.symbol()) EngineSymbol(Position(x.toLong(), y.toLong()), c) else null
            }
        }
            .flatten()
            .analyzeEngine()

    private fun Char.tryLong() = runCatching { toString().toLong() }.getOrNull()
    private fun EngineNumber.touches(symbols: List<Position>) = (position.x - number.length() + 1..position.x)
        .map { Position(it, position.y) }
        .any { it.touches(symbols) }

    private fun Position.touches(symbols: List<Position>) = symbols.any {
        y in (it.y - 1..it.y + 1) && x in (it.x - 1..it.x + 1)
    }

    private fun EngineNumber.gear(symbols: List<Position>) = (position.x - number.length() + 1..position.x)
        .map { Position(it, position.y) }
        .flatMap { it.gear(symbols) }
        .toSet()
        .ifEmpty { null }

    private fun Position.gear(symbols: List<Position>) = symbols.filter {
        y in (it.y - 1..it.y + 1) && x in (it.x - 1..it.x + 1)
    }

    private data class Position(val x: Long, val y: Long)

}

fun main() {
    processStars(Day03::star1, Day03::star2)
}
