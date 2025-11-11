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
        fun parse(line: String) = parse(Json.parseToJsonElement(line))

        private fun parse(node: JsonElement): SnailfishNumber = when (node) {
            is JsonArray -> node.map { parse(it) }.let { Combined(it[0], it[1]) }
            is JsonPrimitive -> Single(node.long)
            else -> throw IllegalArgumentException("Unknown node type: $node")
        }
    }

    fun magnitude(): Long

    operator fun plus(other: SnailfishNumber) = Combined(this, other).also { this.balance() }

    fun balance() {

    }

    data class Single(val value: Long) : SnailfishNumber {
        override fun magnitude() = value

        override fun toString() = value.toString()
    }

    data class Combined(
        val left: SnailfishNumber,
        val right: SnailfishNumber
    ) : SnailfishNumber {
        override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()

        override fun toString() = "[$left,$right]"
    }
}