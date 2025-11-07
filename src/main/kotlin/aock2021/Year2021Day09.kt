package aock2021

import shared.IntGrid
import shared.Point2d
import shared.product

data class Year2021Day09(
    private val grid: IntGrid
) {
    constructor(input: String) : this(IntGrid(input))

    fun partOne() = lowPoints()
        .map { grid.at(it) + 1 }
        .sum()

    fun partTwo() = lowPoints()
        .map { point ->
            point.allDirectAndIndirectNeighbours { neighbour -> grid.contains(neighbour) && grid.at(neighbour) < 9 }.toSet()
        }
        .distinct()
        .map { it.size }
        .sortedDescending()
        .take(3)
        .product()

    private fun lowPoints(): Sequence<Point2d> = grid.points()
        .filter { point ->
            point.neighbours()
                .filter { grid.contains(it) }
                .all { neighbour -> grid.at(point) < grid.at(neighbour) }
        }
}