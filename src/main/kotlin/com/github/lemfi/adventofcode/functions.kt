package com.github.lemfi.adventofcode

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun String?.data(data: String) = this ?: data

@OptIn(ExperimentalTime::class)
fun processStars(vararg star: (String?) -> Any) =

    star
        .map { measureTimedValue { it(null) } }
        .mapIndexed { index, timedValue ->
            """star${index + 1}: 
                |    res:  ${timedValue.value}
                |    time: ${timedValue.duration}
            """.trimMargin()
        }
        .joinToString("\n\n")
