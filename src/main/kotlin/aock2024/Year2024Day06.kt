package aock2024

import shared.CharGrid
import shared.Direction
import shared.Point2d


data class Year2024Day06(
    private val grid: CharGrid,
    private val guard: Point2d
) {
    companion object {
        private const val OBSTACLE = '#'
        private const val GUARD = '^'
        private const val PATH = 'X'

        private fun nextDirection(
            grid: CharGrid,
            currentPosition: Point2d,
            currentDirection: Direction
        ): Direction? {
            var newDirection = currentDirection

            for (i in 0..3) {
                val nextPosition = currentPosition + newDirection//.flipVertical()

                if (!grid.contains(nextPosition)) {
                    return null
                }

                if (grid.at(nextPosition) != OBSTACLE) {
                    return newDirection
                }

                newDirection = newDirection.rotateRight()
            }

            return null
        }

        fun findPath(
            grid: CharGrid,
            position: Point2d,
            direction: Direction
        ): List<Point2d> {
            var currentPosition = position
            var currentDirection = direction
            val path = mutableListOf(currentPosition)

            while (grid.contains(currentPosition)) {
                currentDirection = nextDirection(grid, currentPosition, currentDirection) ?: break

                val nextPosition = currentPosition + currentDirection//.flipVertical()

                currentPosition = nextPosition
                grid.set(currentPosition, PATH)

                path += currentPosition
            }

            return path.toList()
        }

        fun containsLoop(
            grid: CharGrid,
            position: Point2d,
            direction: Direction
        ): Boolean {
            var currentPosition = position
            var currentDirection = direction

            val pathMap = mutableMapOf(currentPosition to setOf(currentDirection))

            while (grid.contains(currentPosition)) {
                currentDirection = nextDirection(grid, currentPosition, currentDirection) ?: break

                val nextPosition = currentPosition + currentDirection//.flipVertical()

                if (pathMap[nextPosition]?.contains(currentDirection) == true) {
                    return true
                }

                currentPosition = nextPosition
                grid.set(currentPosition, PATH)

                pathMap += nextPosition to (pathMap[nextPosition] ?: (mutableSetOf<Direction>() + currentDirection))
            }

            return false
        }

    }

    constructor(input: String) : this(CharGrid(input))

    constructor(grid: CharGrid) : this(grid, grid.findAll(GUARD)[0])

    fun partOne() = findPath(grid, guard, Direction.NORTH).toSet().size

    fun partTwo() = findPath(grid, guard, Direction.NORTH).asSequence()
        .distinct()
        .map {
            val newGrid = grid.copy()
            newGrid.set(it, OBSTACLE)
            newGrid
        }
        .count { containsLoop(it, guard, Direction.NORTH) }

}