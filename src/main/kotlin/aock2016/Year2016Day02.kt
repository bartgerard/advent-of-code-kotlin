package aock2016

import shared.Direction
import shared.sanitize

data class Year2016Day02(
    private val input: List<List<Direction>>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { line -> line.map { Direction.parse(it) } })

    fun partOne() = 0L
    fun partTwo() = 0L
}