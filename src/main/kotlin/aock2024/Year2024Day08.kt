package aock2024

import shared.CharGrid
import shared.Point2d

data class Year2024Day08(
    private val grid: CharGrid
) {
    constructor(input: String) : this(CharGrid(input))

    fun partOne() = findAntiNodes(grid.frequenciesExcluding(setOf('.')), 1..1).count()

    fun partTwo() = findAntiNodes(grid.frequenciesExcluding(setOf('.')), 0..Int.MAX_VALUE).count()

    private fun findAntiNodes(
        frequencies: Map<Char, List<Point2d>>,
        broadcastingRange: IntRange
    ) = frequencies.flatMap { (_, antennas) ->
        antennas.flatMap { antenna1 ->
            antennas.filter { antenna2 -> antenna1 != antenna2 }
                .flatMap { antenna2 ->
                    val vector = antenna2 - antenna1

                    broadcastingRange.asSequence()
                        .map { i -> antenna2 + (vector * i) }
                        .takeWhile { grid.contains(it) }
                }
        }

    }
        .toSet()

}