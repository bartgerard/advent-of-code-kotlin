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
    }

    constructor(input: String) : this(
        MutableWordGrid(input)
    )

    constructor(grid: MutableWordGrid) : this(
        grid,
        grid.findAll(GUARD)[0]
    )

    fun partOne(): Int = findPath().toSet().size

    private fun findPath(): List<Point2d> {
        var currentPosition = guard
        var currentDirection = Direction.NORTH

        val path = mutableListOf<Point2d>()

        while (grid.contains(currentPosition)) {
            val nextPosition = currentPosition + currentDirection.flipVertical()

            if (!grid.contains(nextPosition)) {
                break
            }

            if (grid.at(nextPosition) == OBSTACLE) {
                currentDirection = currentDirection.rotateRight()
            } else {
                currentPosition = nextPosition
                path += currentPosition
                grid.set(currentPosition, PATH)
            }
        }

        return path.toList()
    }

    private fun containsLoop(grid: MutableWordGrid): Boolean {
        var currentPosition = guard
        var currentDirection = Direction.NORTH

        val path = mutableListOf<Point2d>()

        while (grid.contains(currentPosition)) {
            val nextPosition = currentPosition + currentDirection.flipVertical()

            if (!grid.contains(nextPosition)) {
                break
            }

            if (grid.at(nextPosition) == OBSTACLE) {
                currentDirection = currentDirection.rotateRight()
            } else {
                if (Collections.indexOfSubList(path, listOf(nextPosition, nextPosition)) >= 0) {
                    return true
                }

                currentPosition = nextPosition
                path += currentPosition
                grid.set(currentPosition, PATH)
            }
        }

        return false
    }
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

    fun pathUntilObstacleOrBorder(position: Point2d, direction: Direction): List<Point2d> {
        val path = mutableListOf<Point2d>()

        var currentPosition = position

        while (grid.contains(currentPosition) && grid.at(currentPosition) != OBSTACLE) {
            currentPosition += direction.flipVertical()
            path += currentPosition
        }

        return path.toList()
    }

    fun partTwo(): Int {
        return grid.findAll('.')
            .count {
                val newGrid = grid.copy()
                newGrid.set(it, OBSTACLE)
                containsLoop(newGrid)
            }
    }

    fun partTwoAaah(): Int {
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
}