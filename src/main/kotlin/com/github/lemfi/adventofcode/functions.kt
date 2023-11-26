package com.github.lemfi.adventofcode

import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.writeText
import kotlin.time.measureTimedValue

fun String?.data(data: String) = this ?: data

const val ANSI_RESET = "\u001B[0m"

private fun yellow(any: Any) = "\u001B[33m$any$ANSI_RESET"
private fun blue(any: Any) = "\u001B[34m$any$ANSI_RESET"
private fun underline(any: Any) = "\u001B[4m$any$ANSI_RESET"


fun processStars(vararg stars: (String?) -> Any) =

    measureTimedValue {

        if (stars.size == 2) {
            stars
                .forEachIndexed { index, function ->
                    val timedValue = measureTimedValue { function(null) }
                    println(
                        """${yellow("star${index + 1}")}: 
                                |    res:  ${blue(timedValue.value)}
                                |    time: ${timedValue.duration}
                            """.trimMargin()
                    )
                }
        } else {
            stars
                .toList()
                .windowed(2, 2)
                .forEachIndexed { dayIndex, day ->
                    println("\n${underline("Day ${dayIndex + 1}")}")
                    day.forEachIndexed { starIndex, star ->
                        val timedValue = measureTimedValue { star(null) }
                        println(
                            """    ${yellow("star${starIndex + 1}")}: 
                                    |        res:  ${blue(timedValue.value)}
                                    |        time: ${timedValue.duration}
                                """.trimMargin()
                        )
                    }
                }
        }
    }.let {
        println(
            """
                
                
                ${underline("TOTAL time:")}
                ${it.duration}
            """.trimIndent()
        )
    }

private fun classPattern(year: Int, day: Int) = """
        package com.github.lemfi.adventofcode.year$year

        import com.github.lemfi.adventofcode.processStars

        object Day${day.toString().padStart(2, '0')} {

            private fun String?.toData() = this ?: data($day)

            fun star1(input: String?) = 0

            fun star2(input: String?) = 0
        }

        fun main() {
            processStars(Day${day.toString().padStart(2, '0')}::star1, Day${day.toString().padStart(2, '0')}::star2)
        }

    """.trimIndent()

private fun testPattern(year: Int, day: Int) = """
        package com.github.lemfi.adventofcode.year$year

        import org.junit.jupiter.api.Assertions
        import org.junit.jupiter.api.Test

        class Day${day.toString().padStart(2, '0')}Test {

            @Test
            fun star1() {
                Assertions.assertEquals(0, Day${day.toString().padStart(2, '0')}.star1(null))
            }

            @Test
            fun star2() {
                Assertions.assertEquals(0, Day${day.toString().padStart(2, '0')}.star2(null))
            }
        }
    """.trimIndent()

private fun dataClassPattern(year: Int) = """
        package com.github.lemfi.adventofcode.year$year

        import java.nio.charset.Charset

        fun data(day: Int): String = runCatching {
            object {}.javaClass.getResourceAsStream("/$year/day${'$'}{day.toString().padStart(2, '0')}.txt")?.readAllBytes()
                ?.toString(Charset.defaultCharset())
        }.getOrNull() ?: throw IllegalAccessError("no data for day ${'$'}day")
    """.trimIndent()

private fun playPattern(year: Int) = """
        package com.github.lemfi.adventofcode.year$year

        import com.github.lemfi.adventofcode.processStars

        fun main() {

            processStars(
                Day01::star1,
                Day01::star2,
                Day02::star1,
                Day02::star2,
                Day03::star1,
                Day03::star2,
                Day04::star1,
                Day04::star2,
                Day05::star1,
                Day05::star2,
                Day06::star1,
                Day06::star2,
                Day07::star1,
                Day07::star2,
                Day08::star1,
                Day08::star2,
                Day09::star1,
                Day09::star2,
                Day10::star1,
                Day10::star2,
                Day11::star1,
                Day11::star2,
                Day12::star1,
                Day12::star2,
                Day13::star1,
                Day13::star2,
                Day14::star1,
                Day14::star2,
                Day15::star1,
                Day15::star2,
                Day16::star1,
                Day16::star2,
                Day17::star1,
                Day17::star2,
                Day18::star1,
                Day18::star2,
                Day19::star1,
                Day19::star2,
                Day20::star1,
                Day20::star2,
                Day21::star1,
                Day21::star2,
                Day22::star1,
                Day22::star2,
                Day23::star1,
                Day23::star2,
                Day24::star1,
                Day24::star2,
                Day25::star1,
                Day25::star2,
            )
        }
    """.trimIndent()

@Suppress("unused")
private fun createYear(year: Int) {

    Paths.get("./src/main/kotlin/com/github/lemfi/adventofcode/year$year").apply {
        createDirectories()
    }
    Paths.get("./src/main/resources/$year").apply {
        createDirectories()
    }
    Paths.get("./src/test/kotlin/com/github/lemfi/adventofcode/year$year").apply {
        createDirectories()
    }

    (1..25).forEach { day ->
        Paths.get(
            "./src/main/kotlin/com/github/lemfi/adventofcode/year$year/Day${
                day.toString().padStart(2, '0')
            }.kt"
        )
            .apply {
                createFile()
                writeText(classPattern(year, day))
            }

        Paths.get("./src/main/resources/$year/day${day.toString().padStart(2, '0')}.txt").apply {
            createFile()
        }

        Paths.get(
            "./src/test/kotlin/com/github/lemfi/adventofcode/year$year/Day${
                day.toString().padStart(2, '0')
            }Test.kt"
        )
            .apply {
                createFile()
                writeText(testPattern(year, day))
            }
    }
    Paths.get("./src/main/kotlin/com/github/lemfi/adventofcode/year$year/functions.kt")
        .apply {
            createFile()
            writeText(dataClassPattern(year))
        }
    Paths.get("./src/main/kotlin/com/github/lemfi/adventofcode/year$year/Play.kt")
        .apply {
            createFile()
            writeText(playPattern(year))
        }

}