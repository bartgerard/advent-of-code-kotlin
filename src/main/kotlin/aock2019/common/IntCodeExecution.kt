package aock2019.common

data class IntCodeExecution(
    val program: MutableList<Int>,
    val input: MutableList<Int> = mutableListOf(),
    val output: MutableList<Int> = mutableListOf(),
    var instructionPointer: Int = 0,
    var relativeBase: Int = 0,
) {

    constructor(program: List<Int>, input: List<Int>) : this(
        program.toMutableList(),
        input.toMutableList()
    )

    fun opCode() = program[instructionPointer] % 100

    fun modes() = ParameterMode.toModes(program[instructionPointer])

    fun input() = input.removeFirstOrNull()

    fun output(value: Int) {
        output += value
    }

    fun address(index: Int) = when (modes().getOrElse(index - 1) { ParameterMode.POSITION }) {
        ParameterMode.POSITION -> program[instructionPointer + index]
        ParameterMode.IMMEDIATE -> instructionPointer + index
        ParameterMode.RELATIVE -> program[instructionPointer + index] + relativeBase
    }

    fun parameter(index: Int) = program[address(index)]

    fun run(): IntCodeExecution {
        while (true) {
            when (val opcode = opCode()) {
                1 -> {
                    // ADD
                    program[program[instructionPointer + 3]] = parameter(1) + parameter(2)
                    instructionPointer += 4
                }

                2 -> {
                    // MULTIPLY
                    program[program[instructionPointer + 3]] = parameter(1) * parameter(2)
                    instructionPointer += 4
                }

                3 -> {
                    // INPUT
                    program[program[instructionPointer + 1]] = input() ?: break
                    instructionPointer += 2
                }

                4 -> {
                    // OUTPUT
                    output(parameter(1))
                    instructionPointer += 2
                }

                5 -> {
                    // JUMP-IF-TRUE
                    if (parameter(1) > 0) {
                        instructionPointer = parameter(2)
                    } else {
                        instructionPointer += 3
                    }
                }

                6 -> {
                    // JUMP-IF-FALSE
                    if (parameter(1) == 0) {
                        instructionPointer = parameter(2)
                    } else {
                        instructionPointer += 3
                    }
                }

                7 -> {
                    // LESS_THAN
                    program[program[instructionPointer + 3]] = if (parameter(1) < parameter(2)) 1 else 0
                    instructionPointer += 4
                }

                8 -> {
                    // EQUALS
                    program[program[instructionPointer + 3]] = if (parameter(1) == parameter(2)) 1 else 0
                    instructionPointer += 4
                }

                9 -> {
                    // ADJUST_RELATIVE_BASE
                    relativeBase += parameter(1)
                    instructionPointer += 2
                }

                99 -> break
                else -> error("Unknown opcode $opcode")
            }
        }

        return this
    }

    fun diagnosticCode() = output.lastOrNull() ?: -1

}