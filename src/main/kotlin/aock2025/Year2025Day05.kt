package aock2025

import shared.range.length
import shared.range.merge
import shared.sanitize
import shared.splitByEmptyLine
import shared.toLongRanges
import shared.toLongs

data class Year2025Day05(
    private val freshIngredientIdRanges: List<LongRange>,
    private val availableIngredientIdRanges: List<Long>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].toLongRanges(),
        input[1].toLongs()
    )

    fun partOne() = availableIngredientIdRanges.count { isFresh(it) }
    fun partTwo() = freshIngredientIdRanges.merge().sumOf { it.length() }

    private fun isFresh(ingredientId: Long) = freshIngredientIdRanges.any { it.contains(ingredientId) }
}