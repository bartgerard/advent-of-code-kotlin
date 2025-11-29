package ec2025

import shared.CharGrid
import shared.Point2d
import shared.Vector2d

data class Year2025Quest14(
    private val grid: CharGrid
) {
    companion object {
        const val ACTIVE = '#'
        const val INACTIVE = '.'
        private fun countActive(grid: CharGrid) = grid.findAll(ACTIVE).size
        private fun isActive(grid: CharGrid, point: Point2d) = grid.at(point) == ACTIVE
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne() = run(10)
    fun partTwo() = run(2025)
    fun partThree() = 0L

    private fun run(times: Int): Long {
        var current = grid.copy()
        var total = 0L
        repeat(times) {
            current = step(current)
            total += countActive(current)
        }
        return total
    }

    private fun step(grid: CharGrid): CharGrid {
        val next = grid.copy()
        grid.points().forEach { point ->
            val activeNeighbors = point.neighbours(Vector2d.DIAGONAL_ADJACENT)
                .filter { grid.contains(it) && isActive(grid, it) }
                .size
            val nextValue = when {
                isActive(grid, point).xor(activeNeighbors % 2 == 0) -> ACTIVE
                else -> INACTIVE
            }
            next.set(point, nextValue)
        }
        return next
    }
}