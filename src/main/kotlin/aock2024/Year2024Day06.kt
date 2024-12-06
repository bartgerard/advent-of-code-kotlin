package aock2024

import shared.Direction
import shared.MutableWordGrid
import shared.Point2d
import java.util.*


data class Year2024Day06(
    private val grid: MutableWordGrid,
    private val guard: Point2d
) {
    companion object {
        private const val OBSTACLE = '#'
        private const val GUARD = '^'
        private const val PATH = 'X'

        private fun nextDirection(
            grid: MutableWordGrid,
            currentPosition: Point2d,
            currentDirection: Direction
        ): Direction? {
            var newDirection = currentDirection

            for (i in 0..3) {
                val nextPosition = currentPosition + newDirection.flipVertical()

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

        private fun findPath(
            grid: MutableWordGrid,
            position: Point2d,
            direction: Direction
        ): Path {
            var currentPosition = position
            var currentDirection = direction
            val path = mutableListOf(currentPosition)

            while (grid.contains(currentPosition)) {
                currentDirection = nextDirection(grid, currentPosition, currentDirection) ?: break

                val nextPosition = currentPosition + currentDirection.flipVertical()

                if (!grid.contains(nextPosition)) {
                    break
                }

                if (path.contains(nextPosition)) {
                    val candidate = currentPosition + currentDirection.flipVertical()

                    val hypotheticalNextPoint = if (grid.at(candidate) == OBSTACLE) {
                        currentPosition + currentDirection.rotateRight().flipVertical()
                    } else {
                        candidate
                    }

                    val index = Collections.indexOfSubList(path, listOf(nextPosition, hypotheticalNextPoint))

                    if (index >= 0) {
                        return Path(path.toList(), index)
                    }
                }

                currentPosition = nextPosition
                path += currentPosition
                grid.set(currentPosition, PATH)

                println(grid.toString())
                println()
            }

            return Path(path.toList(), -1)
        }

    }

    constructor(input: String) : this(MutableWordGrid(input))

    constructor(grid: MutableWordGrid) : this(grid, grid.findAll(GUARD)[0])

    fun partOne(): Int = findPath(grid, guard, Direction.NORTH).path.toSet().size

    fun partTwo(): Int {
        var currentPosition = guard
        var currentDirection = Direction.NORTH

        val path = mutableListOf(currentPosition)
        val candidateObstacles = mutableSetOf<Point2d>()

        while (grid.contains(currentPosition)) {
            val nextPosition = currentPosition + currentDirection.flipVertical()

            if (!grid.contains(nextPosition)) {
                break
            }

            if (grid.at(nextPosition) == OBSTACLE) {
                currentDirection = currentDirection.rotateRight()
                continue
            }

            if (grid.at(nextPosition) in setOf(PATH, GUARD)) {
                val candidate = nextPosition + currentDirection.flipVertical()
                val hypotheticalNextPoint = nextPosition + currentDirection.rotateRight().flipVertical()

                if (grid.contains(candidate)
                    && Collections.indexOfSubList(path, listOf(nextPosition, hypotheticalNextPoint)) >= 0
                ) {
                    candidateObstacles += candidate
                    grid.set(candidate, 'O')

                    //println(grid.toString())
                    //println()
                }
            }

            val hypotheticalNextDirection = currentDirection.rotateRight()
            val hypotheticalNextPosition = currentPosition + hypotheticalNextDirection.flipVertical()

            if (grid.contains(hypotheticalNextPosition)) {
                if (findPath(grid, hypotheticalNextPosition, hypotheticalNextDirection).startOfLoopIndex >= 0) {
                    candidateObstacles += nextPosition
                }
            }

            currentPosition = nextPosition
            path += currentPosition

            if (grid.at(nextPosition) == '.') {
                grid.set(currentPosition, PATH)
            }
        }

        return candidateObstacles.size
    }

    data class Path(
        val path: List<Point2d>,
        val startOfLoopIndex: Int
    )

    /*
        fun partTwo(): Int {
            var currentPosition = guard
            var currentDirection = Direction.NORTH

            val path = mutableListOf(currentPosition)
            val candidateObstacles = mutableSetOf<Point2d>()

            while (grid.contains(currentPosition)) {
                val nextPosition = currentPosition + currentDirection.flipVertical()

                if (!grid.contains(nextPosition)) {
                    break
                }

                if (grid.at(nextPosition) == OBSTACLE) {
                    currentDirection = currentDirection.rotateRight()
                } else {
                    if (grid.at(nextPosition) in setOf(PATH, GUARD)) {
                        val candidate = nextPosition + currentDirection.flipVertical()
                        val hypotheticalNextPoint = nextPosition + currentDirection.rotateRight().flipVertical()

                        if (grid.contains(candidate)
                            && Collections.indexOfSubList(path, listOf(nextPosition, hypotheticalNextPoint)) >= 0
                        ) {
                            candidateObstacles += candidate
                            grid.set(candidate, 'O')

                            println(grid.toString())
                            println()
                        }
                    }

                    currentPosition = nextPosition
                    path += currentPosition

                    if (grid.at(nextPosition) == '.') {
                        grid.set(currentPosition, PATH)
                    }
                }
            }

            return candidateObstacles.size
        }

     */
}