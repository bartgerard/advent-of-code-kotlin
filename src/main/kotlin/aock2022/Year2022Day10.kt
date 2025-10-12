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

    fun partTwo() = 0L
}

class CPU {
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