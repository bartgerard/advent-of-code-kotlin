package aock2025

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.CharGrid

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

    private fun isMovableRoll(roll: Point2dInt): Boolean = roll.neighbours(Vector2dInt.SURROUNDING)
        .count { grid.contains(it) && grid.at(it) == ROLLS_OF_PAPER } < 4

    private fun moveRoll(roll: Point2dInt) {
        grid.set(roll, ACCESSED)
    }
}