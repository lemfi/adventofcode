package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day12 {

    private fun String?.toData() = this ?: data(12)

    fun star1(input: String?) = input
        .toData()
        .split("\"", "{", "}", "[", "]", ",", ":")
        .mapNotNull { runCatching { it.toInt() }.getOrNull() }
        .sum()

    fun star2(input: String?) = input
        .toData()
        .trim()
        .toJson()
        .sum()

    sealed interface JsonElement
    data class JsonArray(val elements: List<JsonElement>) : JsonElement
    data class JsonObject(val elements: Map<String, JsonElement>) : JsonElement
    data class JsonString(val value: String) : JsonElement
    data class JsonNumber(val value: Int) : JsonElement

    private fun String.toJson(): JsonElement =
        when {
            startsWith("[") -> toJsonArray().first
            startsWith("{") -> toJsonObject().first
            else -> error("invalid json")
        }

    private fun String.toJsonObject() = getGroup('{', '}').let { JsonObject(it.toObjectElements()) to it }
    private fun String.toJsonArray() = getGroup('[', ']').let { JsonArray(it.toArrayElements()) to it }

    private fun String.toJsonNumber() =
        " $this".getGroup(' ', ',').let { JsonNumber(it.toInt()) to it }

    private fun String.toJsonString() = getGroup('"', '"').let { JsonString(it) to it }

    private fun String.toJsonElement() =
        when {
            startsWith("\"") -> toJsonString()

            startsWith("1") || startsWith("2") || startsWith("3") || startsWith("4") || startsWith("5") || startsWith("6")
                    || startsWith("7") || startsWith("8") || startsWith("9") || startsWith("0") || startsWith("-") ->
                toJsonNumber()

            startsWith("[") -> toJsonArray()

            startsWith("{") -> toJsonObject()

            else -> null
        }

    private fun String.toArrayElements(): List<JsonElement> =
        toJsonElement()?.let { (json, s) ->
            listOf(
                json,
                *substringAfter(s)
                    .removePrefix("\"").trim()
                    .removePrefix("}").trim()
                    .removePrefix("]").trim()
                    .removePrefix(",").trim()
                    .toArrayElements().toTypedArray()
            )
        } ?: emptyList()


    private fun String.toObjectElements(): Map<String, JsonElement> {

        var toParse = this
        val attributes = mutableListOf<Pair<String, JsonElement>>()
        while (toParse.isNotBlank()) {
            toParse
                .trim()
                .getGroup('"', '"')
                .also { key ->
                    toParse.drop(key.length + 2)
                        .trim()
                        .removePrefix(":")
                        .trim()
                        .toJsonElement()
                        ?.also { (json, s) ->
                            attributes.add(key to json)
                            toParse = toParse.substringAfter(s)
                                .removePrefix("\"").trim()
                                .removePrefix("}").trim()
                                .removePrefix("]").trim()
                                .removePrefix(",").trim()
                        }
                }
        }
        return attributes.toMap()
    }

    private fun String.getGroup(open: Char, close: Char): String {
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

    private fun JsonElement.sum(): Int =
        when (this) {
            is JsonNumber -> value
            is JsonString -> 0
            is JsonArray -> elements.sumOf { it.sum() }
            is JsonObject -> with(elements.values) {
                if (any { it is JsonString && it.value == "red" }) 0 else sumOf { it.sum() }
            }
        }
}


fun main() {
    processStars(Day12::star1, Day12::star2)
}
