package aock2022

import shared.sanitize

data class Year2022Day10(
    private val instructions: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne(): Long {
        return CPU().execute(instructions)
            .drop(20)
            .chunked(40)
            .map { it.first() }
            .sumOf { it.first * it.second }
    }

    fun partTwo(): Long {
        val result = CPU().execute(instructions)
            .drop(1)
            .chunked(40)
            .joinToString("\n") { line -> CPU.display(line.map { it.second }) }

        println(result)
        return 0L
    }
}

class CPU {
    companion object {
        fun display(spritePositions: List<Long>): String = spritePositions
            .mapIndexed { index, position -> if (position in index - 1..index + 1) "#" else "." }
            .joinToString("")
    }

    fun execute(instructions: List<String>): List<Pair<Int, Long>> {
        var value = 1L
        var t = 0
        val result: MutableList<Pair<Int, Long>> = mutableListOf(t++ to value)

        for (instruction in instructions) {
            when {
                instruction.startsWith("noop") -> {
                    result += t++ to value
                }

                instruction.startsWith("addx") -> {
                    val (_, b) = instruction.split(" ")
                    repeat(2) {
                        result += t++ to value
                    }
                    value += b.toInt()
                }
            }
        }

        return result
    }
}