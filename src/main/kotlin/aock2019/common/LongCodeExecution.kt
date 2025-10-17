package aock2019.common

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

data class LongCodeExecution(
    val program: MutableList<Long>,
    val input: BlockingQueue<Long>,
    val output: BlockingQueue<Long> = LinkedBlockingQueue(),
    var instructionPointer: Int = 0,
    var relativeBase: Int = 0,
) {

    constructor(program: List<Long>, input: List<Long>) : this(
        program.toMutableList(),
        LinkedBlockingQueue(input.toMutableList())
    )

    fun get(index: Int) = program.getOrElse(index) { 0L }

    fun set(index: Int, value: Long) {
        val address = address(index)

        if (address !in program.indices) {
            program.addAll(List(address - program.size + 1) { 0 })
        }

        program[address] = value
    }

    fun opCode() = get(instructionPointer) % 100L

    fun modes() = ParameterMode.toModes(get(instructionPointer))

    fun input(): Long = input.take()
    //.removeFirstOrNull()

    fun output(value: Long) {
        output += value
    }

    fun address(index: Int) = when (modes().getOrElse(index - 1) { ParameterMode.POSITION }) {
        ParameterMode.POSITION -> get(instructionPointer + index).toInt()
        ParameterMode.IMMEDIATE -> instructionPointer + index
        ParameterMode.RELATIVE -> (get(instructionPointer + index) + relativeBase).toInt()
    }

    fun parameter(index: Int) = get(address(index))

    fun run(): LongCodeExecution {
        while (true) {
            when (val opcode = opCode()) {
                1L -> {
                    // ADD
                    set(3, parameter(1) + parameter(2))
                    instructionPointer += 4
                }

                2L -> {
                    // MULTIPLY
                    set(3, parameter(1) * parameter(2))
                    instructionPointer += 4
                }

                3L -> {
                    // INPUT
                    set(1, input())
                    instructionPointer += 2
                }

                4L -> {
                    // OUTPUT
                    output(parameter(1))
                    instructionPointer += 2
                }

                5L -> {
                    // JUMP-IF-TRUE
                    if (parameter(1) > 0L) {
                        instructionPointer = parameter(2).toInt()
                    } else {
                        instructionPointer += 3
                    }
                }

                6L -> {
                    // JUMP-IF-FALSE
                    if (parameter(1) == 0L) {
                        instructionPointer = parameter(2).toInt()
                    } else {
                        instructionPointer += 3
                    }
                }

                7L -> {
                    // LESS_THAN
                    set(3, if (parameter(1) < parameter(2)) 1 else 0)
                    instructionPointer += 4
                }

                8L -> {
                    // EQUALS
                    set(3, if (parameter(1) == parameter(2)) 1 else 0)
                    instructionPointer += 4
                }

                9L -> {
                    // ADJUST_RELATIVE_BASE
                    relativeBase += parameter(1).toInt()
                    instructionPointer += 2
                }

                99L -> break
                else -> error("Unknown opcode $opcode")
            }
        }

        return this
    }

    fun diagnosticCode() = output.lastOrNull() ?: -1

}

// TEST - Thermal Environment Supervision Terminal
// BOOST - Basic Operation Of System Test