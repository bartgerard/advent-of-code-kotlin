package aock2025

import shared.distinctPairs
import shared.geometry2d.Point2dInt
import shared.geometry2d.Rectangle2dInt
import shared.sanitize
import shared.toIntegers
import shared.zipWithNextCyclical

data class Year2025Day09(
    private val points: List<Point2dInt>
) {
    constructor(input: String) : this(
        input.sanitize()
            .lines()
            .map { it.toIntegers() }
            .map { (x, y) -> Point2dInt(x, y) }
    )

    fun partOne(): Long = points.distinctPairs()
        .map { (p1, p2) -> Rectangle2dInt.of(p1, p2) }
        .maxOf { it.area() }

    fun partTwo(): Long {
        val lines: List<Rectangle2dInt> = points.zipWithNextCyclical()
            .map { (p1, p2) -> Rectangle2dInt.of(p1, p2) }

        return points.distinctPairs()
            .map { (p1, p2) -> Rectangle2dInt.of(p1, p2) }
            .sortedByDescending { it.area() }
            .first { rectangle -> lines.none { line -> line.overlaps(rectangle.innerRectangle()) } }
            .area()
    }
}