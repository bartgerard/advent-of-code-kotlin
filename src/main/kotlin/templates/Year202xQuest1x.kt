package templates

import shared.sanitize

data class Year202xQuest1x(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = 0L
    fun partTwo() = 0L
    fun partThree() = 0L
}