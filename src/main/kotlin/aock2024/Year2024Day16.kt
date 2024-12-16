package aock2024

import shared.CharGrid
import shared.Dijkstra
import shared.Direction
import shared.Point2d

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
            start to Direction.EAST,
            { it.first == end },
            { it ->
                buildList {
                    add(it.first + it.second to it.second)
                    add(it.first to it.second.rotateLeft())
                    add(it.first to it.second.rotateRight())
                }
                    .filter { grid.contains(it.first) && grid.at(it.first) != WALL }
            },
            ::costFunction
        )

        val path = shortestPath.fullPath()

        path.forEach { grid.set(it.first, '|') }
        println(grid)

        return shortestPath.cost()
    }

    fun partTwo() = 0L

    fun costFunction(current: Pair<Point2d, Direction>, next: Pair<Point2d, Direction>): Long? = if (current.first == next.first) {
        1000L
    } else {
        1
    }

    /*
    private fun costFor(v1: Vector2d, v2: Vector2d): Long {
        if (v1 == v2) {
            return 1L
        }

        if (v1.x == -v2.x || v1.y == -v2.y) {
            return 2001L
        }

        return 1001L
    }
     */
}