package aock2023

import org.apache.commons.lang3.Validate.isTrue
import org.apache.commons.math3.util.ArithmeticUtils.pow
import shared.geometry2d.Point2dInt
import shared.grid.CharGrid

data class Year2023Day21(
    private val grid: CharGrid
) {
    companion object {
        const val START = 'S'
        const val WALL = '#'
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(steps: Int): Int = countReachablePlots(grid.findAll(START).first(), steps)

    fun partTwo(steps: Int): Int = countReachablePlotsInInfiniteGrid(grid.findAll(START).first(), steps)

    /*
    Requirement:
    - grid should have an odd width and height
    - start should be in the middle of the grid
    - first, middle and last rows and columns should be empty
     */
    fun partTwoCyclic(steps: Long): Long {
        val start = grid.findAll(START).first()
        val dimension = grid.dimension()
        val size = dimension.width

        isTrue(start.x == size / 2 && start.y == size / 2)
        isTrue(dimension.width == dimension.height)
        isTrue(size % 2L == 1L)
        isTrue(steps % size == size / 2L)


        val gridWidth = steps / size - 1L // subtract starting grid
        val odd = pow(gridWidth / 2 * 2 + 1, 2)
        val even = pow((gridWidth + 1) / 2 * 2, 2)

        val oddPoints = countReachablePlots(
            start,
            size * 2 + 1
        ) // sufficient large number to reach all points in grid at an odd number of steps
        val evenPoints = countReachablePlots(
            start,
            size * 2
        ) // sufficient large number to reach all points in grid at an even number of steps

        val pointsTop = countReachablePlots(
            Point2dInt(start.x, size - 1),
            size - 1
        ) // start at bottom row, number of steps to reach top is size - 1
        val pointsRight = countReachablePlots(Point2dInt(0, start.y), size - 1) // ..
        val pointsBottom = countReachablePlots(Point2dInt(start.x, 0), size - 1) // ..
        val pointsLeft = countReachablePlots(Point2dInt(size - 1, start.y), size - 1) // ..

        val pointsSmallTopRight = countReachablePlots(Point2dInt(0, size - 1), size / 2 - 1)
        val pointsSmallTopLeft = countReachablePlots(Point2dInt(size - 1, size - 1), size / 2 - 1)
        val pointsSmallBottomRight = countReachablePlots(Point2dInt(0, 0), size / 2 - 1)
        val pointsSmallBottomLeft = countReachablePlots(Point2dInt(size - 1, 0), size / 2 - 1)

        val pointsLargeTopRight = countReachablePlots(Point2dInt(0, size - 1), size * 3 / 2 - 1)
        val pointsLargeTopLeft = countReachablePlots(Point2dInt(size - 1, size - 1), size * 3 / 2 - 1)
        val pointsLargeBottomRight = countReachablePlots(Point2dInt(0, 0), size * 3 / 2 - 1)
        val pointsLargeBottomLeft = countReachablePlots(Point2dInt(size - 1, 0), size * 3 / 2 - 1)

        return (odd * oddPoints
                + even * evenPoints
                + pointsTop
                + pointsRight
                + pointsBottom
                + pointsLeft
                + (gridWidth + 1) * (pointsSmallTopRight + pointsSmallTopLeft + pointsSmallBottomRight + pointsSmallBottomLeft)
                + (gridWidth) * (pointsLargeTopRight + pointsLargeTopLeft + pointsLargeBottomRight + pointsLargeBottomLeft))
    }

    fun countReachablePlots(start: Point2dInt, steps: Int): Int {
        var points = setOf(start)

        repeat(steps) {
            points = points.flatMap { it.neighbours() }
                .filter { grid.contains(it) }
                .filter { grid.at(it) != WALL }
                .toSet()
        }

        return points.size
    }

    fun countReachablePlotsInInfiniteGrid(start: Point2dInt, steps: Int): Int {
        val dimension = grid.dimension()
        var points = setOf(start)

        repeat(steps) {
            points = points.flatMap { it.neighbours() }
                .filter { grid.at(it.mod(dimension)) != WALL }
                .toSet()
        }

        return points.size
    }
}