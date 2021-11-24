package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars
import java.security.MessageDigest

object Day4 {

    private fun String?.toData() = this ?: data(4)

    private fun String.toMD5Hex() = MessageDigest.getInstance("MD5")
        .apply { update(this@toMD5Hex.toByteArray()) }
        .digest()
        .joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

    private fun String.isValid(prefix: String = "00000") = startsWith(prefix) && get(prefix.length) != '0'

    fun star1(input: String?) = input.toData()
        .let { secret ->
            var res = 0
            while (!"$secret$res".toMD5Hex().isValid()) {
                res += 1
            }
            res
        }

    fun star2(input: String?) = input.toData()
        .let { secret ->
            var res = 0
            while (!"$secret$res".toMD5Hex().isValid("000000")) {
                res += 1
            }
            res
        }
}

processStars(Day4::star1, Day4::star2)