package aock2024

import shared.MutableGrid
import shared.Point2d

data class Year2024Day10(
    private val grid: MutableGrid
) {
    companion object {
        val TRAIL = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    }

    constructor(input: String) : this(MutableGrid(input))

    fun partOne(): Int = trailheads().sumOf { findDestinationsStartingAt(it).count() }

    fun partTwo(): Long = trailheads().sumOf { scoreForTrailStartingAt(it, TRAIL.subList(1, TRAIL.size)) }

    fun trailheads() = grid.findAll(TRAIL[0])

    fun findDestinationsStartingAt(point: Point2d): Set<Point2d> {
        val previousPoints = mutableSetOf<Point2d>(point)
        val nextPoints = mutableSetOf<Point2d>()

        for (nextHeight in TRAIL.subList(1, TRAIL.size)) {
            for (previousPoint in previousPoints) {
                nextPoints += nextPoints(previousPoint, nextHeight)
            }
            previousPoints.clear()
            previousPoints.addAll(nextPoints)
            nextPoints.clear()
        }

        return previousPoints
    }

    fun scoreForTrailStartingAt(point: Point2d, trailTail: List<Char>): Long = if (trailTail.size == 1) {
        nextPoints(point, trailTail[0]).count().toLong()
    } else {
        nextPoints(point, trailTail[0]).sumOf { scoreForTrailStartingAt(it, trailTail.subList(1, trailTail.size)) }
    }

    private fun nextPoints(previousPoint: Point2d, nextHeight: Char): List<Point2d> = previousPoint.orthogonalNeighbours().filter { grid.contains(it) && grid.at(it) == nextHeight }

}