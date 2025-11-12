package ec2025

import shared.prefixes
import shared.sanitize
import shared.splitByEmptyLine

data class Year2025Quest07(
    private val names: List<String>,
    private val mappings: Map<Char, Set<Char>>
) {
    companion object {
        const val MIN_LENGTH = 7
        const val MAX_LENGTH = 11
    }

    private val cache = mutableMapOf<Pair<Char, Int>, Long>()

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input.first().split(","),
        input.last().lines()
            .associate { line ->
                line.split(" > ")
                    .let { (from, to) -> from[0] to to.split(",").map { it[0] }.toSet() }
            }
    )

    fun partOne() = filterCompliantNames().first().value

    fun partTwo() = filterCompliantNames()
        .sumOf { (index, _) -> index + 1 }

    fun partThree() = filterCompliantNames()
        .map { (_, name) -> name }
        .prefixes()
        .sumOf { prefix -> countOptionsFor(prefix.last(), prefix.length) }

    private fun filterCompliantNames(): List<IndexedValue<String>> = names.withIndex()
        .filter { (_, name) ->
            name.zipWithNext()
                .all { (a, b) -> mappings[a]?.contains(b) ?: false }
        }

    fun countOptionsFor(
        start: Char,
        length: Int
    ): Long = cache.getOrPut(start to length) {
        when {
            length > MAX_LENGTH -> 0L
            length == MAX_LENGTH -> 1L
            else -> {
                val options = mappings[start]
                    ?.sumOf { nextChar -> countOptionsFor(nextChar, length + 1) }
                    ?: 0L

                if (length >= MIN_LENGTH) {
                    options + 1L
                } else {
                    options
                }
            }
        }
    }
}