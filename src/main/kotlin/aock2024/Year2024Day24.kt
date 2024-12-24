package aock2024

import shared.sanitize
import shared.splitByEmptyLine

data class Year2024Day24(
    private val inputs: Map<String, Boolean>,
    private val instructions: List<BitInstruction>
) {
    companion object {
        fun parseBoolean(value: String) = when (value) {
            "1" -> true
            else -> false
        }

        fun toString(boolean: Boolean) = if (boolean) {
            "1"
        } else {
            "0"
        }
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].lines()
            .map { it.split(": ") }
            .associate { it[0] to parseBoolean(it[1]) },
        input[1].lines()
            .map { BitInstruction.parse(it) }
    )

    fun partOne(): Long {
        val registers = inputs.toMutableMap()
        val remainingInstructions = instructions.toMutableList()

        while (remainingInstructions.isNotEmpty()) {
            for (instruction in remainingInstructions.toList()) {
                if (registers.containsKey(instruction.wire1) && registers.containsKey(instruction.wire2)) {
                    remainingInstructions.remove(instruction)

                    val result = instruction.operation(registers[instruction.wire1]!!, registers[instruction.wire2]!!)
                    registers[instruction.wire3] = result
                }
            }
        }

        val output = registers.entries.filter { it.key.startsWith('z') }
            .sortedBy { it.key }
            .reversed()
            .map { it.value }
            .joinToString(separator = "") { toString(it) }

        return output.toLong(2)
    }

    fun partTwo() = 0L
}

data class BitInstruction(
    val wire1: String,
    val wire2: String,
    val wire3: String,
    val operation: (Boolean, Boolean) -> Boolean
) {
    companion object {
        val AND: (Boolean, Boolean) -> Boolean = { a, b -> a.and(b) }
        val OR: (Boolean, Boolean) -> Boolean = { a, b -> a.or(b) }
        val XOR: (Boolean, Boolean) -> Boolean = { a, b -> a.xor(b) }

        fun parse(input: String): BitInstruction {
            val split = input.split(" ")

            val operation = when (split[1]) {
                "AND" -> AND
                "OR" -> OR
                "XOR" -> XOR
                else -> throw IllegalArgumentException(split[1])
            }

            return BitInstruction(split[0], split[2], split[4], operation)
        }
    }
}