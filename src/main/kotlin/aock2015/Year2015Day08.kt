package aock2015

import shared.byLine

data class Year2015Day08(
    private val lines: List<String>
) {
    constructor(input: String) : this(input.byLine())

    fun partOne(): Int = lines.sumOf { it.length - representationLength(it) }

    fun partTwo(): Int = lines.sumOf { encodedLength(it) - it.length }

    private fun representationLength(s: String): Int {
        return s
            .replace("\\\\", "?")
            .replace("\\\"", "?")
            .replace("\\\\x\\w{2}".toRegex(), "?")
            .length - 2
    }

    private fun encodedLength(s: String): Int {
        return s
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .length + 2
    }

}