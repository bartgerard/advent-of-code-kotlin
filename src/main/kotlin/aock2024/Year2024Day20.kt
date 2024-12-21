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

    fun partOne(minimumSaving: Int, allowedShortcut: Int = 2): Int {
        val start = grid.findAll(START).first()
        val end = grid.findAll(END).first()
        val solution = Dijkstra.findShortestPath(
            start,
            { point -> point == end },
            { _, point -> point.neighbours(ORTHOGONAL_ADJACENT).filter { grid.contains(it) && grid.at(it) != WALL } }
        )

        val shortestPath = solution.fullPath()
        shortestPath.forEach { grid.set(it, 'O') }

        var cheats = 0L
        val shortCuts = mutableListOf<Int>()

        for ((i, currentStep) in shortestPath.withIndex()) {
            for ((j, nextStep) in shortestPath.withIndex().drop(i + 1)) {
                val lengthOfShortcut = currentStep.manhattan(nextStep)
                val timeSaved = j - i - lengthOfShortcut
                if (lengthOfShortcut in 2..allowedShortcut && minimumSaving <= timeSaved) {
                    shortCuts.add(timeSaved)
                }
            }

            cheats = cheats + shortCuts.size
        }

        display(shortCuts)

        return shortCuts.size
    }

    private fun display(shortCuts: List<Int>) {
        shortCuts.groupingBy { it }
            .eachCount()
            .toSortedMap { key: Int, value: Int -> key }
            .entries
            .joinToString(separator = ", ", prefix = "{", postfix = "}") { "${it.value}x${it.key}" }
            .let { println(it) }
    }

}