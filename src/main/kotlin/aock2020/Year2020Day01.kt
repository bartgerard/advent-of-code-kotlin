package aock2020

import shared.sanitize
import shared.toLongs

data class Year2020Day01(
    private val input: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne(sum: Long) = findPairsWithSum(sum)
        ?.let { (a, b) -> a * b }

    fun partTwo(sum: Long) = findTriplesWithSum(sum)
        ?.let { (a, b, c) -> a * b * c }

    fun findPairsWithSum(sum: Long) = input.map { Pair(it, sum - it) }
        .firstOrNull { input.contains(it.second) }

    fun findTriplesWithSum(sum: Long) = input.firstNotNullOfOrNull {
        findPairsWithSum(sum - it)
            ?.let { pair -> Triple(it, pair.first, pair.second) }
    }
}