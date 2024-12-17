package aock2024

import shared.sanitize
import shared.splitByEmptyLine
import shared.toIntegers

data class Year2024Day17(
    private val initialRegisters: List<Int>,
    private val program: List<Pair<Instruction, Int>>
) {
    companion object {
        const val A = 0
        const val B = 1
        const val C = 2
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].toIntegers(),
        input[1].toIntegers().chunked(2).map { Instruction.parse(it[0]) to it[1] }
    )

    fun partOne(): String = execute().joinToString(",")

    fun partTwo() = 0L

    fun execute(): List<Int> {
        val output = mutableListOf<Int>()
        val registers = initialRegisters.toMutableList()

        var i = 0

        while (i in program.indices) {
            val (instruction, operand) = program[i]

            when (instruction) {
                Instruction.ADV -> {
                    registers[A] = registers[A] / (2.shl(comboOperand(registers, operand) - 1))
                }

                Instruction.BXL -> {
                    registers[B] = registers[B].xor(operand)
                }

                Instruction.BST -> {
                    registers[B] = comboOperand(registers, operand).mod(8)
                }

                Instruction.JNZ -> {
                    if (registers[A] != 0) {
                        i = operand
                        continue
                    }
                }

                Instruction.BXC -> {
                    registers[B] = registers[B].xor(registers[C])
                }

                Instruction.OUT -> {
                    output += comboOperand(registers, operand).mod(8)
                }

                Instruction.BDV -> {
                    registers[B] = registers[A] / (2.shl(comboOperand(registers, operand) - 1))
                }

                Instruction.CDV -> {
                    registers[C] = divide(registers, operand)
                }
            }
            i++
        }

        println(registers)

        return output
    }

    private fun divide(registers: MutableList<Int>, operand: Int): Int {
        val comboOperand = comboOperand(registers, operand)
        return if (comboOperand == 1) {
            registers[A]
        } else if (comboOperand <= 1) {
            1
        } else {
            registers[A] / (2.shl(comboOperand - 1))
        }
    }

    private fun comboOperand(registers: List<Int>, literalOperand: Int): Int = when (literalOperand) {
        in 0..3 -> literalOperand
        4 -> registers[A]
        5 -> registers[B]
        6 -> registers[C]
        7 -> TODO()
        else -> throw IllegalArgumentException("invalid operand: $literalOperand")
    }
}

enum class Instruction(
    val opcode: Int,
    val label: String
) {
    ADV(0, "adv"),
    BXL(1, "bxl"),
    BST(2, "bst"),
    JNZ(3, "jnz"),
    BXC(4, "bxc"),
    OUT(5, "out"),
    BDV(6, "bdv"),
    CDV(7, "cdv");

    companion object {
        fun parse(opcode: Int) = when (opcode) {
            0 -> ADV
            1 -> BXL
            2 -> BST
            3 -> JNZ
            4 -> BXC
            5 -> OUT
            6 -> BDV
            7 -> CDV
            else -> throw IllegalArgumentException("invalid opcode: $opcode")
        }
    }
}