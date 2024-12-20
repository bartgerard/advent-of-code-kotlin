package aock2024

import shared.CharGrid
import shared.Dijkstra
import shared.Point2d
import shared.Solution
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

    fun partOne(minimumSaving: Int, allowedShortcut: Int = 2): Int {
        val solution = findShortestPath()

        val shortestPath = solution.fullPath()
        shortestPath.forEach { grid.set(it, 'O') }

        var cheats = 0L
        val shortCuts = mutableListOf<Int>()

        for (i in shortestPath.indices) {
            val currentStep = shortestPath[i]

            for (j in shortestPath.indices.reversed()) {
                val difference = j - i
                if (difference <= minimumSaving + 2) {
                    break
                }

                val nextStep = shortestPath[j]

                val lengthOfShortcut = currentStep.manhattan(nextStep)
                if (lengthOfShortcut in 2..allowedShortcut) {
                    shortCuts.add(difference - lengthOfShortcut)
                }
            }

            cheats = cheats + shortCuts.size
        }

        val frequencies = shortCuts.groupingBy { it }.eachCount()
        println(frequencies)

        return shortCuts.filter { it >= minimumSaving }.size
    }

    fun partTwo(minimumSaving: Int): Long {
        val solution = findShortestPath()

        val shortestPath = solution.fullPath()
        shortestPath.forEach { grid.set(it, 'O') }

        var cheats = 0L
        val shortCuts = mutableListOf<Long>()

        for (i in shortestPath.indices) {
            val currentStep = shortestPath[i]
            val cost = i

            for (j in shortestPath.indices.reversed()) {
                if (i + minimumSaving > j) {
                    break
                }

                val nextStep = shortestPath[i]

                if (currentStep.manhattan(nextStep) <= 20) {

                }
            }

            shortCuts += currentStep.neighbours(ORTHOGONAL_ADJACENT)
                .filter { grid.contains(it) && grid.at(it) == WALL }
                .flatMap { it.neighbours(ORTHOGONAL_ADJACENT) }
                .filter {
                    grid.contains(it)
                            && grid.at(it) != WALL
                            && it != currentStep
                }
                .map { solution.path.costTo(it) - cost - 2 } // going through the wall
                .filter { it > 0 }

            cheats = cheats + shortCuts.size
        }

        val frequencies = shortCuts.groupingBy { it }.eachCount()
        println(frequencies)

        return 0
        //return shortCuts.filter { it >= minimumSaving }.size
    }

    private fun findShortestPath(): Solution<Point2d> {
        val start = grid.findAll(START).first()
        val end = grid.findAll(END).first()

        val solution = Dijkstra.findShortestPath(
            start,
            { point -> point == end },
            { _, point -> point.neighbours(ORTHOGONAL_ADJACENT).filter { grid.contains(it) && grid.at(it) != WALL } }
        )
        return solution
    }

}