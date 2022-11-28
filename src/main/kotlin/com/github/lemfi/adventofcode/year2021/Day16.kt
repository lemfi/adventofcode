package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day16 {

    private const val TYPE_LITERAL = 4
    private const val PREFIX_LENGTH = 6
    private const val PACKET_TOTAL_LENGTH_LENGTH = 15
    private const val PACKET_NB_SUB_PACKET_LENGTH = 11
    private const val LENGTH_TYPE_ID_LENGTH = 1
    private const val TOTAL_LENGTH_TYPE_ID = 0

    private fun String?.toData() = this ?: data(16)

    private fun String?.prepareData() =
        toData()
            .toList()
            .joinToString("") {
                it.toString().toInt(16).toString(2).padStart(4, '0')
            }

    fun star1(input: String?) = input
        .prepareData()
        .extractPackets()
        .sumOf { it.versionSum() }

    fun star2(input: String?) = input
        .prepareData()
        .extractPackets()
        .sumOf { it.value() }

    private fun String.extractPackets(limit: Int = Int.MAX_VALUE): MutableList<Packet> =
        mutableListOf<Packet>().apply {
            var binary = this@extractPackets
            @Suppress("NAME_SHADOWING") var limit = limit
            while (limit > 0 && binary.isNotBlank() && !binary.all { it == '0' }) {
                limit--
                addAll(binary.toPackets()
                    .apply { binary = binary.drop(sumOf { it.length() }) }
                )
            }
        }

    private val String.version: Int get() = take(3).toInt(2)
    private val String.typeId: Int get() = drop(3).take(3).toInt(2)
    private val String.withoutPrefix: String get() = drop(6)

    private fun String.toPackets(): List<Packet> {

        return if (typeId == TYPE_LITERAL) {
            withoutPrefix.extractLiteral(version)
        } else {
            withoutPrefix.extractOperation(version, typeId)
        }
    }

    private fun String.extractOperation(version: Int, typeId: Int) = run {
        val lengthTypeId = first().digitToInt()
        if (lengthTypeId == TOTAL_LENGTH_TYPE_ID) {
            drop(1).extractTotalLengthPackets(version, typeId)
        } else {
            drop(1).extractNbSubPackets(version, typeId)
        }
    }

    private fun String.extractNbSubPackets(version: Int, typeId: Int) = run {
        val nbSubPackets = take(PACKET_NB_SUB_PACKET_LENGTH).toInt(2)
        with(drop(PACKET_NB_SUB_PACKET_LENGTH)) {
            toOperatorPacket(
                version = version,
                typeId = typeId,
                packetLength = PREFIX_LENGTH + LENGTH_TYPE_ID_LENGTH + PACKET_NB_SUB_PACKET_LENGTH,
                limit = nbSubPackets,
            )
        }
    }

    private fun String.extractTotalLengthPackets(version: Int, typeId: Int) = run {
        val subPacketsLength = take(PACKET_TOTAL_LENGTH_LENGTH).toInt(2)
        with(drop(PACKET_TOTAL_LENGTH_LENGTH)) {
            take(subPacketsLength).toOperatorPacket(
                version = version,
                typeId = typeId,
                packetLength = PREFIX_LENGTH + LENGTH_TYPE_ID_LENGTH + PACKET_TOTAL_LENGTH_LENGTH
            )
        }
    }

    private fun String.toOperatorPacket(version: Int, typeId: Int, packetLength: Int, limit: Int = Int.MAX_VALUE) =
        listOf(
            OperatorPacket(
                version = version,
                typeId = typeId,
                length = packetLength,
                operations = extractPackets(limit)
            )
        )

    private fun String.extractLiteral(version: Int) = run {
        var literal = this
        StringBuilder()
            .apply {
                do {
                    val lastFound = literal.first() == '0'
                    append(literal.drop(1).take(4))
                    literal = literal.drop(5)
                } while (!lastFound)
            }
            .toString()
            .let { binary ->
                listOf(
                    LiteralPacket(
                        version = version,
                        typeId = TYPE_LITERAL,
                        length = PREFIX_LENGTH + binary.length + binary.length / 4,
                        number = binary.toLong(2),
                    )
                )
            }
    }

    sealed class Packet {
        abstract val version: Int
        abstract val typeId: Int

        abstract fun length(): Int
        abstract fun versionSum(): Int
        abstract fun value(): Long
    }

    data class LiteralPacket(
        override val version: Int,
        override val typeId: Int,
        val length: Int,
        val number: Long,
    ) : Packet() {
        override fun versionSum() = version
        override fun length() = length
        override fun value() = number
    }

    data class OperatorPacket(
        override val version: Int,
        override val typeId: Int,
        val length: Int,
        val operations: List<Packet>,
    ) : Packet() {
        override fun versionSum() = version + operations.sumOf { it.versionSum() }
        override fun length() = length + operations.sumOf { it.length() }

        override fun value(): Long {
            return when (typeId) {
                0 -> operations.sumOf { it.value() }
                1 -> operations.fold(1L) { acc, packet -> acc * packet.value() }
                2 -> operations.minOf { it.value() }
                3 -> operations.maxOf { it.value() }
                5 -> if (operations[0].value() > operations[1].value()) 1 else 0
                6 -> if (operations[0].value() < operations[1].value()) 1 else 0
                7 -> if (operations[0].value() == operations[1].value()) 1 else 0
                else -> error("hmm...")
            }
        }
    }
}

fun main() {
    processStars(Day16::star1, Day16::star2)
}
