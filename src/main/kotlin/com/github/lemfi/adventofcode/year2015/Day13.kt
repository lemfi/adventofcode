package com.github.lemfi.adventofcode.year2015

import com.github.lemfi.adventofcode.processStars

object Day13 {

    private fun String?.toData() = (this ?: data(13))
        .lines()
        .map {
            it.replace(" happiness units by sitting next to", "")
                .replace("would lose ", "-")
                .replace("would gain ", "")
                .removeSuffix(".")
                .split(" ")
                .let { (p1, happiness, p2) ->
                    Sitting(p1, p2, happiness.toInt())
                }
        }

    fun star1(input: String?): Int {
        val sittings = input.toData()
        return computeBestPossibleHappiness(sittings)
    }

    fun star2(input: String?): Int {
        val sittings = input.toData().toMutableList()
        val persons = (sittings.map { it.p1 } + sittings.map { it.p2 }).toSet()

        persons.forEach {
            sittings.add(Sitting("me", it, 0))
            sittings.add(Sitting(it, "me", 0))
        }

        return computeBestPossibleHappiness(sittings)
    }

    private fun computeBestPossibleHappiness(sittings: List<Sitting>): Int {
        val persons = (sittings.map { it.p1 } + sittings.map { it.p2 }).toSet()

        val possibilities = persons.first().nexts(persons.drop(1))

        return possibilities
            .map { it.toSeat(sittings, possibilities.first().person) }
            .maxByOrNull { it.happiness(sittings, persons.first()) }
            ?.happiness(sittings, persons.first()) ?: 0
    }

    fun List<Sitting>.happiness(p1: String, p2: String) =
        first { it.p1 == p1 && it.p2 == p2 }.happiness + first { it.p1 == p2 && it.p2 == p1 }.happiness

    fun String.nexts(persons: List<String>): List<SeatPossibilities> =
        if (persons.isEmpty()) listOf(SeatPossibilities(this, emptyList()))
        else persons.map { person ->
            SeatPossibilities(person = this, leftToPossibilities = person.nexts(persons.filterNot { it == person }))
        }

    fun happiness(sittings: List<Sitting>, person: String, s2: SeatPossibilities, head: String): Int =
        sittings.happiness(
            p1 = person,
            p2 = s2.person
        ) + if (s2.leftToPossibilities.isEmpty())
            sittings.happiness(
                p1 = s2.person,
                p2 = head
            ) else
            s2.leftToPossibilities.maxOf {
                happiness(
                    sittings = sittings,
                    person = s2.person,
                    s2 = it,
                    head = head,
                )
            }

    fun SeatPossibilities.toSeat(sittings: List<Sitting>, head: String): Seat =
        Seat(
            person = person,
            leftTo = leftToPossibilities
                .maxByOrNull { happiness(sittings, person, it, head) }
                ?.toSeat(sittings, head)
        )

    data class SeatPossibilities(val person: String, val leftToPossibilities: List<SeatPossibilities>)
    data class Seat(val person: String, val leftTo: Seat?) {
        fun happiness(sittings: List<Sitting>, head: String): Int =
            (sittings.firstOrNull { it.p1 == person && it.p2 == (leftTo?.person ?: head) }?.happiness
                ?: 0) + (sittings.firstOrNull { it.p2 == person && it.p1 == (leftTo?.person ?: head) }?.happiness
                ?: 0) + (leftTo?.happiness(sittings, head) ?: 0)
    }

    data class Sitting(val p1: String, val p2: String, val happiness: Int)
}

fun main() {
    processStars(Day13::star1, Day13::star2)
}
