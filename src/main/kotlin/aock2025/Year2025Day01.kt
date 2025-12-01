package aock2025

import shared.floorMod
import shared.sanitize

data class Year2025Day01(
    private val input: List<Int>
) {
    companion object {
        const val START = 50
        const val DIAL_NUMBERS = 100
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .map { (if (it[0] == 'R') 1 else -1) * it.substring(1).toInt() }
            .toList()
    )

    fun partOne(): Int = input.scan(START) { acc, clicks -> acc.rotate(clicks) }
        .filter { it == 0 }
        .size

    fun partTwo(): Long {
        var acc = START
        var count = 0L

        input.forEach {
            val range = if (it > 0) {
                1..it
            } else {
                -1 downTo it
            }

            for (i in range) {
                if (acc.rotate(i) == 0) {
                    count++
                }
            }

            acc = acc.rotate(it)
        }

        return count
    }

    private fun Int.rotate(clicks: Int) = (this + clicks).floorMod(DIAL_NUMBERS)
}