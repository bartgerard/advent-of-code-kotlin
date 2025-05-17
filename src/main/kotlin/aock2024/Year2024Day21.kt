package aock2024

import shared.CharGrid
import shared.Vector2d
import shared.allShortest
import shared.combinations
import shared.generate
import shared.sanitize
import shared.toIntegers
import kotlin.math.absoluteValue

data class Year2024Day21(
    private val codes: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = codes.sumOf { complexity(it, 2) }

    fun partTwo() = codes.sumOf { complexity(it, 25) }

    private fun complexity(string: String, depth: Int) = numericPart(string) * lengthOfShortestSequence2(string, depth)

    private fun numericPart(code: String) = code.trimStart('0').toIntegers().first()

    private fun lengthOfShortestSequence(code: String, indirections: Int): Int {
        var previous = Keypad.NUMERIC.allPossibleNumericKeypadInstructionsFor(code)

        repeat(indirections) {
            previous = previous.flatMap { Keypad.DIRECTIONAL.allPossibleDirectionalKeypadInstructionsFor(it) }
        }

        return previous.minOf { it.length }
    }

    private fun lengthOfShortestSequence2(code: String, depth: Int): Long {
        val sequences = Keypad.NUMERIC.allPossibleNumericKeypadInstructionsFor(code)

        return sequences.minOf { sequence ->
            "A$sequence".zipWithNext { a, b -> Keypad.DIRECTIONAL.lengthOfShortestSequence(a, b, depth) }.sum()
        }
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
        val CACHE = mutableMapOf<Pair<Pair<Char, Char>, Int>, Long>()
        fun toInstructions(v: Vector2d): String = if (v.x != 0) {
            (if (v.x > 0) ">" else "<").repeat(v.x.absoluteValue)
        } else if ((v.y != 0)) {
            (if (v.y > 0) "v" else "^").repeat(v.y.absoluteValue)
        } else {
            ""
        }

        val SEQUENCES = DIRECTIONAL.layout.values()
            .filter { it != ' ' }
            .combinations()
            .associateWith { (a, b) -> DIRECTIONAL.allPossibleInstructionsBetween(a, b) }

        val LENGTHS = SEQUENCES.entries
            .associate { it.key to it.value.first().length.toLong() }
    }

    private fun allPossibleInstructionsBetween(from: Char, to: Char): List<String> {
        val p1 = layout.findAll(from).first()
        val p2 = layout.findAll(to).first()
        val v = p2 - p1
        return v.orthogonalOptions()
            .filter { layout.at(p1 + it[0]) != ' ' }
            .map { it.joinToString(separator = "", postfix = "A") { toInstructions(it) } }
    }

    fun allPossibleNumericKeypadInstructionsFor(code: String) =
        "A$code".zipWithNext { key1, key2 -> allPossibleInstructionsBetween(key1, key2) }
            .generate()
            .allShortest()

    fun allPossibleDirectionalKeypadInstructionsFor(code: String) =
        "A$code".zipWithNext { key1, key2 -> SEQUENCES[key1 to key2]!! }
            .generate()
            .allShortest()

    fun lengthOfShortestSequence(key1: Char, key2: Char, depth: Int): Long = CACHE.getOrPut((key1 to key2) to depth) {
        if (depth == 1) {
            LENGTHS[key1 to key2]!!
        } else {
            SEQUENCES[key1 to key2]!!.minOf { sequence ->
                "A${sequence}".zipWithNext { a, b -> lengthOfShortestSequence(a, b, depth - 1) }.sum()
            }
        }
    }
}