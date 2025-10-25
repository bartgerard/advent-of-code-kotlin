package aock2018

import shared.firstRepeated
import shared.repeatForever
import shared.sanitize
import shared.toLongs

data class Year2018Day01(
    private val input: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = input.fold(0L) { acc, value -> acc + value }
    fun partTwo() = input.repeatForever()
        .scan(0L) { acc, value -> acc + value }
        .firstRepeated(2)
}