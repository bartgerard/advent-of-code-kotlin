package aock2021

import shared.median
import shared.sanitize
import shared.toLongs
import shared.triangular
import kotlin.math.abs

data class Year2021Day07(
    private val input: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = input.sumOf { abs(it - input.median()) }

    fun partTwo() = (input.min()..input.max())
        .minOf { position ->
            input.sumOf { crab ->
                triangular(abs(crab - position))
            }
        }

}