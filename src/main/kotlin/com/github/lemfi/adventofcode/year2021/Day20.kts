package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day20 {

    private fun String?.toData() = this ?: data(20)

    private fun String?.prepareData() = toData()
        .split("\n\n")
        .let { (enhancement, image) ->
            image.lines().let { lines ->
                (lines.indices).flatMap { y ->
                    (lines[y].indices).mapNotNull { x ->
                        if (lines[y][x] == '#') Pixel(x, y) else null
                    }
                }
            }.toImage() to enhancement
        }

    fun star1(input: String?) = input
        .prepareData()
        .applyImageEnhancement(2)

    fun star2(input: String?) = input
        .prepareData()
        .applyImageEnhancement(50)

    private fun Pair<Image, String>.applyImageEnhancement(turns: Int) = let { (inputImage, enhancement) ->

        var image = inputImage
        var bordersOn = false
        (1..turns).map {
            with(image) {

                image = (minY - 1..maxY + 1)
                    .flatMap { y ->
                        (minX - 1..maxX + 1).map { x -> Pixel(x, y) }
                    }
                    .mapNotNull { pixel ->
                        val number = getNumber(pixel, bordersOn)
                        if (enhancement[number] == '.') null
                        else pixel
                    }
                    .toImage()
            }
            bordersOn = !bordersOn && enhancement[0] == '#'
        }
        image.nbPixel()

    }

    data class Image(private val image: Map<Int, Map<Int, List<Pixel>>>) {
        val minX: Int = image.minOf { it.key }
        val minY: Int = image.values.minOf { it.keys.minOf { it } }
        val maxX: Int = image.maxOf { it.key }
        val maxY: Int = image.values.maxOf { it.keys.maxOf { it } }

        fun nbPixel() = image.values.sumOf { it.size }

        fun getNumber(pixel: Pixel, bordersOn: Boolean) =
            (pixel.y - 1..pixel.y + 1).flatMap { y ->
                (pixel.x - 1..pixel.x + 1).map { x ->
                    if (image[x]?.get(y) != null) 1
                    else if (x in minX..maxX && y in minY..maxY) 0
                    else if (bordersOn) 1
                    else 0
                }
            }.joinToString("").toInt(2)

    }

    private fun List<Pixel>.toImage(): Image = Image(groupBy {
        it.x
    }.map { it.key to it.value.groupBy { it.y } }.toMap())

    data class Pixel(val x: Int, val y: Int)

}

processStars(Day20::star1, Day20::star2)