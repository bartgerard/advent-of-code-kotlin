package aock2024

import shared.sanitize
import shared.toLongs

data class Year2024Day07(
    val equations: List<Pair<Long, List<Long>>>
) {
    companion object {
        val ADD: (Long, Long) -> Long = { i, j -> i + j }
        val MULTIPLY: (Long, Long) -> Long = { i, j -> i * j }
        val CONCAT: (Long, Long) -> Long = { i, j -> (i.toString() + j.toString()).toLong() }

        val OPERATORS = listOf(ADD, MULTIPLY)
        val EXTENDED_OPERATORS = listOf(ADD, MULTIPLY, CONCAT)
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .map {
                val parts = it.split(":")
                parts[0].toLong() to parts[1].toLongs()
            }
    )

    fun partOne(): Long = this.equations.sumOf { calibrationResult(it, OPERATORS) }

    fun calibrationResult(
        equation: Pair<Long, List<Long>>,
        operators: List<(Long, Long) -> Long>
    ): Long {

        val testValue = equation.first
        val values = equation.second

        var previous = mutableListOf<Long>(values[0])
        var next = mutableListOf<Long>()

        for (nextValue in values.subList(1, values.size)) {
            next = mutableListOf<Long>()
            for (p in previous) {

                for (operator in operators) {
                    val newValue = operator(p, nextValue)
                    if (newValue <= testValue) {
                        next += newValue
                    }
                }
            }
            previous = next.toMutableList()
        }

        return if (previous.contains(testValue)) {
            return testValue
        } else {
            return 0
        }
    }

    fun partTwo(): Long = this.equations.sumOf { calibrationResult(it, EXTENDED_OPERATORS) }
}