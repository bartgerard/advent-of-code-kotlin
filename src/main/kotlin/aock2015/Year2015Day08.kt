package aock2015

import shared.sanitize

data class Year2015Day08(
    private val lines: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = lines.sumOf { it.length - representationLength(it) }

    fun partTwo() = lines.sumOf { encodedLength(it) - it.length }

    private fun representationLength(s: String) = s
        .replace("\\\\", "?")
        .replace("\\\"", "?")
        .replace("\\\\x\\w{2}".toRegex(), "?")
        .length - 2

    private fun encodedLength(s: String) = s
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .length + 2

}