package aock2024

import shared.MutableGrid
import shared.Point2d

data class Year2024Day08(
    private val grid: MutableGrid
) {
    constructor(input: String) : this(MutableGrid(input))

    fun partOne(): Int = findAntinodes(grid.frequenciesExcluding(setOf('.')), 1..1).count()

    fun partTwo(): Int = findAntinodes(grid.frequenciesExcluding(setOf('.')), 0..Int.MAX_VALUE).count()

    private fun findAntinodes(
        frequencies: Map<Char, List<Point2d>>,
        range: IntRange
    ): Set<Point2d> = frequencies.flatMap { (_, antennas) ->
        antennas.flatMap { antenna1 ->
            antennas.filter { antenna2 -> antenna1 != antenna2 }
                .flatMap { antenna2 ->
                    val vector = antenna2 - antenna1

                    range.asSequence()
                        .map { i -> antenna2 + (vector * i) }
                        .takeWhile { grid.contains(it) }
                }
        }

    }
        .toSet()

}