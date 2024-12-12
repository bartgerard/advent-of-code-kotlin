package aock2015

import shared.sanitize

data class Year2015Day1x(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne(): Long = 0
    fun partTwo(): Long = 0
}