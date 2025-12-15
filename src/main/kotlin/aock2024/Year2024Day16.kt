package aock2024

import shared.algorithm.dijkstra.Dijkstra
import shared.algorithm.dijkstra.Solutions
import shared.geometry2d.Point2dInt
import shared.grid.CharGrid
import shared.spatial.Direction

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

    fun partTwo() = shortestPath().vertices().map { it.first }.toSet()
        //.also { display(it) }
        .size

    private fun display(points: Set<Point2dInt>) {
        points.forEach { grid.set(it, 'O') }
        println(grid)
    }

    private fun shortestPath(): Solutions<Pair<Point2dInt, Direction>> {
        val start = grid.findAll(START).first()
        val end = grid.findAll(END).first()

        val shortestPath = Dijkstra.findShortestPaths(
            start to Direction.EAST,
            { (point, _) -> point == end },
            {
                buildList {
                    add(it.first + it.second to it.second)
                    add(it.first to it.second.rotateLeft())
                    add(it.first to it.second.rotateRight())
                }
                    .filter { (point, _) -> grid.contains(point) && grid.at(point) != WALL }
            },
            ::costFunction
        )
        return shortestPath
    }

    fun costFunction(current: Pair<Point2dInt, Direction>, next: Pair<Point2dInt, Direction>): Long? =
        if (current.first == next.first) {
            1000L
        } else {
            1
        }

}