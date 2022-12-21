package com.github.lemfi.adventofcode.year2022

import com.github.lemfi.adventofcode.processStars

object Day19 {

    private fun String?.toData() = this ?: data(19)

    fun star1(input: String?) =
        input.toData().lines().map {
            Blueprint(
                id = it.substringAfter("Blueprint ").substringBefore(":").toInt(),
                oreCost = it.substringAfter("Each ore robot costs ").substringBefore(".").toStones(),
                clayCost = it.substringAfter("Each clay robot costs ").substringBefore(".").toStones(),
                obsidianCost = it.substringAfter("Each obsidian robot costs ").substringBefore(".").toStones(),
                geodeCost = it.substringAfter("Each geode robot costs ").substringBefore(".").toStones(),
            )
        }
            .map { blueprint ->

                var maxGeodes = 0
                val treatedBags = mutableSetOf<List<Int>>()
                val bags = mutableListOf(Bag(time = 24, blueprint = blueprint))
                while (bags.isNotEmpty()) {
                    bags.removeLast().apply {
                        if (!treatedBags.contains(hash())) {
                            treatedBags.add(hash())
                            if (time == 0) maxGeodes = maxOf(maxGeodes, geode)
                            else if (buildGeodeRobot(blueprint) == 1) {
                                bags.addIfNotIn(
                                    copy(
                                        ore = ore + oreRobots,
                                        clay = clay + clayRobots,
                                        obsidian = obsidian + obsidianRobots,
                                        geode = geode + geodeRobots,
                                        geodeRobots = geodeRobots + 1,
                                        time = time - 1,
                                    )
                                )
                            } else {
                                if (canBuildObsidianRobot(blueprint) && obsidianRobots < blueprint.obsidianToUse()) {
                                    bags.addIfNotIn(
                                        copy(
                                            ore = ore + oreRobots,
                                            clay = clay + clayRobots,
                                            obsidian = obsidian + obsidianRobots,
                                            geode = geode + geodeRobots,
                                            obsidianRobots = obsidianRobots + 1,
                                            time = time - 1,
                                        ).apply { buildObsidianRobot(blueprint) }
                                    )
                                }
                                if (canBuildClayRobot(blueprint) && clayRobots < blueprint.clayToUse()) {
                                    bags.addIfNotIn(
                                        copy(
                                            ore = ore + oreRobots,
                                            clay = clay + clayRobots,
                                            obsidian = obsidian + obsidianRobots,
                                            geode = geode + geodeRobots,
                                            clayRobots = clayRobots + 1,
                                            time = time - 1,
                                        ).apply { buildClayRobot(blueprint) }
                                    )
                                }
                                if (canBuildOreRobot(blueprint) && oreRobots < blueprint.oreToUse()) {
                                    bags.addIfNotIn(
                                        copy(
                                            ore = ore + oreRobots,
                                            clay = clay + clayRobots,
                                            obsidian = obsidian + obsidianRobots,
                                            geode = geode + geodeRobots,
                                            oreRobots = oreRobots + 1,
                                            time = time - 1,
                                        ).apply { buildOreRobot(blueprint) }
                                    )
                                }
                                bags.addIfNotIn(
                                    copy(
                                        ore = ore + oreRobots,
                                        clay = clay + clayRobots,
                                        obsidian = obsidian + obsidianRobots,
                                        geode = geode + geodeRobots,
                                        time = time - 1,
                                    )
                                )
                            }
                        }
                    }
                }
                maxGeodes * blueprint.id
            }.fold(0) { acc, geodes ->
                acc + geodes
            }

