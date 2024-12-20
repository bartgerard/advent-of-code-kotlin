package aock2024

import shared.CharGrid
import shared.Dijkstra
import shared.Vector2d.Companion.ORTHOGONAL_ADJACENT

data class Year2024Day20(
    private val grid: CharGrid
) {
    companion object {
        const val START = 'S'
        const val END = 'E'
        const val WALL = '#'
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(minimumSaving: Int): Int {
        val start = grid.findAll(START).first()
        val end = grid.findAll(END).first()

        val solution = Dijkstra.findShortestPath(
            start,
            { point -> point == end },
            { _, point ->
                point.neighbours(ORTHOGONAL_ADJACENT)
                    .filter { grid.contains(it) && grid.at(it) != WALL }
            }
        )

        val shortestPath = solution.fullPath()
        shortestPath.forEach { grid.set(it, 'O') }

        var cheats = 0L
        val shortCuts = mutableListOf<Long>()

        for (step in shortestPath) {
            val cost = shortestPath.indexOf(step)

            shortCuts += step.neighbours(ORTHOGONAL_ADJACENT)
                .filter { grid.contains(it) && grid.at(it) == WALL }
                .flatMap { it.neighbours(ORTHOGONAL_ADJACENT) }
                .filter {
                    grid.contains(it)
                            && grid.at(it) != WALL
                            && it != step
                }
                .map { solution.path.costTo(it) - cost - 2 } // going through the wall
                .filter { it > 0 }

            cheats = cheats + shortCuts.size
        }

        val frequencies = shortCuts.groupingBy { it }.eachCount()
        println(frequencies)

        return shortCuts.filter { it >= minimumSaving }.size
    }

    fun partTwo() = 0L

}