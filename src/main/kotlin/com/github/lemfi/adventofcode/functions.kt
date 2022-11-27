package com.github.lemfi.adventofcode

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun String?.data(data: String) = this ?: data

const val ANSI_RESET = "\u001B[0m"

private fun yellow(any: Any) = "\u001B[33m$any$ANSI_RESET"
private fun blue(any: Any) = "\u001B[34m$any$ANSI_RESET"
private fun underline(any: Any) = "\u001B[4m$any$ANSI_RESET"


@OptIn(ExperimentalTime::class)
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