    fun star2(input: String?) = input.toData().lines().map {
        Blueprint(
            id = it.substringAfter("Blueprint ").substringBefore(":").toInt(),
            oreCost = it.substringAfter("Each ore robot costs ").substringBefore(".").toStones(),
            clayCost = it.substringAfter("Each clay robot costs ").substringBefore(".").toStones(),
            obsidianCost = it.substringAfter("Each obsidian robot costs ").substringBefore(".").toStones(),
            geodeCost = it.substringAfter("Each geode robot costs ").substringBefore(".").toStones(),
        )
    }.take(3)
        .map { blueprint ->

            var maxGeodes: Bag? = null
            val treatedBags = mutableSetOf<List<Int>>()
            val bags = mutableListOf(Bag(time = 32, blueprint = blueprint))
            while (bags.isNotEmpty()) {
                bags.removeLast().apply {
                    if (!treatedBags.contains(hash())) {
                        treatedBags.add(hash())
                        if (time == 0) maxGeodes =
                            if (maxGeodes == null) this else if (geode > maxGeodes!!.geode) this else maxGeodes
                        else if (canBuildGeodeRobot(blueprint)) {
                            bags.addIfNotIn(
                                copy(
                                    ore = ore + oreRobots,
                                    clay = clay + clayRobots,
                                    obsidian = obsidian + obsidianRobots,
                                    geode = geode + geodeRobots,
                                    geodeRobots = geodeRobots + 1,
                                    time = time - 1,
                                ).apply { buildGeodeRobot(blueprint) }
                            )
                        } else {
                            bags.addIfNotIn(
                                copy(
                                    ore = ore + oreRobots,
                                    clay = clay + clayRobots,
                                    obsidian = obsidian + obsidianRobots,
                                    geode = geode + geodeRobots,
                                    time = time - 1,
                                )
                            )
                            if (canBuildOreRobot(blueprint) && oreRobots < blueprint.oreToUse()) {
                                bags.addIfNotIn(
                                    copy(
                                        ore = ore + oreRobots,
                                        clay = clay + clayRobots,
                                        obsidian = obsidian + obsidianRobots,
                                        geode = geode + geodeRobots,
                                        oreRobots = oreRobots + 1,
                                        time = time - 1,
                                    ).apply { buildOreRobot(blueprint) }
                                )
                            }
                            if (canBuildClayRobot(blueprint) && clayRobots < blueprint.clayToUse()) {
                                bags.addIfNotIn(
                                    copy(
                                        ore = ore + oreRobots,
                                        clay = clay + clayRobots,
                                        obsidian = obsidian + obsidianRobots,
                                        geode = geode + geodeRobots,
                                        clayRobots = clayRobots + 1,
                                        time = time - 1,
                                    ).apply { buildClayRobot(blueprint) }
                                )
                            }
                            if (canBuildObsidianRobot(blueprint) && obsidianRobots < blueprint.obsidianToUse()) {
                                bags.addIfNotIn(
                                    copy(
                                        ore = ore + oreRobots,
                                        clay = clay + clayRobots,
                                        obsidian = obsidian + obsidianRobots,
                                        geode = geode + geodeRobots,
                                        obsidianRobots = obsidianRobots + 1,
                                        time = time - 1,
                                    ).apply { buildObsidianRobot(blueprint) }
                                )
                            }
                        }
                    }
                }
            }
            println(maxGeodes)
            maxGeodes!!.geode
        }.fold(1) { acc, geodes ->
            acc * geodes
        }

    private fun MutableList<Bag>.addIfNotIn(bag: Bag) {
        add(bag)
    }

    private fun Blueprint.oreToUse() =
        maxOf(
            oreCost.first { it is Ore }.cost,
            clayCost.first { it is Ore }.cost,
            obsidianCost.first { it is Ore }.cost,
            geodeCost.first { it is Ore }.cost
        )

    private fun Blueprint.obsidianToUse() =
        geodeCost.first { it is Obsidian }.cost

    private fun Blueprint.clayToUse() =
        obsidianCost.first { it is Clay }.cost

    private fun Bag.buildGeodeRobot(blueprint: Blueprint): Int {
        return if (blueprint.geodeCost.all {
                when (it) {
                    is Ore -> ore >= it.cost
                    is Clay -> clay >= it.cost
                    is Obsidian -> obsidian >= it.cost
                    is Geode -> geode >= it.cost
                }
            }) {
            //            println("Building one Geode robot with ")
            blueprint.geodeCost.forEach {
                when (it) {
                    is Ore -> ore -= it.cost //.apply { println("$this ore") }
                    is Clay -> clay -= it.cost //.apply { println("$this clay") }
                    is Obsidian -> obsidian -= it.cost //.apply { println("$this obsidian") }
                    is Geode -> geode -= it.cost //.apply { println("$this geode") }
                }
            }
            1
        } else 0
    }

    private fun Bag.buildObsidianRobot(blueprint: Blueprint): Int {
        return if (canBuildObsidianRobot(blueprint)) {
            //            println("Building one Obsidian robot with ")
            blueprint.obsidianCost.forEach {
                when (it) {
                    is Ore -> ore -= it.cost //.apply { println("$this ore") }
                    is Clay -> clay -= it.cost //.apply { println("$this clay") }
                    is Obsidian -> obsidian -= it.cost //.apply { println("$this obsidian") }
                    is Geode -> geode -= it.cost //.apply { println("$this geode") }
                }
            }
            1
        } else 0
    }

    private fun Bag.canBuildGeodeRobot(blueprint: Blueprint) = blueprint.geodeCost.all {
        when (it) {
            is Ore -> ore >= it.cost
            is Clay -> clay >= it.cost
            is Obsidian -> obsidian >= it.cost
            is Geode -> geode >= it.cost
        }
    }

    private fun Bag.canBuildObsidianRobot(blueprint: Blueprint) = blueprint.obsidianCost.all {
        when (it) {
            is Ore -> ore >= it.cost
            is Clay -> clay >= it.cost
            is Obsidian -> obsidian >= it.cost
            is Geode -> geode >= it.cost
        }
    }

