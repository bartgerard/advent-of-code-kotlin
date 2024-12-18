package aock2019

import shared.sanitize
import shared.toIntegers

data class Year2019Day02(
    private val program: MutableList<Int>
) {
    constructor(input: String) : this(input.sanitize().toIntegers().toMutableList())

    fun programAlarm(): Year2019Day02 {
        program[1] = 12
        program[2] = 2
        return this
    }

    fun partOne(): List<Int> {
        var position = 0
        while (true) {
            val opcode = program[position]

            when (opcode) {
                1 -> {
                    program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]]
                }

                2 -> {
                    program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]]
                }

                99 -> break
                else -> throw IllegalArgumentException("Unknown opcode $opcode")
            }

            position = position + 4
        }

        return program
    }

    fun partTwo() = 0L

}