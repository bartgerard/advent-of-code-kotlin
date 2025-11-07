package aock2021

import shared.middle
import shared.sanitize

data class Year2021Day10(
    private val input: List<String>
) {
    companion object {
        val pairs = mapOf(
            '(' to ')',
            '<' to '>',
            '{' to '}',
            '[' to ']'
        )
        val pointsByIllegalCharacter = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )
        val pointsByCompletingCharacter = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4
        )
    }

    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = input.mapNotNull { it.firstIllegalCharacter() }
        .sumOf { pointsByIllegalCharacter[it] ?: 0 }

    fun partTwo() = input.mapNotNull { it.complete() }
        .map { chars ->
            chars.mapNotNull { pointsByCompletingCharacter[it] }
                .fold(0L) { acc, points -> acc * 5 + points }
        }
        .sorted()
        .middle()

    private fun String.firstIllegalCharacter(): Char? {
        val stack = mutableListOf<Char>()

        for (char in this) {
            if (char in pairs.keys) {
                stack.add(char)
            } else if (char in pairs.values) {
                val lastOpened = stack.removeLastOrNull() ?: return char
                if (pairs[lastOpened] != char) {
                    return char
                }
            }
        }

        return null
    }

    private fun String.complete(): List<Char>? {
        val stack = mutableListOf<Char>()

        for (char in this) {
            if (char in pairs.keys) {
                stack.add(char)
            } else if (char in pairs.values) {
                val lastOpened = stack.removeLastOrNull() ?: return null
                if (pairs[lastOpened] != char) {
                    return null
                }
            }
        }

        return stack.reversed().mapNotNull { pairs[it] }
    }
}