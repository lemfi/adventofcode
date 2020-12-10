package com.github.lemfi.adventofcode.year2020

import kotlin.math.max

fun day10_1(data: String): Long {

    return data.lines()
            .map { it.toLong() }
            .toValidTree()
            ?.toJoltDiffs()
            ?.let { it.first * it.second } ?: -1
}

fun day10_2(data: String): Long {
    return data.lines()
            .map { it.toLong() }
            .sorted()
            .split()
            .map { it.toPosibilities(max(0, it.first() - 3), it.last() + 3).flatten() }
            .map { it.count().toLong() }
            .reduce { acc, i -> acc * i }
}

fun List<Long>.split(currenLst: List<Long> = emptyList(), lst: List<List<Long>> = emptyList()): List<List<Long>> {
    if (this.isEmpty()) return listOf(*lst.toTypedArray(), currenLst)
    val current = first()
    if (filter { it <= current + 3 && it != current }.let { it.size == 1 && (it.first() - current) == 3L }) {
        return drop(1).split(listOf(), listOf(*lst.toTypedArray(), listOf(*currenLst.toTypedArray(), current)))
    } else {
        return drop(1).split(listOf(*currenLst.toTypedArray(), current), lst)
    }
}

fun List<Long>.toPosibilities(min: Long, max: Long, current: List<Long> = emptyList(), possibilities: List<List<Long>> = emptyList()): List<List<Long>> {
    if (filter { it <= min + 3 }.isEmpty()) {
        if (current.contains(max -3)) {
            return listOf(*possibilities.toTypedArray(), current)
        } else return possibilities
    }
    val filter = filter { it <= min + 3 }

    return listOf(*possibilities.toTypedArray(), *filter.map { value -> filterNot { it <= value }.toPosibilities(value, max, listOf(value), listOf()) }.flatten().toTypedArray())
}

fun Tree.toJoltDiffs(res: Pair<Long, Long> = 1L to 1L): Pair<Long, Long> {
    if (tree == null) return res
    if (value + 1 == tree.value) return tree.toJoltDiffs(res.first + 1 to res.second)
    if (value + 3 == tree.value) return tree.toJoltDiffs(res.first to res.second + 1)
    else return tree.toJoltDiffs(res)
}

fun List<Long>.toValidTree(init: Long = 0): Tree? {
    if (this.isEmpty()) return null
    return sorted().first { it <= init + 3 }
            .let {
                value ->
                Tree(value, filterNot { it == value }.toValidTree(value))
            }
}

data class Tree(
        val value: Long,
        val tree: Tree?
)

fun main() {

    day10_1(day10data)
            .apply { println(this) }

    day10_2(day10data)
            .apply { println("res: $this") }

}

val day10data = """35
111
135
32
150
5
106
154
41
7
27
117
109
63
64
21
138
98
40
71
144
13
66
48
12
55
119
103
54
78
65
112
39
128
53
140
77
34
28
81
151
125
85
124
2
99
131
59
60
6
94
33
42
93
14
141
92
38
104
9
29
100
52
19
147
49
74
70
84
113
120
91
97
17
45
139
90
116
149
129
87
69
20
24
148
18
58
123
76
118
130
132
75
110
105
1
8
86"""