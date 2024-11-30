package aock2015

import shared.Direction
import shared.Point2d
import shared.groupByIndexRemainder
import java.util.Collections.unmodifiableSet

data class Year2015Day03(
    private val directions: List<Direction>
) {
    constructor(text: String) : this(
        text.chars()
            .mapToObj { Direction.parse(it.toChar()) }
            .toList()
    )

    fun atLeastOnePresent(): Set<Point2d> {
        var point = Point2d.ZERO

        val points = mutableSetOf<Point2d>()
        points.add(Point2d.ZERO)

        for (direction in directions) {
            point += direction
            points.add(point)
        }

        return unmodifiableSet(points)
    }

    private fun splitWork(workers: Int): List<Year2015Day03> {
        return this.directions.groupByIndexRemainder(workers)
            .map { Year2015Day03(it) }
    }

    fun atLeastOnePresentWithHelp(workers: Int): Set<Point2d> {
        return splitWork(workers)
            .asSequence()
            .map { it.atLeastOnePresent() }
            .flatMap { it }
            .toSet()
    }

}