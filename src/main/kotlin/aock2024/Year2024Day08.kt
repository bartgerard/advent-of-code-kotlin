package aock2024

import shared.MutableGrid
import shared.Point2d

data class Year2024Day08(
    private val grid: MutableGrid
) {
    constructor(input: String) : this(MutableGrid(input))

    fun partOne(): Int = findAntinodes(findFrequencies(), 1).count()

    fun partTwo(): Int {
        val frequencies = findFrequencies()
        val antinodes = findAntinodes(frequencies, Int.MAX_VALUE)
        val antennas: List<Point2d> = frequencies.flatMap { it.value }

        val allAntinodes = antennas.toMutableSet()
        allAntinodes.addAll(antinodes)

        return allAntinodes.count()
    }

    private fun findFrequencies(): MutableMap<Char, List<Point2d>> {
        val frequencies = mutableMapOf<Char, List<Point2d>>()

        for (row in 0..<grid.height()) {
            for (column in 0..<grid.width()) {
                val point = Point2d(column, row)
                val character = grid.at(point)

                if (character != '.') {
                    frequencies[character] = (frequencies[character] ?: mutableListOf()) + point
                }
            }
        }

        return frequencies
    }

    private fun findAntinodes(
        frequencies: MutableMap<Char, List<Point2d>>,
        broadCastingLimit: Int
    ): MutableSet<Point2d> {
        val antinodes = mutableSetOf<Point2d>()

        for (frequency in frequencies) {
            val antennas = frequency.value

            for (antenna1 in antennas) {
                for (antenna2 in antennas) {
                    if (antenna1 != antenna2) {
                        val v = antenna2 - antenna1

                        for (t in 1..broadCastingLimit) {
                            val antinode = antenna2 + (v * t)

                            if (grid.contains(antinode)) {
                                antinodes.add(antinode)
                            } else {
                                break
                            }
                        }
                    }
                }
            }
        }
        return antinodes
    }
}