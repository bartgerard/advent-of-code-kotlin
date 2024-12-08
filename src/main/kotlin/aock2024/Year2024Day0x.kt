package aock2024

import shared.sanitize

data class Year2024Day0x(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne(): Int = 0
    fun partTwo(): Int = 0
}