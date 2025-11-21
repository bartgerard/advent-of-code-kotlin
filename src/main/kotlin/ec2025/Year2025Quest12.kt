package ec2025

import shared.IntGrid
import shared.Point2d

data class Year2025Quest12(
    private val grid: IntGrid
) {
    constructor(input: String) : this(IntGrid(input))

    fun partOne() = ignitableBarrelsStartingFrom(Point2d.ZERO)
        .size

    fun partTwo() = sequenceOf(
        Point2d.ZERO,
        grid.dimension().bottomRight()
    )
        .flatMap { ignitableBarrelsStartingFrom(it) }
        .toSet()
        .size

    fun partThree(): Long {
        val alreadyIgnited = mutableSetOf<Point2d>()
        val ignitableBarrelsByStart = mutableSetOf<Set<Point2d>>()

        grid.points()
            .sortedByDescending { grid.at(it) }
            .filter { !alreadyIgnited.contains(it) }
            .forEach { point ->
                val cluster = ignitableBarrelsStartingFrom(point).toSet()
                ignitableBarrelsByStart += cluster
                alreadyIgnited += cluster
            }

        val ignitedBarrels = mutableSetOf<Point2d>()

        repeat(3) {
            ignitedBarrels += ignitableBarrelsByStart
                .map { points -> points.filter { point -> !ignitedBarrels.contains(point) } }
                .maxBy { points -> points.size }
        }

        return ignitedBarrels.size.toLong()
    }

    private fun ignitableBarrelsStartingFrom(start: Point2d): List<Point2d> =
        start.allDirectAndIndirectNeighbours { previous, next ->
            grid.contains(next) && grid.at(previous) >= grid.at(next)
        }
}