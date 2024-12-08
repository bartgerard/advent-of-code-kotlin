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
    ): MutableSet<Point2d> {
        val antinodes = mutableSetOf<Point2d>()

        for ((_, antennas) in frequencies) {
            antennas.flatMap { antenna1 ->
                antennas.filter { antenna2 -> antenna1 != antenna2 }
                    .map { antenna2 -> antenna1 to antenna2 }
            }
                .forEach { (antenna1, antenna2) ->
                    val vector = antenna2 - antenna1

                    for (distance in range) {
                        val antinode = antenna2 + (vector * distance)

                        if (!grid.contains(antinode)) {
                            break
                        }

                        antinodes.add(antinode)
                    }
                }
        }

        return antinodes
    }

}