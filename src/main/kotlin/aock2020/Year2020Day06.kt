package aock2020

import shared.sanitize

data class Year2020Day06(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())
    /*
    constructor(input: String) : this(
        input.sanitize().splitByEmptyLine()
            .map { line -> line.lines().map { it.toCharArray() } }
    )
     */

    fun partOne() = 0L
    fun partTwo() = 0L
}