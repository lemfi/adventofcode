package com.github.lemfi.adventofcode.year2023

import com.github.lemfi.adventofcode.processStars

object Day05 {

    private fun String?.toData() = this ?: data(5)

    fun star1(input: String?) = input
        .toData()
        .split("\n\n")
        .let {

            val link = it.buildLink()

            it
                .toSeedsStar1()
                .minOf { link.getDestination(it.number) }
        }

    fun star2(input: String?) = input
        .toData()
        .split("\n\n")
        .let {
            val link = it.buildLink()
            it
                .toSeedsStar2()
                .fold(Long.MAX_VALUE) { res, seed ->
                    (seed.start until seed.start + seed.range)
                        .fold(res) { acc, l ->
                            val dest = link.getDestination(l)
                            if (dest > acc) acc else dest
                        }
                }
        }


    private fun List<String>.toSeedsStar1() = first { it.startsWith("seeds") }
        .substringAfter(": ")
        .let { Regex("""\d+""").findAll(it).map { it.value.toLong() }.toList() }
        .map { SeedStar1(it) }

    private fun List<String>.toSeedsStar2() =
        first { it.startsWith("seeds") }
            .substringAfter(": ")
            .let { Regex("""\d+""").findAll(it).map { it.value.toLong() }.toList() }
            .let {
                it.windowed(2, 2).map { (one, two) ->
                    SeedStar2(one, two)
                }
            }

    private fun List<String>.buildLink(): Link {
        val humidityToLocation = first { it.startsWith("humidity-to-location map:") }
            .buildLink(null)
        val temperatureToHumidity = first { it.startsWith("temperature-to-humidity map:") }
            .buildLink(humidityToLocation)
        val lightToTemperature = first { it.startsWith("light-to-temperature map:") }
            .buildLink(temperatureToHumidity)
        val waterToLight = first { it.startsWith("water-to-light map:") }
            .buildLink(lightToTemperature)
        val fertilizerToWater = first { it.startsWith("fertilizer-to-water map:") }
            .buildLink(waterToLight)
        val soilToFertilizer = first { it.startsWith("soil-to-fertilizer map:") }
            .buildLink(fertilizerToWater)
        val seedToSoil = first { it.startsWith("seed-to-soil map:") }
            .buildLink(soilToFertilizer)

        return seedToSoil
    }

    private fun String.buildLink(next: Link?) = Link(
        split("\n")
            .drop(1)
            .map {
                Regex("""\d+""").findAll(it)
                    .map { it.value.toLong() }
                    .toList()
                    .let { (destination, source, range) -> LinkRange(source, destination, range) }
            },
        next
    )

    private fun List<LinkRange>.getDestination(source: Long) =
        firstNotNullOfOrNull { it.getDestination(source) } ?: source

    data class Link(val data: List<LinkRange>, val next: Link?) {

        val sorted = data.sortedBy { it.source }
        val range = sorted.first().source..<sorted.last().source + sorted.last().range

        fun getDestination(source: Long): Long =
            if (source in range) {
                next?.getDestination(sorted.getDestination(source)) ?: sorted.getDestination(source)
            } else {
                next?.getDestination(source) ?: source
            }

    }

    data class LinkRange(
        val source: Long,
        val destination: Long,
        val range: Long,
    ) {

        fun getDestination(mySource: Long) =
            if (mySource in source until source + range) {
                mySource + (destination - source)
            } else {
                null
            }
    }

    data class SeedStar1(val number: Long)
    data class SeedStar2(val start: Long, val range: Long)
}

fun main() {
    processStars(Day05::star1, Day05::star2)
}
