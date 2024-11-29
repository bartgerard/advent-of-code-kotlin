package aock2015

import shared.Direction
import shared.Point3d
import shared.groupByIndexRemainder
import java.util.Collections.unmodifiableSet

data class Day03(
    private val directions: List<Direction>
) {
    constructor(text: String) : this(
        text.chars()
            .mapToObj { Direction.parse(it.toChar()) }
            .toList()
    )

    fun atLeastOnePresent(): Set<Point3d> {
        var point = Point3d.ZERO

        val points = mutableSetOf<Point3d>()
        points.add(Point3d.ZERO)

        for (direction in directions) {
            point += direction.vector
            points.add(point)
        }

        return unmodifiableSet(points)
    }

    private fun splitWork(workers: Int): List<Day03> {
        return this.directions.groupByIndexRemainder(workers)
            .map { Day03(it) }
    }

    fun atLeastOnePresentWithHelp(workers: Int): Set<Point3d> {
        return splitWork(workers)
            .asSequence()
            .map { it.atLeastOnePresent() }
            .flatMap { it }
            .toSet()
    }

}