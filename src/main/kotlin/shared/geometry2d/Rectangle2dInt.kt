package shared.geometry2d

import shared.minMaxOrNull
import shared.range.innerRange
import shared.range.length
import shared.range.overlaps
import kotlin.math.max
import kotlin.math.min

data class Rectangle2dInt(
    val x: IntRange,
    val y: IntRange
) {
    companion object {
        fun parse(input: String): Rectangle2dInt {
            val (x, y) = input.split('x')
            return Rectangle2dInt(
                0..<x.toInt(),
                0..<y.toInt()
            )
        }

        fun of(p1: Point2dInt, p2: Point2dInt) = Rectangle2dInt(
            min(p1.x, p2.x)..max(p1.x, p2.x),
            min(p1.y, p2.y)..max(p1.y, p2.y)
        )

        fun surrounding(points: Collection<Point2dInt>): Rectangle2dInt {
            val (minX, maxX) = points.map { it.x }.minMaxOrNull()!!
            val (minY, maxY) = points.map { it.y }.minMaxOrNull()!!

            return Rectangle2dInt(
                minX..maxX,
                minY..maxY,
            )
        }
    }

    fun area(): Long = x.length().toLong() * y.length().toLong()
    fun perimeter(): Long = 2L * (x.length() + y.length())

    fun points() = x.asSequence().flatMap { a -> y.map { b -> Point2dInt(a, b) } }
    fun contains(p: Point2dInt) = p.x in x && p.y in y

    fun overlaps(other: Rectangle2dInt): Boolean = x.overlaps(other.x) && y.overlaps(other.y)

    fun innerRectangle(): Rectangle2dInt = Rectangle2dInt(x.innerRange(), y.innerRange())
}