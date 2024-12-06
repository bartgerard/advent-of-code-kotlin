package aock2017

import shared.sanitize

data class Year2017Day01(
    private val input: List<Int>
) {
    constructor(input: String) : this(input.sanitize().chunked(1).map { it.toInt() })

    fun partOne(): Int = (input + input.first()).zipWithNext { x, y -> x to y }
        .filter { it.first == it.second }
        .sumOf { it.first }

    fun partTwo(): Int = input.mapIndexed { index, value -> value to input[(index + input.size / 2) % input.size] }
        .filter { it.first == it.second }
        .sumOf { it.first }
}