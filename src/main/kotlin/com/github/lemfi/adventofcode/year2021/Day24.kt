package com.github.lemfi.adventofcode.year2021

import com.github.lemfi.adventofcode.processStars

object Day24 {

    private fun String?.toData() = this ?: data(24)

    sealed class Result {
        fun get() = if (this is ResultOK) res else if (this is ResultPending) "..." else error("Result is KO!")
        fun isOk() = this is ResultOK
    }

    private fun String.toOperand1(): Variable = Variable(substringAfter(" ").substringBeforeLast(" "))

    private fun String.toOperand2(): Operand = substringAfterLast(" ").let {
        when (it) {
            "x", "y", "z", "w" -> Variable(it)
            else -> Value(it.toLong())
        }
    }

    private fun List<String>.toInstructions() =
        map {
            when {
                it.startsWith("inp") -> Inp(it.toOperand1())
                it.startsWith("add") -> Add(it.toOperand1(), it.toOperand2())
                it.startsWith("mul") -> Mul(it.toOperand1(), it.toOperand2())
                it.startsWith("div") -> Div(it.toOperand1(), it.toOperand2())
                it.startsWith("mod") -> Mod(it.toOperand1(), it.toOperand2())
                it.startsWith("eql") -> Eql(it.toOperand1(), it.toOperand2())
                else -> error("unknown instruction")
            }
        }

    data class ResultOK(val res: String) : Result()
    data class ResultPending(val x: Long, val y: Long, val z: Long, val w: Long) : Result()
    object ResultKO : Result()

    data class AluTest(val x: Long, val y: Long, val z: Long)

    val knownBadAlus: Map<Int, MutableSet<AluTest>> = mapOf(
        0 to mutableSetOf(),
        1 to mutableSetOf(),
        2 to mutableSetOf(),
        3 to mutableSetOf(),
        4 to mutableSetOf(),
        5 to mutableSetOf(),
        6 to mutableSetOf(),
        7 to mutableSetOf(),
        8 to mutableSetOf(),
        9 to mutableSetOf(),
        10 to mutableSetOf(),
        11 to mutableSetOf(),
        12 to mutableSetOf(),
        13 to mutableSetOf(),
        14 to mutableSetOf(),
    )

    fun ALU.registerBad() {
        if (init.length <= 12 && initialW() == last)
            knownBadAlus[init.length]!!.add(AluTest(initialX(), initialY(), initialZ()))
    }

    fun ALU.isBad() = init.length <= 12 && knownBadAlus[init.length]!!.contains(
        AluTest(
            initialX(),
            initialY(),
            initialZ(),
        )
    )

    sealed class Operand
    data class Variable(val letter: String) : Operand()

    data class Value(val value: Long) : Operand()

    sealed class Instruction {
        abstract val operand1: Variable
    }

    data class Inp(
        override val operand1: Variable,
    ) : Instruction()

    sealed class Operation : Instruction() {
        abstract val operand2: Operand
        abstract fun ALU.compute()
    }

    data class Add(
        override val operand1: Variable,
        override val operand2: Operand,
    ) : Operation() {
        override fun ALU.compute() =
            update(operand1.letter) {
                it + operand2.toValue()
            }
    }

    data class Mul(
        override val operand1: Variable,
        override val operand2: Operand,
    ) : Operation() {
        override fun ALU.compute() =
            update(operand1.letter) {
                it * operand2.toValue()
            }
    }

    data class Div(
        override val operand1: Variable,
        override val operand2: Operand,
    ) : Operation() {
        override fun ALU.compute() =
            update(operand1.letter) {
                it / operand2.toValue()
            }
    }

    data class Mod(
        override val operand1: Variable,
        override val operand2: Operand,
    ) : Operation() {
        override fun ALU.compute() =
            update(operand1.letter) {
                it % operand2.toValue()
            }
    }

    data class Eql(
        override val operand1: Variable,
        override val operand2: Operand,
    ) : Operation() {
        override fun ALU.compute() =
            update(operand1.letter) {
                if (it == operand2.toValue()) 1 else 0
            }
    }

    data class ALU(
        val instructions: MutableList<Instruction>,
        val init: String = "",
        val log: String = "",

        val variables: MutableMap<String, Long> = mutableMapOf(
            "x" to 0L,
            "y" to 0L,
            "z" to 0L,
            "w" to 0L,
        ),

        val last: Long,
    ) {

        private val initialVariables = variables.toMap()

        init {
            if (init.length == 1)
                knownBadAlus.onEach { it.value.clear() }
        }

        fun x(): Long = variables["x"]!!
        fun y(): Long = variables["y"]!!
        fun z(): Long = variables["z"]!!
        fun w(): Long = variables["w"]!!
        fun initialX(): Long = initialVariables["x"]!!
        fun initialY(): Long = initialVariables["y"]!!
        fun initialZ(): Long = initialVariables["z"]!!
        fun initialW(): Long = initialVariables["w"]!!

        fun update(variable: String, operation: (Long) -> Long) {
            variables[variable] = operation(variables[variable]!!)
        }

        var res: Result = ResultPending(x(), y(), z(), w())

        fun Operand.toValue(): Long = let { operand ->
            when (operand) {
                is Variable -> variables[operand.letter]!!
                is Value -> operand.value
            }
        }

        fun resolve(order: List<Long>.() -> List<Long>): Result {

            return runCatching {
                if (isBad()) {
                    error("I know this one is bad")
                }
                while (instructions.isNotEmpty()) {
                    when (val instruction = instructions.removeFirst()) {
                        is Inp -> {
                            res = (1L..9L).toList().let {
                                val l = it.order().iterator()
                                var aluRes: Result
                                do {
                                    val number = l.next()

                                    val t = ALU(
                                        instructions = instructions.toMutableList(),
                                        init = "$init$number",
                                        log = "$log   ",
                                        variables = variables
                                            .map { t ->
                                                t.key to if (t.key == instruction.operand1.letter) number else t.value
                                            }
                                            .toMap()
                                            .toMutableMap(),
                                        last = last
                                    )
                                    aluRes = t.resolve(order)
                                } while (!aluRes.isOk() && l.hasNext())
                                aluRes
                            }
                            break
                        }

                        is Operation -> instruction.apply { compute() }
                    }
                }

                res
            }
                .let {
                    it.getOrNull()?.let { result ->
                        if (result.isOk()) result
                        else if (init.length == 14) {
                            if (z() == 0L) ResultOK(init) else ResultKO
                        } else {
                            res
                        }
                    } ?: ResultKO
                }
                .also {
                    if (!it.isOk()) registerBad()
                }
        }
    }

    fun star1(input: String?) =
        ALU(input.toData().lines().toInstructions().toMutableList(), last = 1).resolve { reversed() }.get()

    fun star2(input: String?) =
        ALU(input.toData().lines().toInstructions().toMutableList(), last = 9).resolve { this }.get()

}

fun main() {
    processStars(Day24::star1, Day24::star2)
}
