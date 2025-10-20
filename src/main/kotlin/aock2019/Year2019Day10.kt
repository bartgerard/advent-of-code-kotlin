package aock2019

import shared.CharGrid
import shared.Point2d

data class Year2019Day10(
    private val input: CharGrid,
    private val asteroids: List<Point2d>
) {
    constructor(input: String) : this(CharGrid(input))
    constructor(grid: CharGrid) : this(grid, grid.findAll('#'))

    fun partOne() = asteroids.maxOf { countVisibleAsteroidsFrom(it) }

    fun partTwo() = 0L

    fun countVisibleAsteroidsFrom(point: Point2d): Long {
        return 0L
    }
}