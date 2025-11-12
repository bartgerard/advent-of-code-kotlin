package aock2021

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.long
import shared.combinations
import shared.sanitize

data class Year2021Day18(
    private val input: List<SnailfishNumber>
) {
    constructor(input: String) : this(input.sanitize().lines().map { SnailfishNumber.parse(it) })

    fun partOne(): Long = input
        .reduce { acc, number -> acc + number }
        .magnitude()

    fun partTwo() = input.toSet()
        .combinations()
        .maxOf { (a, b) -> (a + b).magnitude() }
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

    operator fun plus(other: SnailfishNumber) = Combined(this, other).balance()

    fun balance(): SnailfishNumber {
        var current = this

        while (current.explode(0)?.also { current = it.result } != null
            || current.split()?.also { current = it } != null
        ) {
            continue
        }

        return current
    }

    fun explode(depth: Int): ExplodeResult?
    fun addToLeftmost(value: Long): SnailfishNumber
    fun addToRightmost(value: Long): SnailfishNumber

    fun split(): SnailfishNumber?

    data class Single(val value: Long) : SnailfishNumber {
        override fun magnitude() = value

        override fun explode(depth: Int): ExplodeResult? = null

        override fun addToLeftmost(value: Long) = Single(this.value + value)
        override fun addToRightmost(value: Long) = Single(this.value + value)

        override fun split(): SnailfishNumber? = if (value >= 10) {
            val left = value / 2
            val right = value - left
            Combined(Single(left), Single(right))
        } else {
            null
        }

        override fun toString() = value.toString()
    }

    data class Combined(
        val left: SnailfishNumber,
        val right: SnailfishNumber
    ) : SnailfishNumber {
        override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()

        override fun explode(depth: Int): ExplodeResult? {
            if (depth == 4 && left is Single && right is Single) {
                return ExplodeResult(
                    result = Single(0),
                    leftValue = left.value,
                    rightValue = right.value
                )
            }

            return left.explode(depth + 1)
                ?.let { leftExplode ->
                    val newRight = leftExplode.rightValue
                        ?.let { right.addToLeftmost(leftExplode.rightValue) }
                        ?: right

                    return ExplodeResult(
                        result = Combined(
                            leftExplode.result,
                            newRight
                        ),
                        leftValue = leftExplode.leftValue,
                        rightValue = null
                    )
                }
                ?: right.explode(depth + 1)
                    ?.let { rightExplode ->
                        return ExplodeResult(
                            result = Combined(
                                rightExplode.leftValue
                                    ?.let { left.addToRightmost(rightExplode.leftValue) }
                                    ?: left,
                                rightExplode.result
                            ),
                            leftValue = null,
                            rightValue = rightExplode.rightValue
                        )
                    }
        }

        override fun addToLeftmost(value: Long) = Combined(left.addToLeftmost(value), right)
        override fun addToRightmost(value: Long) = Combined(left, right.addToRightmost(value))

        override fun split(): SnailfishNumber? {
            val leftSplit = left.split()

            if (leftSplit != null) {
                return Combined(leftSplit, right)
            }

            val rightSplit = right.split()

            if (rightSplit != null) {
                return Combined(left, rightSplit)
            }

            return null
        }

        override fun toString() = "[$left,$right]"
    }
}

data class ExplodeResult(
    val result: SnailfishNumber,
    val leftValue: Long?,
    val rightValue: Long?
)