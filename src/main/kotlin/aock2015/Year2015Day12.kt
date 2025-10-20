package aock2015

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import shared.sanitize
import shared.toIntegers

data class Year2015Day12(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = input.flatMap { it.toIntegers() }
        .sum()

    fun partTwo() = input.flatMap { JSAbacusFramework.parse(it) }
        .sum()
}

interface JSAbacusFramework {
    companion object {

        fun parse(line: String) = parse(Json.parseToJsonElement(line))

        private fun parse(node: JsonElement): List<Int> = when (node) {
            is JsonArray -> node.flatMap { parse(it) }
            is JsonPrimitive -> if (node.content.contains("red")) emptyList() else node.content.toIntegers()
            is JsonObject -> if (node.values.any { it is JsonPrimitive && it.content == "red" }) emptyList() else node.entries.filterNot { it.key == "red" }
                .flatMap { parse(it.value) }
        }
    }
}