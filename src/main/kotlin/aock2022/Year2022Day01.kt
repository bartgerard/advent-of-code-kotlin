package aock2022

import shared.sanitize
import shared.splitByEmptyLine

data class Year2022Day01(
    private val input: List<List<Long>>
) {
    constructor(input: String) : this(
        input.sanitize()
            .splitByEmptyLine()
            .map { lines -> lines.lines().map { it.toLong() } }
    )

    fun partOne() = input.maxOfOrNull { it.sum() }
    fun partTwo() = input.map { it.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}