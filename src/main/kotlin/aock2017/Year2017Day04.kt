package aock2017

import shared.frequencies
import shared.sanitize

data class Year2017Day04(
    private val input: List<List<String>>
) {
    constructor(input: String) : this(input.sanitize().lines().map { it.split(" ") })

    fun partOne() = input.count { line -> line.size == line.toSet().size }
    fun partTwo() = input.count { line -> line.size == line.map { it.frequencies() }.toSet().size }
}