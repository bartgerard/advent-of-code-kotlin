package aock2019

import shared.sanitize

data class Year2019Day04(
    private val range: IntRange
) {
    companion object {
        private fun isValidPassword(password: String) = hasValidSize(password)
                && isIncreasing(password)
                && containsAtLeastOneAdjacentPair(password)

        private fun isValidPasswordExtended(password: String) = isValidPassword(password)
                && !triples(password).containsAll(doubles(password))

        private fun hasValidSize(password: String) = password.length == 6
        private fun isIncreasing(password: String) = password.zipWithNext().all { (x, y) -> x <= y }
        private fun containsAtLeastOneAdjacentPair(password: String) = password.zipWithNext().any { (x, y) -> x == y }

        private fun doubles(password: String) = password.zipWithNext()
            .filter { (x, y) -> x == y }
            .map { (x, _) -> x }
            .toSet()

        private fun triples(password: String) = password.windowed(3)
            .map { it.toSet() }
            .filter { it.size == 1 }
            .map { it.first() }
            .toSet()
    }

    constructor(input: String) : this(
        input.sanitize().lines().first().split("-")
            .let { (start, end) -> IntRange(start.toInt(), end.toInt()) }
    )

    fun partOne() = range.count { isValidPassword(it.toString()) }

    fun partTwo() = range.count { isValidPasswordExtended(it.toString()) }
}