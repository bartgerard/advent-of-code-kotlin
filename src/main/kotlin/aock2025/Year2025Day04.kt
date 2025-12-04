package aock2025

import shared.CharGrid
import shared.Vector2d

data class Year2025Day04(
    private val grid: CharGrid
) {
    companion object {
        const val ROLLS_OF_PAPER = '@'
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(): Int = findMovableRolls().count()

    fun partTwo(): Long {
        var total = 0L
        while (true) {
            val movableRolls = findMovableRolls()
            if (movableRolls.count() == 0) {
                return total
            }
            movableRolls.forEach { roll -> grid.set(roll, 'x') }

            total += movableRolls.count()
        }
    }

    private fun findMovableRolls() = grid.findAll { it == ROLLS_OF_PAPER }.filter { point ->
        point.neighbours(Vector2d.SURROUNDING).count { grid.contains(it) && grid.at(it) == ROLLS_OF_PAPER } < 4
    }
}