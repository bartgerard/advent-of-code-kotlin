package ec2025

import shared.zipWithRemainder

data class Year2025Quest06(
    private val input: String
) {
    fun partOne() = input
        .zipWithRemainder()
        .filter { it.first == 'A' }
        .sumOf { (character, remainder) -> remainder.count { it == character.lowercaseChar() } }

    fun partTwo() = input
        .zipWithRemainder()
        .filter { it.first.isUpperCase() }
        .sumOf { (character, remainder) -> remainder.count { it == character.lowercaseChar() } }

    fun partThree(
        repetitions: Int,
        distanceLimit: Int
    ) = input.let { s ->
        val repeated = s.repeat(repetitions)
        repeated.withIndex()
            .filter { (_, character) -> character.isLowerCase() }
            .sumOf { (index, character) ->
                val startIndex = (index - distanceLimit).coerceAtLeast(0)
                val endIndex = (index + distanceLimit + 1).coerceAtMost(repeated.length - 1)
                repeated.substring(startIndex, endIndex)
                    .count { it == character.uppercaseChar() }
            }
    }

}