    private fun Bag.canBuildClayRobot(blueprint: Blueprint) = blueprint.clayCost.all {
        when (it) {
            is Ore -> ore >= it.cost
            is Clay -> clay >= it.cost
            is Obsidian -> obsidian >= it.cost
            is Geode -> geode >= it.cost
        }
    }

    private fun Bag.canBuildOreRobot(blueprint: Blueprint) = blueprint.oreCost.all {
        when (it) {
            is Ore -> ore >= it.cost
            is Clay -> clay >= it.cost
            is Obsidian -> obsidian >= it.cost
            is Geode -> geode >= it.cost
        }
    }

    private fun Bag.buildClayRobot(blueprint: Blueprint): Int {
        return if (blueprint.clayCost.all {
                when (it) {
                    is Ore -> ore >= it.cost
                    is Clay -> clay >= it.cost
                    is Obsidian -> obsidian >= it.cost
                    is Geode -> geode >= it.cost
                }
            }) {
            //            println("Building one Clay robot with ")
            blueprint.clayCost.forEach {
                when (it) {
                    is Ore -> ore -= it.cost //.apply { println("$this ore") }
                    is Clay -> clay -= it.cost //.apply { println("$this clay") }
                    is Obsidian -> obsidian -= it.cost //.apply { println("$this obsidian") }
                    is Geode -> geode -= it.cost //.apply { println("$this geode") }
                }
            }
            1
        } else 0
    }

    private fun Bag.buildOreRobot(blueprint: Blueprint): Int {
        return if (blueprint.oreCost.all {
                when (it) {
                    is Ore -> ore >= it.cost
                    is Clay -> clay >= it.cost
                    is Obsidian -> obsidian >= it.cost
                    is Geode -> geode >= it.cost
                }
            }) {
            //            println("Building one Ore robot with ")
            blueprint.oreCost.forEach {
                when (it) {
                    is Ore -> ore -= it.cost //.apply { println("$this ore") }
                    is Clay -> clay -= it.cost //.apply { println("$this clay") }
                    is Obsidian -> obsidian -= it.cost //.apply { println("$this obsidian") }
                    is Geode -> geode -= it.cost //.apply { println("$this geode") }
                }
            }
            1
        } else 0
    }

    private fun String.toStones() =
        split(" and ").map {
            it.split(" ").let { (qt, stone) ->
                when (stone) {
                    "ore" -> Ore(qt.toInt())
                    "clay" -> Clay(qt.toInt())
                    "geode" -> Geode(qt.toInt())
                    "obsidian" -> Obsidian(qt.toInt())
                    else -> error("wtf is that stone?")
                }
            }
        }

    data class Bag(
        var oreRobots: Int = 1,
        var clayRobots: Int = 0,
        var obsidianRobots: Int = 0,
        var geodeRobots: Int = 0,

        var ore: Int = 0,
        var clay: Int = 0,
        var obsidian: Int = 0,
        var geode: Int = 0,

        var time: Int,
        val blueprint: Blueprint,
    ) {
        override fun toString(): String =
            """
                ROBOTS:
                    ore     : $oreRobots
                    clay    : $clayRobots
                    obsidian: $obsidianRobots
                    geode   : $geodeRobots
                    
                STONES:
                    ore     : $ore
                    clay    : $clay
                    obsidian: $obsidian
                    geode   : $geode
                    
            """.trimIndent()

        fun hash() = listOf(
            oreRobots,
            clayRobots,
            obsidianRobots,
            geodeRobots,
            ore % (blueprint.oreToUse() + 1),
            clay % (blueprint.clayToUse() + 1),
            obsidian % (blueprint.obsidianToUse() + 1),
            time,
        )
    }

    data class Blueprint(
        val id: Int,
        val oreCost: List<Stone>,
        val clayCost: List<Stone>,
        val obsidianCost: List<Stone>,
        val geodeCost: List<Stone>
    ) {
        val maxOre = oreToUse()
        val maxClay = clayToUse()
        val maxObsidian = obsidianToUse()
    }

    sealed interface Stone {
        val cost: Int
    }

    data class Ore(override val cost: Int) : Stone
    data class Clay(override val cost: Int) : Stone
    data class Obsidian(override val cost: Int) : Stone
    data class Geode(override val cost: Int) : Stone
}

fun main() {
    processStars(Day19::star1, Day19::star2)
}

// 01  02  03  04  05  06  07  08  09  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30
// 1462 too low
// 4   0   3   8   45  6   0   24  27  0   11  0   0   14  15  16  68  162 76  0   63  0   46  24  25  338 81  0   406 0
// 4   0   3   12  45  6   0   24  27  0   11  0   0   14  15  16  68  162 76  0   63  0   46  24  25  338 81  0   406 0
