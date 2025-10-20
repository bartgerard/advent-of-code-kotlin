package aock2022

import shared.Alphabet
import shared.sanitize
import shared.splitIn

data class Year2022Day03(
    private val input: List<String>
) {
    companion object {
        fun score(c: Char) = Alphabet.positionOf(c) + if (c.isLowerCase()) 0 else 26
    }

    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = input.sumOf {
        val pockets = it.splitIn(2)
        score(pockets[0].first { c -> c in pockets[1] })
    }

    fun partTwo() = input.chunked(3)
        .sumOf { (backpack1, backpack2, backpack3) -> score(backpack1.first { it in backpack2 && it in backpack3 }) }
}