package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day7 {

    private fun String?.toData() = this ?: data(7)

    fun star1(input: String?) = input
        .prepare()
        .analyzeDirs()
        .let { dirs ->
            dirs
                .map { it.size(dirs) }
                .filter { it <= 100000 }
                .sum()
        }

    fun star2(input: String?) = input
        .prepare()
        .analyzeDirs()
        .let { dirs ->
            with(dirs.first { it.name == "/" }.size(dirs) + 30000000 - 70000000) {
                dirs
                    .map { it.size(dirs) }
                    .filter { it > this }
                    .min()
            }
        }

    private fun List<Line>.analyzeDirs(): MutableSet<Dir> {

        var currentDir: Dir? = Dir("/")
        return mutableSetOf(currentDir!!).also { dirs ->

            forEach { line ->
                when (line) {
                    is File -> currentDir?.files?.add(line)
                    is Goto -> currentDir =
                        if (line.dir == "..") {
                            currentDir?.parent
                        } else {
                            Dir(line.dir, currentDir).apply { dirs.add(this) }
                        }
                }
            }
        }
    }

    private fun String?.prepare() = toData()
        .lines()
        .drop(1)
        .mapNotNull {
            when {
                it.startsWith("$ cd") -> Goto(it.substringAfter("cd "))
                !it.startsWith("$") && !it.startsWith("dir") -> File(it.split(" ").first().toInt())
                else -> null
            }
        }


    sealed interface Line
    data class Goto(val dir: String) : Line
    data class File(val size: Int) : Line

    data class Dir(val name: String, val parent: Dir? = null) {

        val files: MutableList<File> = mutableListOf()

        fun size(dirs: Collection<Dir>): Int =
            files.sumOf { it.size } + dirs.filter { it.parent == this }.sumOf { it.size(dirs) }
    }
}

fun main() {
    processStars(Day7::star1, Day7::star2)
}