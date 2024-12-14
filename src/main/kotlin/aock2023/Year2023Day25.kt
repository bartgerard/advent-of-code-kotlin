package aock2023

import shared.sanitize

data class Year2023Day25(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = 0
    fun partTwo() = 0
}