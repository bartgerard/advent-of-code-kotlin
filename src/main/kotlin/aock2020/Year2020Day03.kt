package aock2020

import shared.CharGrid
import shared.Point2d
import shared.Vector2d

data class Year2020Day03(
    private val grid: CharGrid
) {
    constructor(input: String) : this(CharGrid(input))

    fun partOne() = countTreesForSlope(Vector2d(3, 1))

    fun partTwo() = sequenceOf(
        Vector2d(1, 1),
        Vector2d(3, 1),
        Vector2d(5, 1),
        Vector2d(7, 1),
        Vector2d(1, 2)
    )
        .map { countTreesForSlope(it) }
        .reduce { a, b -> a * b }

    fun countTreesForSlope(slope: Vector2d) = generateSequence(Point2d.ZERO) { it + slope }
        .takeWhile { it.y < grid.dimension().height }
        .map { it.mod(grid.dimension()) }
        .count { grid.at(it) == '#' }
        .toLong()
}