package aock2015

import shared.geometry2d.Point2dInt
import shared.groupByIndexRemainder
import shared.spatial.Direction
import java.util.Collections.unmodifiableSet

data class Year2015Day03(
    private val directions: List<Direction>
) {
    constructor(input: String) : this(
        input.chars()
            .mapToObj { Direction.parse(it.toChar()) }
            .toList()
    )

    fun atLeastOnePresent(): Set<Point2dInt> {
        var point = Point2dInt.ZERO

        val points = mutableSetOf<Point2dInt>()
        points.add(Point2dInt.ZERO)

        for (direction in directions) {
            point += direction
            points.add(point)
        }

        return unmodifiableSet(points)
    }

    private fun splitWork(workers: Int): List<Year2015Day03> = this.directions.groupByIndexRemainder(workers)
        .map { Year2015Day03(it) }

    fun atLeastOnePresentWithHelp(workers: Int): Set<Point2dInt> = splitWork(workers)
        .asSequence()
        .map { it.atLeastOnePresent() }
        .flatMap { it }
        .toSet()

}