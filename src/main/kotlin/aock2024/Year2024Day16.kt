package aock2024

import shared.*

data class Year2024Day16(
    private val grid: CharGrid
) {
    companion object {
        const val START = 'S'
        const val END = 'E'
        const val WALL = '#'
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(): Long = shortestPath().cost()

    fun partTwo() = shortestPath().vertices().size

    private fun shortestPath(): Solutions<Pair<Point2d, Direction>> {
        val start = grid.findAll(START).first()
        val end = grid.findAll(END).first()

        val shortestPath = Dijkstra.findShortestPaths(
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
        return shortestPath
    }

    fun costFunction(current: Pair<Point2d, Direction>, next: Pair<Point2d, Direction>): Long? = if (current.first == next.first) {
        1000L
    } else {
        1
    }

}