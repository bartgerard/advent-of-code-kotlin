package aock2021

import shared.Dijkstra
import shared.Dimension
import shared.IntGrid
import shared.Point2d

data class Year2021Day15(
    private val grid: IntGrid
) {
    companion object {
        val START = Point2d.ZERO
    }

    constructor(input: String) : this(IntGrid(input))

    fun partOne(): Long {
        val dimension = grid.dimension()
        val end = dimension.bottomRight()

        return Dijkstra.findShortestPath(
            START,
            { it == end },
            { _, current -> current.neighbours().filter { dimension.contains(it) } },
            { _, neighbor -> grid.at(neighbor).toLong() }
        )
            .cost()
    }

    fun partTwo(): Long {
        val dimension = grid.dimension()
        val extendedDimension = dimension * 5
        val end = extendedDimension.bottomRight()

        return Dijkstra.findShortestPath(
            START,
            { it == end },
            { _, current -> current.neighbours().filter { extendedDimension.contains(it) } },
            { _, neighbor -> risk(neighbor, dimension) }
        )
            .cost()
    }

    private fun risk(
        neighbor: Point2d,
        dimension: Dimension
    ): Long {
        val tileX = neighbor.x / dimension.width
        val tileY = neighbor.y / dimension.height
        val originalRisk = grid.at(neighbor.mod(dimension))
        val adjustedRisk = (originalRisk - 1 + tileX + tileY) % 9 + 1
        return adjustedRisk.toLong()
    }
}