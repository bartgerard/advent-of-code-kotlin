package aock2024

import shared.CharGrid
import shared.Dijkstra
import shared.Point2d
import shared.Vector2d
import shared.Vector2d.Companion.ORTHOGONAL_ADJACENT

data class Year2024Day16(
    private val grid: CharGrid
) {
    companion object {
        const val START = 'S'
        const val END = 'E'
        const val WALL = '#'
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(): Long {
        val start = grid.findAll(START).first()
        val end = grid.findAll(END).first()

        val shortestPath = Dijkstra.findShortestPath(
            start,
            { it == end },
            { it -> it.neighbours(ORTHOGONAL_ADJACENT).filter { grid.contains(it) && grid.at(it) != WALL } },
            ::costFunction
        )

        val path = shortestPath.fullPath()

        path.forEach { grid.set(it, '|') }
        println(grid)

        return shortestPath.cost()
    }

    fun partTwo() = 0L

    fun costFunction(previous: Point2d?, current: Point2d, next: Point2d): Long? {
        val v2 = next - current

        if (previous == null) {
            return costFor(Vector2d.EAST, v2)
        }

        return costFor(current - previous, v2)
    }

    private fun costFor(v1: Vector2d, v2: Vector2d): Long {
        if (v1 == v2) {
            return 1L
        }

        if (v1.x == -v2.x || v1.y == -v2.y) {
            return 2001L
        }

        return 1001L
    }
}