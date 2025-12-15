package aock2024

import shared.algorithm.dijkstra.Dijkstra
import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.grid.CharGrid
import shared.sanitize
import shared.toIntegers

data class Year2024Day18(
    private val start: Point2dInt,
    private val destination: Point2dInt,
    private val bytes: List<Point2dInt>
) {
    companion object {
        const val SAFE = '.'
        const val CORRUPTED = '#'
    }

    constructor(
        start: Point2dInt,
        destination: Point2dInt,
        input: String
    ) : this(start, destination, input.sanitize().lines().map { it.toIntegers() }.map { Point2dInt(it[0], it[1]) })

    fun partOne(time: Int): Long {
        val dimension = Dimension2d(destination.x + 1, destination.y + 1)
        val grid = CharGrid(dimension, SAFE)

        repeat(time) { grid.set(bytes[it], CORRUPTED) }

        val shortestPath = Dijkstra.findShortestPath(
            start,
            { it == destination },
            { path, current ->
                //val corruptedBytes = bytes.subList(0, min(path.fullPathTo(current).size - 1, max))
                current.neighbours()
                    .filter { grid.contains(it) }
                    .filter { grid.at(it) != CORRUPTED }
            }
        )

        return shortestPath.cost()
    }

    fun partTwo(): String {
        val dimension = Dimension2d(destination.x + 1, destination.y + 1)
        val grid = CharGrid(dimension, SAFE)

        val minLength = start.manhattan(destination)
        repeat(minLength) { grid.set(bytes[it], CORRUPTED) }

        for (point in bytes.subList(minLength, bytes.size)) {
            grid.set(point, CORRUPTED)

            val shortestPath = Dijkstra.findShortestPath(
                start,
                { it == destination },
                { _, current ->
                    //val corruptedBytes = bytes.subList(0, min(path.fullPathTo(current).size - 1, max))
                    current.neighbours()
                        .filter { grid.contains(it) }
                        .filter { grid.at(it) != CORRUPTED }
                }
            )

            if (shortestPath.destination == null) {
                return "${point.x},${point.y}"
            }
        }

        return ""
    }
}