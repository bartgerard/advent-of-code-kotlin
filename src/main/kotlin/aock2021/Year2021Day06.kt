package aock2021

import shared.frequencies
import shared.sanitize
import shared.toIntegers

data class Year2021Day06(
    private val input: Map<Int, Long>
) {
    constructor(input: String) : this(
        input.sanitize()
            .toIntegers()
            .frequencies()
            .mapValues { (_, count) -> count.toLong() }
    )

    fun partOne(days: Int) = simulateDays(days)

    private fun simulateDays(days: Int): Long {
        var population = input

        repeat(days) {
            population = ageOneDay(population)
        }

        return population.values.sum()
    }

    private fun ageOneDay(
        currentPopulation: Map<Int, Long>
    ) = buildMap {
        (0..8).forEach { internalTimer ->
            val fishesWithInternalTimer = currentPopulation[internalTimer] ?: 0L
            when (internalTimer) {
                0 -> {
                    this[6] = fishesWithInternalTimer
                    this[8] = fishesWithInternalTimer
                }

                else -> {
                    val newTimer = internalTimer - 1
                    this[newTimer] = (this[newTimer] ?: 0L) + fishesWithInternalTimer
                }
            }
        }
    }
}