package shared.geometry2d

import shared.spatial.Direction
import kotlin.math.absoluteValue

data class Point2dInt(
    override val x: Int,
    override val y: Int
) : Point2d<Int> {
    companion object {
        val ZERO = Point2dInt(0, 0)

        fun regionsFor(points: Collection<Point2dInt>): List<Rectangle2dInt> {
            val remaining = points.sortedWith(compareBy({ it.x }, { it.y })).toMutableSet()
            val regions = mutableListOf<Rectangle2dInt>()

            while (remaining.isNotEmpty()) {
                val point = remaining.first()

                val columns = (0..Int.MAX_VALUE).asSequence()
                    .map { i -> point + (Vector2dInt.EAST * i) }
                    .takeWhile { remaining.contains(it) }
                    .toList()
                val rows = (0..Int.MAX_VALUE).asSequence()
                    .map { i -> columns.map { it + Vector2dInt.SOUTH * i } }
                    .takeWhile { it.all { remaining.contains(it) } }
                    .toList()

                remaining -= rows.flatten().toSet()
                regions += Rectangle2dInt(point.x..<(point.x + columns.size), point.y..<(point.y + rows.size))
            }

            return regions
        }
    }

    constructor(coordinates: List<Int>) : this(coordinates[0], coordinates[1])

    override operator fun plus(p: Point2d<Int>) = Point2dInt(x + p.x, y + p.y)
    override operator fun times(p: Point2d<Int>) = Point2dInt(x * p.x, y * p.y)

    override fun manhattan(p: Point2d<Int>) = (x - p.x).absoluteValue + (y - p.y).absoluteValue

    override operator fun minus(p: Point2d<Int>) = Vector2dInt(x - p.x, y - p.y)

    override operator fun plus(v: Vector2d<Int>) = Point2dInt(x + v.x, y + v.y)
    override operator fun minus(v: Vector2d<Int>) = Point2dInt(x - v.x, y - v.y)

    override operator fun plus(direction: Direction) = this + Vector2dInt.forDirection(direction)

    operator fun minus(direction: Direction) = this - Vector2dInt.forDirection(direction)

    fun mod(d: Dimension2d) = Point2dInt(x.mod(d.width), y.mod(d.height))

    fun modWidth(width: Int) = Point2dInt(x.mod(width), y)
    fun modX(d: Dimension2d) = modWidth(d.width)

    fun modHeight(height: Int) = Point2dInt(x, y.mod(height))
    fun modY(d: Dimension2d) = modHeight(d.height)

    fun neighbours(
        directions: List<Vector2d<Int>> = Vector2dInt.ORTHOGONAL_ADJACENT
    ) = directions.map { this + it }

    fun allDirectAndIndirectNeighbours(
        directions: List<Vector2dInt> = Vector2dInt.ORTHOGONAL_ADJACENT,
        predicate: (Point2dInt) -> Boolean
    ): List<Point2dInt> {
        val nextPoints = mutableListOf(this)
        val visited = mutableListOf<Point2dInt>()

        while (nextPoints.isNotEmpty()) {
            val nextPoint = nextPoints.removeFirst()
            visited += nextPoint
            nextPoints += directions.map { nextPoint + it }
                .filter { !visited.contains(it) && !nextPoints.contains(it) && predicate(it) }
        }

        return visited
    }

    fun allDirectAndIndirectNeighbours(
        directions: List<Vector2dInt> = Vector2dInt.ORTHOGONAL_ADJACENT,
        predicate: (Point2dInt, Point2dInt) -> Boolean
    ): List<Point2dInt> {
        val nextPoints = mutableListOf(this)
        val visited = mutableListOf<Point2dInt>()

        while (nextPoints.isNotEmpty()) {
            val nextPoint = nextPoints.removeFirst()
            visited += nextPoint
            nextPoints += directions.map { nextPoint + it }
                .filter { !visited.contains(it) && !nextPoints.contains(it) && predicate(nextPoint, it) }
        }

        return visited
    }

    fun towards(p: Point2dInt): Vector2dInt = Vector2dInt(p.x - x, p.y - y)

    operator fun rangeTo(direction: Direction): Sequence<Point2dInt> = rangeTo(Vector2dInt.forDirection(direction))
    operator fun rangeTo(v: Vector2dInt): Sequence<Point2dInt> = generateSequence(0) { it + 1 }
        .map { this + v * it }

}