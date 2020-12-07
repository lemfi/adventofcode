package com.github.lemfi.adventofcode.year2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun sample1() {

        Assertions.assertEquals(4, day7_1("""light red bags contain 1 bright white bag, 2 muted yellow bags.
            |dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            |bright white bags contain 1 shiny gold bag.
            |muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            |shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            |dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            |vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            |faded blue bags contain no other bags.
            |dotted black bags contain no other bags.""".trimMargin()))
    }

    @Test
    fun sample2() {

        Assertions.assertEquals(32, day7_2("""light red bags contain 1 bright white bag, 2 muted yellow bags.
            |dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            |bright white bags contain 1 shiny gold bag.
            |muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            |shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            |dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            |vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            |faded blue bags contain no other bags.
            |dotted black bags contain no other bags.""".trimMargin()))
    }

    @Test
    fun sample3() {

        Assertions.assertEquals(126, day7_2("""shiny gold bags contain 2 dark red bags.
            |dark red bags contain 2 dark orange bags.
            |dark orange bags contain 2 dark yellow bags.
            |dark yellow bags contain 2 dark green bags.
            |dark green bags contain 2 dark blue bags.
            |dark blue bags contain 2 dark violet bags.
            |dark violet bags contain no other bags.""".trimMargin()))
    }

    @Test
    fun real1() {
        Assertions.assertEquals(124, day7_1(day7data))
    }

    @Test
    fun real2() {
        Assertions.assertEquals(34862, day7_2(day7data))
    }

}