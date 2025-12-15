package aock2020

import shared.frequencies
import shared.sanitize
import shared.splitByEmptyLine

data class Year2020Day06(
    private val groups: List<List<List<Char>>>
) {
    constructor(input: String) : this(
        input.sanitize()
            .splitByEmptyLine()
            .map { group -> group.lines().map { it.toCharArray().toList() } }
    )

    fun partOne() = groups.sumOf { group -> group.flatMap { it.toCharArray().toList() }.toSet().size }

    fun partTwo() = groups.sumOf { group ->
        group.flatMap { it.toCharArray().toList() }
            .frequencies()
            .count { (_, frequency) -> frequency == group.size }
    }
}