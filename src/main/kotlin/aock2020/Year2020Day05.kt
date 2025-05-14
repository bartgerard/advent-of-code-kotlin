package aock2020

import shared.sanitize

data class Year2020Day05(
    private val input: List<BinarySpace>
) {
    constructor(input: String) : this(input.sanitize().lines().map { BinarySpace(it) })

    fun partOne() = input.maxOfOrNull { it.seatId() }
    fun partTwo() = occupiedSeatIds()
        .windowed(2)
        .firstOrNull { it[0] == it[1] - 2 }
        ?.let { it[0] + 1 }

    private fun occupiedSeatIds(): List<Int> = input.map { it.seatId() }.sorted()
}

data class BinarySpace(
    private val value: String
) {
    fun seatId() = value
        .replace("F", "0")
        .replace("B", "1")
        .replace("L", "0")
        .replace("R", "1")
        .toInt(radix = 2)
}