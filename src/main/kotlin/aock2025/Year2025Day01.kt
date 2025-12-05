package aock2025

import shared.sanitize
import kotlin.math.absoluteValue

data class Year2025Day01(
    private val input: List<Int>
) {
    companion object {
        const val START = 50
        const val DIAL_NUMBERS = 100
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .map { (if (it[0] == 'L') -1 else 1) * it.substring(1).toInt() }
            .toList()
    )

    fun partOne() = input.scan(START) { dialPosition, clicks -> dialPosition.rotate(clicks) }
        .count { it == 0 }

    fun partTwo(): Long = input.fold(START to 0L) { (dialPosition, zeroCount), clicks ->
        val fullRotations = ((dialPosition + clicks) / DIAL_NUMBERS).absoluteValue
        val zeroCrossing = if (dialPosition != 0 && clicks <= -dialPosition) 1L else 0L

        dialPosition.rotate(clicks) to zeroCount + fullRotations + zeroCrossing
    }
        .second

    private fun Int.rotate(clicks: Int) = (this + clicks).mod(DIAL_NUMBERS)

    fun partTwoV1(): Long {
        var dialPosition = START
        var zeroCount = 0L

        input.forEach {
            val range = if (it > 0) {
                1..it
            } else {
                -1 downTo it
            }

            for (i in range) {
                if (dialPosition.rotate(i) == 0) {
                    zeroCount++
                }
            }

            dialPosition = dialPosition.rotate(it)
        }

        return zeroCount
    }
}