package aock2021

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.long
import shared.sanitize

data class Year2021Day18(
    private val input: List<SnailfishNumber>
) {
    constructor(input: String) : this(input.sanitize().lines().map { SnailfishNumber.parse(it) })

    fun partOne(): Long = input
        .reduce { acc, number -> acc + number }
        .magnitude()

    fun partTwo() = 0L
}

interface SnailfishNumber {
    companion object {
        @JvmStatic
        fun parse(line: String) = parse(Json.parseToJsonElement(line))

        private fun parse(node: JsonElement): SnailfishNumber = when (node) {
            is JsonArray -> node.map { parse(it) }.let { Combined(it[0], it[1]) }
            is JsonPrimitive -> Single(node.long)
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }

    fun magnitude(): Long

    operator fun plus(other: SnailfishNumber) = Combined(this, other).also { this.balance() }

    fun balance(): SnailfishNumber {
        // If any pair is nested inside four pairs, the leftmost such pair explodes.
        // If any regular number is 10 or greater, the leftmost such regular number splits.

        // To explode a pair, the pair's left value is added to the first regular number to the left of the exploding pair (if any),
        // and the pair's right value is added to the first regular number to the right of the exploding pair (if any).
        // Exploding pairs will always consist of two regular numbers.
        // Then, the entire exploding pair is replaced with the regular number 0.
        return this.balance(0)
    }

    fun balance(depth: Int): SnailfishNumber

    data class Single(val value: Long) : SnailfishNumber {
        override fun magnitude() = value
        override fun balance(depth: Int): SnailfishNumber {
            if (value >= 10) {
                val left = value / 2
                val right = value - left
                return Combined(
                    Single(left),
                    Single(right)
                )
                    .balance(depth + 1)
            } else {
                return this
            }
        }

        override fun toString() = value.toString()
    }

    data class Combined(
        val left: SnailfishNumber,
        val right: SnailfishNumber
    ) : SnailfishNumber {
        override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()
        override fun balance(depth: Int): SnailfishNumber {
            return this
        }

        override fun toString() = "[$left,$right]"
    }
}

data class ExplodeResult(
    val result: SnailfishNumber,
    val leftValue: Long?,
    val rightValue: Long?
)