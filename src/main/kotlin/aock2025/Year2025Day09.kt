package aock2025

import shared.distinctPairs
import shared.length
import shared.sanitize
import shared.toLongs
import shared.zipWithNextCyclical
import kotlin.math.max
import kotlin.math.min

data class Year2025Day09(
    private val points: List<Point2l>
) {
    constructor(input: String) : this(input.sanitize().lines().map { it.toLongs() }.map { (x, y) -> Point2l(x, y) })

    fun partOne(): Long = points.distinctPairs()
        .map { (p1, p2) -> Rectangle2l.of(p1, p2) }
        .maxOf { it.area }

    fun partTwo(): Long {
        val rectangles = points.zipWithNextCyclical()
            .map { (p1, p2) -> Rectangle2l.of(p1, p2) }

        val redPlates = rectangles.flatMap { it.corners() }
            .toSet()

        return redPlates.distinctPairs()
            .map { (p1, p2) -> Rectangle2l.of(p1, p2) }
            .filter { rectangle ->
                val allContainedRedPlates = redPlates.filter { plate -> rectangle.contains(plate) }
                val corners = rectangle.corners()
                allContainedRedPlates.all { corners.contains(it) }
            }
            .maxOf { it.area }
    }
}

data class Point2l(val x: Long, val y: Long)

data class Rectangle2l(
    val x: LongRange,
    val y: LongRange
) {
    companion object {
        fun of(p1: Point2l, p2: Point2l) = Rectangle2l(
            min(p1.x, p2.x)..max(p1.x, p2.x),
            min(p1.y, p2.y)..max(p1.y, p2.y)
        )
    }

    val area get() = x.length() * y.length()
    fun contains(p: Point2l) = p.x in x && p.y in y

    fun corners() = buildSet {
        add(Point2l(x.first, y.first))
        add(Point2l(x.first, y.last))
        add(Point2l(x.last, y.first))
        add(Point2l(x.last, y.last))
    }
}