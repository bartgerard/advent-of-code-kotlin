package aock2020

import shared.sanitize
import shared.toLongs

data class Year2020Day08(
    private val instructions: List<Instruction>
) {
    constructor(input: String) : this(input.sanitize().lines().map { Instruction.parse(it) })

    fun partOne() = 0L
    fun partTwo() = 0L
}

data class Instruction(
    val operation: String,
    val argument: Long
) {
    companion object {
        fun parse(line: String): Instruction {
            val (operation, argument) = line.split(" ")
            return Instruction(operation, argument.toLongs().first())
        }
    }
}