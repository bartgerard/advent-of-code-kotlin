package aock2025

import shared.Point2dLong
import shared.Rectangle2dLong
import shared.distinctPairs
import shared.sanitize
import shared.toLongs
import shared.zipWithNextCyclical

data class Year2025Day09(
    private val points: List<Point2dLong>
) {
    constructor(input: String) : this(
        input.sanitize()
            .lines()
            .map { it.toLongs() }
            .map { (x, y) -> Point2dLong(x, y) }
    )

    fun partOne(): Long = points.distinctPairs()
        .map { (p1, p2) -> Rectangle2dLong.of(p1, p2) }
        .maxOf { it.area }

    fun partTwo(): Long {
        val lines: List<Rectangle2dLong> = points.zipWithNextCyclical()
            .map { (p1, p2) -> Rectangle2dLong.of(p1, p2) }

        return points.distinctPairs()
            .map { (p1, p2) -> Rectangle2dLong.of(p1, p2) }
            .sortedByDescending { it.area }
            .first { rectangle -> lines.none { line -> line.overlaps(rectangle.innerRectangle()) } }
            .area
    }
}