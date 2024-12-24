package aock2015

import shared.sanitize

data class Year2015Day07(
    private val circuit: Circuit
) {
    constructor(input: String) : this(Circuit.parse(input))

    fun partOne(wire: String): Long = circuit.signalFor(wire, mutableMapOf())
    fun partTwo(wire: String): Long = circuit.signalFor(wire, mutableMapOf("b" to partOne(wire)))
}

data class Circuit(
    private val instructionByOutput: Map<String, Instruction>
) {
    companion object {
        fun parse(input: String) = input.sanitize()
            .lines()
            .associate { line ->
                line.split(" -> ")
                    .let { it[1].trim() to Instruction.parse(it[0]) }
            }
            .let { Circuit(it) }
    }

    fun signalFor(wire: String, signals: MutableMap<String, Long>): Long {
        if (signals.containsKey(wire)) {
            return signals[wire]!!
        }

        val signal = wire.toLongOrNull() ?: instructionByOutput[wire]!!.let { instruction ->
            instruction.operation.invoke(
                instruction.wires.map { signalFor(it, signals) }
            )
        }

        signals[wire] = signal

        return signal
    }
}

data class Instruction(
    val wires: List<String>,
    val operation: (List<Long>) -> Long
) {
    companion object {
        fun parse(input: String): Instruction {
            return when {
                input.contains(" AND ") -> {
                    val (wire1, wire2) = input.split(" AND ")
                    Instruction(listOf(wire1, wire2)) { it[0].and(it[1]) }
                }

                input.contains(" OR ") -> {
                    val (wire1, wire2) = input.split(" OR ")
                    Instruction(listOf(wire1, wire2)) { it[0].or(it[1]) }
                }

                input.contains(" LSHIFT ") -> {
                    val (wire, value) = input.split(" LSHIFT ")
                    Instruction(listOf(wire)) { it[0].shl(value.toInt()) }
                }

                input.contains(" RSHIFT ") -> {
                    val (wire, value) = input.split(" RSHIFT ")
                    Instruction(listOf(wire)) { it[0].shr(value.toInt()) }
                }

                input.contains("NOT ") -> {
                    val wire = input.split("NOT ")[1]
                    Instruction(listOf(wire)) { it[0].inv() and 0xffff }
                }

                else -> Instruction(listOf(input)) { it[0] }

                //else -> input.toLongOrNull()
                //    ?.let { value -> Instruction(emptyList()) { _ -> value } }
                //    ?: Instruction(listOf(input)) { it[0] }
            }
        }
    }
}


fun main() {
    println(65535)
    println(Short.MAX_VALUE * 2)
}