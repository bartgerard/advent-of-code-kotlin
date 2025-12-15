package aock2022

import shared.algorithm.dijkstra.Dijkstra
import shared.geometry2d.Point2dInt
import shared.grid.CharGrid

data class Year2022Day12(
    private val grid: CharGrid,
    private val start: Point2dInt,
    private val end: Point2dInt
) {
    companion object {
        fun elevation(char: Char) = when (char) {
            'S' -> 0
            'E' -> 26
            else -> char.code - 'a'.code
        }
    }

    constructor(input: String) : this(CharGrid(input))
    constructor(grid: CharGrid) : this(
        grid,
        grid.findAll('S').first(),
        grid.findAll('E').first()
    )

    fun partOne(): Long = Dijkstra.findShortestPath(
        start,
        { it == end },
        { _, current ->
            val currentElevation = elevation(grid.at(current))
            current.neighbours()
                .filter { grid.contains(it) }
                .filter { elevation(grid.at(it)) - currentElevation <= 1 } // up
        }
    )
        .cost()

    fun partTwo(): Long = Dijkstra.findShortestPath(
        end,
        { elevation(grid.at(it)) == 0 },
        { _, current ->
            val currentElevation = elevation(grid.at(current))
            current.neighbours()
                .filter { grid.contains(it) }
                .filter { currentElevation - elevation(grid.at(it)) <= 1 } // down
        }
    )
        .cost()
}