package shared.geometry2d

import shared.spatial.Direction
import kotlin.math.absoluteValue

data class Point2dLong(
    override val x: Long,
    override val y: Long
) : Point2d<Long> {
    override fun plus(p: Point2d<Long>) = Point2dLong(x + p.x, y + p.y)
    override fun times(p: Point2d<Long>) = Point2dLong(x * p.x, y * p.y)

    override fun manhattan(p: Point2d<Long>) = (x - p.x).absoluteValue + (y - p.y).absoluteValue

    override fun minus(p: Point2d<Long>) = Vector2dLong(x - p.x, y - p.y)

    override operator fun plus(v: Vector2d<Long>) = Point2dLong(x + v.x, y + v.y)
    override operator fun minus(v: Vector2d<Long>) = Point2dLong(x - v.x, y - v.y)

    override fun plus(direction: Direction) = this + Vector2dInt.forDirection(direction)

    operator fun plus(v: Vector2dInt) = Point2dLong(x + v.x, y + v.y)

    fun neighbours(
        directions: List<Vector2d<Long>>
    ) = directions.map { this + it }

}