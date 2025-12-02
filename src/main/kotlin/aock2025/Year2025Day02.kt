package aock2025

import shared.sanitize
import shared.toLongRanges

data class Year2025Day02(
    private val input: List<LongRange>
) {
    companion object {
        val REPEATED_ONCE = "(\\d+)\\1".toRegex()
        val REPEATED_AT_LEAST_TWICE = "(\\d+)\\1+".toRegex()
    }

    constructor(input: String) : this(input.sanitize().toLongRanges())

    fun partOne() = filterByPattern(REPEATED_ONCE).sum()

    fun partTwo() = filterByPattern(REPEATED_AT_LEAST_TWICE).sum()

    private fun filterByPattern(pattern: Regex) = input.flatMap { id ->
        id.filter { it.toString().matches(pattern) }
    }
}