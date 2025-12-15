package shared.geometry2d

import shared.LinearEquation2d
import shared.LinearFunction2d

data class LineSegment2dInt(
    val p1: Point2dInt,
    val p2: Point2dInt
) {
    fun slope() = (p2.y - p1.y) / (p2.x - p1.x)
    fun yIntercept() = p1.y - slope() * p1.x

    fun length() = p1.manhattan(p2)

    fun toFunction() = LinearFunction2d(slope(), yIntercept())

    fun toLinearEquation(): LinearEquation2d {
        val a = p2.y - p1.y
        val b = p1.x - p2.x
        val c = a * p1.x + b * p1.y
        return LinearEquation2d(a, b, c)
    }

    fun intersect(other: LineSegment2dInt): Point2dInt? = toLinearEquation().intersect(other.toLinearEquation())
        ?.let { (x, y) -> Point2dInt(x.toInt(), y.toInt()) }

    fun contains(p: Point2dInt) = p.x in minOf(p1.x, p2.x)..maxOf(p1.x, p2.x)
            && p.y in minOf(p1.y, p2.y)..maxOf(p1.y, p2.y)

    fun intersectWithinSegment(other: LineSegment2dInt): Point2dInt? = intersect(other)
        ?.let { if (contains(it) && other.contains(it)) it else null }

    fun isVertical() = p1.x == p2.x && p1.y != p2.y

    fun intersectDiscrete(other: LineSegment2dInt): List<Point2dInt> {
        if (!isVertical() && !other.isVertical()
            && slope() == other.slope()
            && yIntercept() == other.yIntercept()
        ) {
            // TODO
            return (p1.x..p2.x).intersect(other.p1.x..other.p2.x)
                .map { Point2dInt(it, yIntercept()) }
        } else if (isVertical() && other.isVertical() && p1.x == other.p1.x) {
            // TODO
            return (p1.y..p2.y).intersect(other.p1.y..p2.y).map { Point2dInt(p1.x, it) }
        }

        return intersect(other)?.let { listOf(it) } ?: emptyList()
    }
}