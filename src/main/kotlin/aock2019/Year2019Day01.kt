package aock2019

import shared.sanitize
import shared.toLongs

data class Year2019Day01(
    private val input: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = input.sumOf { calculateFuel(it) }
    fun partTwo() = input.sumOf { calculateTotalFuel(it) }

    fun calculateFuel(mass: Long) = mass / 3 - 2
    fun calculateTotalFuel(mass: Long): Long {
        var fuel = calculateFuel(mass)
        var totalFuel = 0L

        while (fuel > 0) {
            totalFuel += fuel
            fuel = calculateFuel(fuel)
        }

        return totalFuel
    }
}