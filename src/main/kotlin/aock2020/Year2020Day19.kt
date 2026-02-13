package aock2020

import shared.sanitize

data class Year2020Day19(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = 0L
    fun partTwo() = 0L
}