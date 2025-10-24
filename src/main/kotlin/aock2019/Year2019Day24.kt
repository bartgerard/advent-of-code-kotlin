package aock2019

import shared.CharGrid
import shared.Dimension
import shared.at

data class Year2019Day24(
    private val grid: CharGrid
) {
    companion object {
        const val BUG = '#'
        const val EMPTY = '.'
        val DIMENSION = Dimension(5, 5)
        val POINTS = DIMENSION.points()
            .associateWith { point -> point.neighbours().filter { DIMENSION.contains(it) } }

        fun biodiversityRating(grid: CharGrid) = grid.findAll(BUG).sumOf { (x, y) -> 1 shl y * 5 + x }

        fun advanceOneMinute(oldGrid: CharGrid): CharGrid {
            val newGrid = CharGrid(DIMENSION, EMPTY)

            POINTS.forEach { (point, neighbours) ->
                val oldSpace = oldGrid.at(point)
                val adjacentBugs = neighbours.map { oldGrid.at(it) }.count { it == BUG }

                when (oldSpace) {
                    BUG if adjacentBugs != 1 -> newGrid.set(point, EMPTY)
                    EMPTY if adjacentBugs in 1..2 -> newGrid.set(point, BUG)
                    else -> newGrid.set(point, oldSpace)
                }
            }

            return newGrid
        }
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(): Int {
        var appearedTwice = false
        val states = mutableSetOf<Int>()
        return generateSequence(grid) { advanceOneMinute(it) }
            .map { biodiversityRating(it) }
            .onEach { rating ->
                if (states.contains(rating)) {
                    appearedTwice = true
                }

                states += rating
            }
            .dropWhile { !appearedTwice }
            .first()
    }

    fun partTwo() = 0L
}