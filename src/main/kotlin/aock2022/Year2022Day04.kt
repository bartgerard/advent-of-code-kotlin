package aock2022

import shared.range.contains
import shared.range.overlaps
import shared.sanitize

data class Year2022Day04(
    private val input: List<Pair<LongRange, LongRange>>
) {
    constructor(input: String) : this(
        input.sanitize()
            .lines()
            .map { line ->
                line.split("-", ",")
                    .map { it.toLong() }
                    .let { it[0]..it[1] to it[2]..it[3] }
            }
    )

    fun partOne() = input.count { it.first.contains(it.second) || it.second.contains(it.first) }
    fun partTwo() = input.count { it.first.overlaps(it.second) }
}