package aock2024

import shared.CharGrid
import shared.Dijkstra
import shared.Dimension
import shared.Point2d
import shared.Vector2d
import shared.sanitize
import shared.toIntegers

data class Year2024Day18(
    private val start: Point2d,
    private val destination: Point2d,
    private val bytes: List<Point2d>
) {
    companion object {
        const val SAFE = '.'
        const val CORRUPTED = '#'
    }

    constructor(
        start: Point2d,
        destination: Point2d,
        input: String
    ) : this(start, destination, input.sanitize().lines().map { it.toIntegers() }.map { Point2d(it[0], it[1]) })

    fun partOne(time: Int): Long {
        val dimension = Dimension(destination.x + 1, destination.y + 1)
        val grid = CharGrid(dimension, SAFE)

        repeat(time) { grid.set(bytes[it], CORRUPTED) }

        val shortestPath = Dijkstra.findShortestPath(
            start,
            { it == destination },
            { path, current ->
                //val corruptedBytes = bytes.subList(0, min(path.fullPathTo(current).size - 1, max))
                current.neighbours(Vector2d.ORTHOGONAL_ADJACENT)
                    .filter { grid.contains(current) && grid.at(current) != CORRUPTED }
            }
        )

        return shortestPath.cost()
    }

    fun partTwo(): String {
        val dimension = Dimension(destination.x + 1, destination.y + 1)
        val grid = CharGrid(dimension, SAFE)

        val minLength = start.manhattan(destination)
        repeat(minLength) { grid.set(bytes[it], CORRUPTED) }

        for (point in bytes.subList(minLength, bytes.size)) {
            grid.set(point, CORRUPTED)

            val shortestPath = Dijkstra.findShortestPath(
                start,
                { it == destination },
                { path, current ->
                    //val corruptedBytes = bytes.subList(0, min(path.fullPathTo(current).size - 1, max))
                    current.neighbours(Vector2d.ORTHOGONAL_ADJACENT)
                        .filter { grid.contains(current) && grid.at(current) != CORRUPTED }
                }
            )

            if (shortestPath.destination == null) {
                return "${point.x},${point.y}"
            }
        }

        return ""
    }
}