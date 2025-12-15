package aock2024

import shared.sanitize
import shared.splitByEmptyLine

data class Year2024Day19(
    private val towels: List<String>,
    private val designs: List<String>
) {
    companion object {
        fun toRegex(towels: List<String>) =
            towels.joinToString(separator = ")|(", prefix = "^(?:(", postfix = "))+\$").toRegex()

        fun countPossibilities(design: String, towels: List<String>, cache: MutableMap<String, Long>): Long =
            cache.getOrPut(design) {
                if (design.isEmpty()) {
                    1L
                } else {
                    towels.filter { design.startsWith(it) }
                        .map { design.drop(it.length) }
                        .sumOf { remainingDesign ->
                            countPossibilities(
                                remainingDesign,
                                towels.filter { remainingDesign.contains(it) },
                                cache
                            )
                        }
                }
            }
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].split(", "),
        input[1].lines()
    )

    fun partOne() = designs.count { isPossible(it, towels) }
    fun partTwo() = designs.sumOf { countPossibilities(it, towels, mutableMapOf()) }

    fun isPossible(design: String, towels: List<String>): Boolean = toRegex(towels).matches(design)

}