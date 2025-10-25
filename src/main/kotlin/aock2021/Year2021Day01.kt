package aock2021

import shared.sanitize
import shared.toLongs

data class Year2021Day01(
    private val input: List<Long>
) {
    companion object {
        fun countIncreased(input: List<Long>): Int = input
            .zipWithNext { a, b -> b - a }
            .count { it > 0 }
    }

    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = countIncreased(input)

    fun partTwo() = input.windowed(3)
        .map { it.sum() }
        .let { countIncreased(it) }

}