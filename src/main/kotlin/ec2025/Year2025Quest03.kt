package ec2025

import shared.frequencies
import shared.sanitize
import shared.toLongs

data class Year2025Quest03(
    private val input: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = input.toSet().sum()
    fun partTwo() = input.toSet().sorted().take(20).sum()
    fun partThree() = input.frequencies().map { (_, count) -> count }.max()
}