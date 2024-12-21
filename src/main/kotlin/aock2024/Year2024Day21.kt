package aock2024

import shared.*
import kotlin.math.absoluteValue

data class Year2024Day21(
    private val codes: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = codes.sumOf { complexity(it, 2) }

    fun partTwo() = codes.sumOf { complexity(it, 25) }

    private fun complexity(string: String, indirections: Int): Int = numericPart(string) * lengthOfShortestSequence(string, indirections)

    fun numericPart(code: String) = code.trimStart('0').toIntegers().first()

    fun lengthOfShortestSequence(code: String, indirections: Int): Int {
        var previous = Keypad.NUMERIC.allPossibleInstructionsFor(code)

        repeat(indirections) {
            previous = previous.flatMap { Keypad.DIRECTIONAL.allPossibleInstructionsFor(it) }
        }

        return previous.minOf { it.length }
    }
}

enum class Keypad(
    val layout: CharGrid
) {
    NUMERIC(
        CharGrid("789\n456\n123\n 0A")
    ),
    DIRECTIONAL(
        CharGrid(" ^A\n<v>")
    );

    companion object {
        val CACHE = mutableMapOf<String, List<String>>()
        fun toInstructions(v: Vector2d): String = if (v.x != 0) {
            (if (v.x > 0) ">" else "<").repeat(v.x.absoluteValue)
        } else if ((v.y != 0)) {
            (if (v.y > 0) "v" else "^").repeat(v.y.absoluteValue)
        } else {
            ""
        }
    }

    fun allPossibleInstructionsBetween(from: Char, to: Char): List<String> {
        val p1 = layout.findAll(from).first()
        val p2 = layout.findAll(to).first()
        val v = p2 - p1
        return v.orthogonalOptions()
            .filter { layout.at(p1 + it[0]) != ' ' }
            .map { it.joinToString(separator = "", postfix = "A") { toInstructions(it) } }
    }

    fun allPossibleInstructionsFor(code: String) = "A$code".zipWithNext { key1, key2 -> allPossibleInstructionsBetween(key1, key2) }
        .generate()
        .allShortest()

    fun lengthOfShortestSequence(from: Char, to: Char, repetition: Int): Int {
        return  0
    }
}

fun main() {
    Keypad.DIRECTIONAL.lengthOfShortestSequence('<', '>', 25)
}