package aock2025

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.CharGrid

data class Year2025Day07(
    private val grid: CharGrid,
) {
    companion object {
        const val START = 'S'
        const val SPLITTER = '^'
    }

    constructor(input: String) : this(CharGrid(input))

    fun partOne(): Int {
        val start = grid.findAll(START).first()
        val splitters = mutableSetOf<Point2dInt>()
        findUsedSplitters(start, splitters)
        return splitters.size
    }

    fun partTwo(): Long = grid.findAll(START).sumOf { beamOrigin ->
        countTimelines(beamOrigin)
    }

    private fun findUsedSplitters(
        beamOrigin: Point2dInt,
        splitters: MutableSet<Point2dInt>,
    ) {
        findNextSplitter(beamOrigin)
            ?.let { splitter ->
                if (splitter !in splitters) {
                    splitters += splitter
                    splitBeam(splitter)
                        .forEach { beamOrigin -> findUsedSplitters(beamOrigin, splitters) }
                }
            }
    }

    private fun countTimelines(
        beamOrigin: Point2dInt,
        memory: MutableMap<Point2dInt, Long> = mutableMapOf(),
    ): Long = memory.getOrPut(beamOrigin) {
        findNextSplitter(beamOrigin)
            ?.let { splitter ->
                splitBeam(splitter)
                    .sumOf { timeline -> countTimelines(timeline, memory) }
            }
            ?: 1L
    }

    private fun findNextSplitter(beamOrigin: Point2dInt): Point2dInt? = grid.dimension()
        .pointsInDirection(beamOrigin, Vector2dInt.SOUTH)
        .takeWhile { grid.contains(it) }
        .filter { grid.at(it) == SPLITTER }
        .firstOrNull()

    private fun splitBeam(point: Point2dInt): Sequence<Point2dInt> = sequenceOf(
        point + Vector2dInt.EAST,
        point + Vector2dInt.WEST
    )

    /*
    fun partOneV1(): Long {
        val start = grid.findAll(START).first()

        var beamColumns = setOf(start.x)

        var beamCounts = 0L

        for (row in (start.y + 1)..<grid.dimension().height) {
            val nextBeamColumns = beamColumns.toMutableSet()

            for (column in beamColumns) {
                if (grid.at(row, column) == SPLITTER) {
                    beamCounts += 1
                    nextBeamColumns -= column

                    val newBeamColumns = setOf(column - 1, column + 1)

                    nextBeamColumns += newBeamColumns
                }
            }

            beamColumns = nextBeamColumns
        }

        return beamCounts
    }
     */

}