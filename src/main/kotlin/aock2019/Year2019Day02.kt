package aock2019

import shared.sanitize
import shared.toIntegers

data class Year2019Day02(
    private val program: List<Int>
) {
    constructor(input: String) : this(input.sanitize().toIntegers())

    fun partOne(noun: Int, verb: Int) = IntCode.run(IntCode.alter(program, noun, verb))

    fun partTwo(address0: Int): Int {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val result = partOne(noun, verb)
                if (result[0] == address0) {
                    return 100 * noun + verb
                }
            }
        }

        return -1
    }

    fun runProgram() = IntCode.run(program.toMutableList())

}

class IntCode {

    companion object {
        const val NOUN = 1
        const val VERB = 2

        fun alter(initialProgram: List<Int>, noun: Int, verb: Int): MutableList<Int> {
            val program = initialProgram.toMutableList()
            program[NOUN] = noun
            program[VERB] = verb
            return program
        }

        fun run(program: MutableList<Int>): List<Int> {
            var position = 0
            while (true) {
                when (val opcode = program[position]) {
                    1 -> {
                        program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]]
                    }

                    2 -> {
                        program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]]
                    }

                    99 -> break
                    else -> error("Unknown opcode $opcode")
                }

                position += 4
            }

            return program
        }
    }

}