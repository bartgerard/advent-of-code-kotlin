package aock2025

import shared.CharGrid
import shared.Point2d
import shared.Vector2d

data class Year2025Day04(
    private val grid: CharGrid
) {
    companion object {
        const val ROLLS_OF_PAPER = '@'
        const val ACCESSED = 'x'
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(): Int = findMovableRolls().count()

    fun partTwo(): Long {
        var movedCount = 0L

        while (true) {
            val movableRolls = findMovableRolls()

            if (movableRolls.count() == 0) {
                return movedCount
            }

            movableRolls.forEach { moveRoll(it) }
            movedCount += movableRolls.count()
        }
    }

    private fun findMovableRolls() = grid.findAll { it == ROLLS_OF_PAPER }
        .filter { roll -> isMovableRoll(roll) }

    private fun isMovableRoll(roll: Point2d): Boolean = roll.neighbours(Vector2d.SURROUNDING)
        .count { grid.contains(it) && grid.at(it) == ROLLS_OF_PAPER } < 4

    private fun moveRoll(roll: Point2d) {
        grid.set(roll, ACCESSED)
    }
}