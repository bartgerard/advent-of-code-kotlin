package aock2021

import shared.LineSegment2d
import shared.Point2d
import shared.distinctPairs
import shared.sanitize
import shared.toIntegers

data class Year2021Day05(
    private val lines: List<LineSegment2d>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.toIntegers() }
            .map {
                LineSegment2d(
                    Point2d(it[0], it[1]),
                    Point2d(it[2], it[3]),
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