package aock2024

import shared.sanitize

data class Year2024Day1x(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = 0
    fun partTwo() = 0
}