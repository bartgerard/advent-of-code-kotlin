package aock2025

import shared.makeSameLength
import shared.product
import shared.sanitize
import shared.splitByVerticalDelimiter

data class Year2025Day06(
    private val problems: List<Problem>
) {
    constructor(input: String) : this(
        input.sanitize()
            .lines()
            .makeSameLength()
            .splitByVerticalDelimiter(' ')
            .map { block ->
                Problem(
                    Operation.parse(block.last().first()),
                    block.dropLast(1)
                )
            }
    )

    fun partOne(): Long = problems.sumOf { it.solve() }
    fun partTwo(): Long = problems.sumOf { it.cephalopod().solve() }
}

data class Problem(
    private val operation: Operation,
    private val values: List<String>
) {
    fun solve() = operation.method.invoke(values.map { it.trim().toLong() })

    /**
     * Each number is given in its own column,
     * with the most significant digit at the top
     * and the least significant digit at the bottom.
     */
    fun cephalopod() = Problem(
        operation,
        values.first()
            .indices.map { i ->
                values.map { it[i] }
                    .filter { it != ' ' }
                    .joinToString("")
            }
            .reversed()
    )
}

enum class Operation(
    val symbol: Char,
    val method: (List<Long>) -> Long
) {
    SUM('+', List<Long>::sum),
    PRODUCT('*', List<Long>::product);

    companion object {
        fun parse(symbol: Char) = entries.first { it.symbol == symbol }
    }
}