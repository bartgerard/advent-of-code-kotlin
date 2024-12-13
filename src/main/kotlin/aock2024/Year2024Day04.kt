package aock2024

import shared.Direction.*
import shared.CharGrid
import shared.Vector2d

data class Year2024Day04(
    private val grid: CharGrid
) {
    companion object {
        const val WORD = "XMAS"
        val M_AND_S = setOf('M', 'S')
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne() = grid.countOccurrences(WORD, Vector2d.SURROUNDING)

    fun partTwo() = grid.findAll('A')
        .count { point ->
            Vector2d.DIAGONAL_ADJACENT.all { grid.contains(point + it) }
                    && setOf(grid.at(point + NORTH_WEST), grid.at(point + SOUTH_EAST)).containsAll(M_AND_S)
                    && setOf(grid.at(point + NORTH_EAST), grid.at(point + SOUTH_WEST)).containsAll(M_AND_S)
        }
}