package ec2025

import shared.geometry2d.Point2dInt
import shared.grid.IntGrid

data class Year2025Quest12(
    private val grid: IntGrid
) {
    constructor(input: String) : this(IntGrid(input))

    fun partOne() = ignitableBarrelsStartingFrom(Point2dInt.ZERO)
        .size

    fun partTwo() = sequenceOf(
        Point2dInt.ZERO,
        grid.dimension().bottomRight()
    )
        .flatMap { ignitableBarrelsStartingFrom(it) }
        .toSet()
        .size

    fun partThree(): Long {
        val alreadyIgnited = mutableSetOf<Point2dInt>()
        val ignitableBarrelsByStart = mutableSetOf<Set<Point2dInt>>()

        grid.points()
            .sortedByDescending { grid.at(it) }
            .filter { !alreadyIgnited.contains(it) }
            .forEach { point ->
                val cluster = ignitableBarrelsStartingFrom(point).toSet()
                ignitableBarrelsByStart += cluster
                alreadyIgnited += cluster
            }

        val ignitedBarrels = mutableSetOf<Point2dInt>()

        repeat(3) {
            ignitedBarrels += ignitableBarrelsByStart
                .map { points -> points.filter { point -> !ignitedBarrels.contains(point) } }
                .maxBy { points -> points.size }
        }

        return ignitedBarrels.size.toLong()
    }

    private fun ignitableBarrelsStartingFrom(start: Point2dInt): List<Point2dInt> =
        start.allDirectAndIndirectNeighbours { previous, next ->
            grid.contains(next) && grid.at(previous) >= grid.at(next)
        }
}