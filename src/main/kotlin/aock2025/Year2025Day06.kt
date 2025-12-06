package aock2025

import shared.makeSameLength
import shared.product
import shared.sanitize
import shared.splitByVerticalDelimiter

data class Year2025Day06(
    private val problems: List<Pair<Char, List<String>>>
) {
    constructor(input: String) : this(
        input.sanitize()
            .lines()
            .makeSameLength()
            .splitByVerticalDelimiter(' ')
            .map { block -> block.last().first() to block.dropLast(1) }
    )

    fun partOne(): Long = problems.sumOf { (operation, strings) ->
        solve(operation, strings.map { it.trim().toLong() })
    }

    fun partTwo(): Long = problems.sumOf { (operation, strings) ->
        val numbers = strings.first().indices.map { i ->
            strings.map { it[i] }
                .filter { it != ' ' }
                .joinToString("")
                .toLong()
        }
        solve(operation, numbers)
    }

    private fun solve(operation: Char, numbers: List<Long>): Long = when (operation) {
        '+' -> numbers.sum()
        '*' -> numbers.product()
        else -> error("Invalid operation: $operation")
    }

}