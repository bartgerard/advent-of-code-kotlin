package aock2021

import shared.distinctPairs
import shared.geometry2d.LineSegment2dInt
import shared.geometry2d.Point2dInt
import shared.sanitize
import shared.toIntegers

data class Year2021Day05(
    private val lines: List<LineSegment2dInt>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.toIntegers() }
            .map {
                LineSegment2dInt(
                    Point2dInt(it[0], it[1]),
                    Point2dInt(it[2], it[3]),
                )
            }
    )

    fun partOne() = lines
        .filter { (p1, p2) -> p1.x == p2.x || p1.y == p2.y }
        .distinctPairs()
        .flatMap { (l1, l2) -> l1.intersectDiscrete(l2) } // TODO missing overlapping points
        .toSet()
        .size

    fun partTwo() = 0L
}