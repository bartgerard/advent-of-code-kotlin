package aock2019

import shared.sanitize
import shared.toIntegers

data class Year2019Day05(
    private val program: List<Int>
) {
    constructor(input: String) : this(input.sanitize().toIntegers())

    fun partOne() = IntCodeV2.run(program.toMutableList())
    fun partTwo() = 0L
}

class IntCodeV2 {

    companion object {
        fun run(program: MutableList<Int>): List<Int> {
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

                    3 -> {

                    }

                    4 -> {

                    }

                    99 -> break
                    else -> throw IllegalArgumentException("Unknown opcode $opcode")
                }

                position = position + 4
            }

            return program
        }
    }

}