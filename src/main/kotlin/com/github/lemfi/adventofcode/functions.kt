package com.github.lemfi.adventofcode

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun String?.data(data: String) = this ?: data

const val ANSI_RESET = "\u001B[0m"

private fun yellow(any: Any) = "\u001B[33m$any$ANSI_RESET"
private fun blue(any: Any) = "\u001B[34m$any$ANSI_RESET"


@OptIn(ExperimentalTime::class)
fun processStars(vararg star: (String?) -> Any) =

    star
        .map { measureTimedValue { it(null) } }
        .mapIndexed { index, timedValue ->
            """${yellow("star${index + 1}")}: 
                |    res:  ${blue(timedValue.value)}
                |    time: ${timedValue.duration}
            """.trimMargin()
        }
        .joinToString("\n\n")
