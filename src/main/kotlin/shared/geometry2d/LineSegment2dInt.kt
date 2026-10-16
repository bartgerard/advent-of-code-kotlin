package shared.geometry2d

import shared.LinearEquation2d
import shared.LinearFunction2d
import shared.gcd
import kotlin.math.absoluteValue

data class LineSegment2dInt(
    val p1: Point2dInt,
    val p2: Point2dInt
) {
    fun slope(): Double = (p2.y - p1.y).toDouble() / (p2.x - p1.x)
    fun yIntercept(): Double = p1.y - slope() * p1.x

    fun length() = p1.manhattan(p2)

    fun toFunction() = LinearFunction2d(slope(), yIntercept())

    // a x + b y + c = 0
    fun toLinearEquation(): LinearEquation2d {
        val a = p2.y - p1.y
        val b = p1.x - p2.x
        val c = -(a * p1.x + b * p1.y)
        return LinearEquation2d(a, b, c)
    }

    fun intersect(other: LineSegment2dInt): Point2dInt? = toLinearEquation().intersect(other.toLinearEquation())
        ?.let { (x, y) -> Point2dInt(x.toInt(), y.toInt()) }

    fun contains(p: Point2dInt) = p.x in minOf(p1.x, p2.x)..maxOf(p1.x, p2.x)
            && p.y in minOf(p1.y, p2.y)..maxOf(p1.y, p2.y)

    fun intersectWithinSegment(other: LineSegment2dInt): Point2dInt? = intersect(other)
        ?.let { if (contains(it) && other.contains(it)) it else null }

    fun isVertical() = p1.x == p2.x && p1.y != p2.y

    /**
     * All integer points that lie on both segments.
     */
    fun intersectDiscrete(other: LineSegment2dInt): List<Point2dInt> {
        val d1 = p2 - p1
        val d2 = other.p2 - other.p1
        val denominator = cross(d1, d2)

        if (denominator == 0L) {
            // parallel, collinear or degenerate
            return latticePoints().filter { other.isOnSegment(it) }
        }

        // p1 + t d1 = other.p1 + u d2  =>  t = ((other.p1 - p1) x d2) / (d1 x d2)
        val numerator = cross(other.p1 - p1, d2)
        val xNumerator = p1.x * denominator + d1.x * numerator
        val yNumerator = p1.y * denominator + d1.y * numerator

        if (xNumerator % denominator != 0L || yNumerator % denominator != 0L) {
            return emptyList()
        }

        val point = Point2dInt((xNumerator / denominator).toInt(), (yNumerator / denominator).toInt())

        return if (contains(point) && other.contains(point)) listOf(point) else emptyList()
    }

    private fun latticePoints(): List<Point2dInt> {
        val d = p2 - p1
        val steps = gcd(d.x.absoluteValue, d.y.absoluteValue)

        if (steps == 0) {
            return listOf(p1)
        }

        val step = Vector2dInt(d.x / steps, d.y / steps)

        return (0..steps).map { p1 + step * it }
    }

    private fun isOnSegment(p: Point2dInt): Boolean = cross(p2 - p1, p - p1) == 0L && contains(p)

    private fun cross(v1: Vector2d<Int>, v2: Vector2d<Int>): Long = v1.x.toLong() * v2.y - v1.y.toLong() * v2.x
}
