package aock2020

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.CharGrid

data class Year2020Day03(
    private val grid: CharGrid
) {
    constructor(input: String) : this(CharGrid(input))

    fun partOne() = countTreesForSlope(Vector2dInt(3, 1))

    fun partTwo() = sequenceOf(
        Vector2dInt(1, 1),
        Vector2dInt(3, 1),
        Vector2dInt(5, 1),
        Vector2dInt(7, 1),
        Vector2dInt(1, 2)
    )
        .map { countTreesForSlope(it) }
        .reduce { a, b -> a * b }

    fun countTreesForSlope(slope: Vector2dInt) = generateSequence(Point2dInt.ZERO) { it + slope }
        .takeWhile { it.y < grid.dimension().height }
        .map { it.mod(grid.dimension()) }
        .count { grid.at(it) == '#' }
        .toLong()
}