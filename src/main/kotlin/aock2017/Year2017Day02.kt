package aock2017

import shared.sanitize
import shared.splitByLine
import shared.toIntegers

data class Year2017Day02(
    private val lines: List<List<Int>>
) {
    constructor(input: String) : this(input.sanitize().splitByLine().map { it.toIntegers() })

    fun partOne(): Int = lines.sumOf { it.max() - it.min() }

    fun partTwo(): Int = lines.map { it.sorted() }
        .sumOf { findDivisor(it) }

    private fun findDivisor(values: List<Int>): Int = values.indices.flatMap { i ->
        ((i + 1) until values.size).map { j -> values[i] to values[j] }
    }
        .filter { it.second % it.first == 0 }
        .map { it.second / it.first }
        .first()
}