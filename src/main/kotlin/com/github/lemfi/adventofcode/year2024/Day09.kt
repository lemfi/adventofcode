package com.github.lemfi.adventofcode.year2024

import com.github.lemfi.adventofcode.processStars

object Day09 {

    private fun String?.toData() = this ?: data(9)

    fun star1(input: String?) = input
        .parse()
        .let { blocks ->
            var lastFree = 0
            blocks
                .reversed()
                .onEach { block ->
                    if (!block.free) {
                        var start = lastFree
                        while (start < blocks.size) {
                            val freeBlock = blocks[start++]
                            if (freeBlock.position >= block.position) break
                            if (freeBlock.free) {
                                lastFree = freeBlock.position
                                freeBlock.apply {
                                    free = false
                                    id = block.id
                                }
                                block.apply {
                                    free = true
                                    id = -1
                                }
                                break
                            }
                        }
                    }
                }
                .sortedBy { it.position }
                .mapIndexed { index, block ->
                    if (!block.free) index * block.id else 0
                }
                .sum()
        }

    fun star2(input: String?) = input
        .parse()
        .let { blocks ->
            var lastBlock = Long.MAX_VALUE
            blocks
                .reversed()
                .onEach { block ->
                    if (!block.free && block.id < lastBlock) {
                        lastBlock = block.id
                        var start = -1
                        while (start < blocks.size) {
                            val freeBlock = blocks[++start]

                            if (freeBlock.position >= block.position) break

                            if (freeBlock.free) {
                                if (freeBlock.size > block.size) {
                                    blocks.slice(start + block.size until start + freeBlock.size)
                                        .forEach { remainingBlock ->
                                            remainingBlock.size = freeBlock.size - block.size
                                        }
                                }

                                if (freeBlock.size >= block.size) {
                                    blocks.slice(start until start + block.size).forEach { occupiedBlock ->
                                        occupiedBlock.apply {
                                            free = false
                                            id = block.id
                                            size = 1
                                        }
                                    }

                                    blocks.slice(block.position - block.size + 1..block.position).forEach { prevBlock ->
                                        prevBlock.apply {
                                            free = true
                                            id = -1
                                        }
                                    }
                                    break
                                }
                            }
                        }
                    }
                }
                .sortedBy { it.position }
                .mapIndexed { index, block ->
                    if (!block.free) index * block.id else 0
                }
                .sum()
        }

    private fun String?.parse() = toData()
        .mapIndexed { index, c ->
            if (index % 2 == 0) (1..c.digitToInt()).map {
                Block(
                    index / 2L,
                    free = false,
                    position = index,
                    size = c.digitToInt()
                )
            }
            else (1..c.digitToInt()).map { Block(-1, free = true, position = index, size = c.digitToInt()) }
        }
        .flatten()
        .mapIndexed { index, c ->
            c.apply { position = index }
        }
}

data class Block(
    var id: Long,
    var free: Boolean,
    var position: Int,
    var size: Int,
)


fun main() {
    processStars(Day09::star1, Day09::star2)
}