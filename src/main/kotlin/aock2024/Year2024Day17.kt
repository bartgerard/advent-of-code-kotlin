package aock2024

import shared.sanitize
import shared.splitByEmptyLine
import shared.toIntegers
import shared.toLongs

data class Year2024Day17(
    private val initialRegisters: List<Long>,
    private val program: List<Int>
) {
    companion object {
        const val A = 0
        const val B = 1
        const val C = 2
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].toLongs(),
        input[1].toIntegers()
    )

    fun partOne(): String = execute(initialRegisters.toMutableList()).joinToString(",")

    fun partTwo(): Long = findCopyGeneratingInput()

    fun execute(i: Long): List<Int> = initialRegisters.toMutableList()
        .also { it[0] = i }
        .let { execute(it) }

    fun execute(registers: MutableList<Long>): List<Int> {
        val output = mutableListOf<Int>()
        val compiledProgram = program.chunked(2).map { Instruction.parse(it[0]) to it[1] }

        var i = 0

        while (i in compiledProgram.indices) {
            val (instruction, operand) = compiledProgram[i]

            when (instruction) {
                Instruction.ADV -> {
                    registers[A] = registers[A].shr(comboOperand(registers, operand))
                }

                Instruction.BXL -> {
                    registers[B] = registers[B].xor(operand.toLong())
                }

                Instruction.BST -> {
                    registers[B] = comboOperand(registers, operand).mod(8L)
                }

                Instruction.JNZ -> {
                    if (registers[A] != 0L) {
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
                    registers[B] = registers[A].shr(comboOperand(registers, operand))
                }

                Instruction.CDV -> {
                    registers[C] = registers[A].shr(comboOperand(registers, operand))
                }
            }
            i++
        }

        return output
    }

    private fun comboOperand(registers: List<Long>, literalOperand: Int): Int = when (literalOperand) {
        in 0..3 -> literalOperand
        4 -> registers[A].toInt()
        5 -> registers[B].toInt()
        6 -> registers[C].toInt()
        else -> error("invalid operand: $literalOperand")
    }.mod(8)

    fun findCopyGeneratingInput(): Long {
        var value = 0L

        (0..<program.size).toList().reversed().forEach { i ->
            value = value shl 3

            while (execute(value) != program.subList(i, program.size)) {
                value++
            }
        }

        return value
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
            else -> error("invalid opcode: $opcode")
        }
    }
}