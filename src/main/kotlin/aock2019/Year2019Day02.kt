package aock2019

import shared.sanitize
import shared.toIntegers

data class Year2019Day02(
    private val program: List<Int>
) {
    companion object {
        const val NOUN = 1
        const val VERB = 2
    }

    constructor(input: String) : this(input.sanitize().toIntegers().toMutableList())

    fun alter(initialProgram: List<Int>, noun: Int, verb: Int): MutableList<Int> {
        val program = initialProgram.toMutableList()
        program[NOUN] = noun
        program[VERB] = verb
        return program
    }

    fun partOne(noun: Int, verb: Int) = run(alter(program, noun, verb))

    fun runProgram() = run(program.toMutableList())

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

                99 -> break
                else -> throw IllegalArgumentException("Unknown opcode $opcode")
            }

            position = position + 4
        }

        return program
    }

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

}