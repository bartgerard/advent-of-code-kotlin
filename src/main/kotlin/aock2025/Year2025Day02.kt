package aock2025

import shared.NumberPatterns
import shared.sanitize
import shared.toLongRanges

data class Year2025Day02(
    private val input: List<LongRange>
) {
    constructor(input: String) : this(input.sanitize().toLongRanges())

    fun partOne() = filterByPattern(NumberPatterns.PALINDROME).sum()

    fun partTwo() = filterByPattern(NumberPatterns.REPEATED_AT_LEAST_TWICE).sum()

    private fun filterByPattern(pattern: Regex) = input.flatMap { id ->
        id.filter { it.toString().matches(pattern) }
    }
}