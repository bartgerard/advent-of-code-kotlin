package shared

import shared.Vector2d.Companion.ORTHOGONAL_ADJACENT
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.roundToInt

fun <T> Pair<T, T>.x() = first
fun <T> Pair<T, T>.y() = second

data class Vector2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Vector2d(0, 0)

        ///            x-axis
        ///        O------>
        ///        |
        ///        |
        /// y-axis v
        val NORTH_WEST = Vector2d(-1, -1)
        val NORTH = Vector2d(0, -1)
        val NORTH_EAST = Vector2d(1, -1)
        val EAST = Vector2d(1, 0)
        val SOUTH_EAST = Vector2d(1, 1)
        val SOUTH = Vector2d(0, 1)
        val SOUTH_WEST = Vector2d(-1, 1)
        val WEST = Vector2d(-1, 0)

        val HORIZONTAL_ADJACENT = listOf(WEST, EAST)
        val VERTICAL_ADJACENT = listOf(NORTH, SOUTH)

        val ORTHOGONAL_ADJACENT = listOf(NORTH, EAST, SOUTH, WEST)
        val DIAGONAL_ADJACENT = listOf(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST)

        val SURROUNDING = ORTHOGONAL_ADJACENT + DIAGONAL_ADJACENT

        fun forDirection(direction: Direction): Vector2d = when (direction) {
            Direction.NORTH_WEST -> NORTH_WEST
            Direction.NORTH -> NORTH
            Direction.NORTH_EAST -> NORTH_EAST
            Direction.EAST -> EAST
            Direction.SOUTH_EAST -> SOUTH_EAST
            Direction.SOUTH -> SOUTH
            Direction.SOUTH_WEST -> SOUTH_WEST
            Direction.WEST -> WEST
        }
    }

    operator fun times(scalar: Int) = Vector2d(x * scalar, y * scalar)

    fun orthogonal() = buildSet {
        if (x != 0) add(Vector2d(x, 0))
        if (y != 0) add(Vector2d(0, y))
        if (x == 0 && y == 0) add(ZERO)
    }

    fun orthogonalOptions() = buildList {
        when {
            x != 0 && y != 0 -> {
                add(buildList {
                    add(Vector2d(x, 0))
                    add(Vector2d(0, y))
                })
                add(buildList {
                    add(Vector2d(0, y))
                    add(Vector2d(x, 0))
                })
            }

            x != 0 -> add(listOf(Vector2d(x, 0)))
            y != 0 -> add(listOf(Vector2d(0, y)))
            else -> add(listOf(ZERO))
        }
    }

}

data class Area2d(
    val points: Set<Point2d>
) {
    companion object {
        fun areas(points: List<Point2d>): List<Area2d> {
            val remaining = points.toMutableList()
            val areas = mutableSetOf<List<Point2d>>()

            while (remaining.isNotEmpty()) {
                val region2 = remaining.first().neighbours(ORTHOGONAL_ADJACENT) { remaining.contains(it) && remaining.remove(it) }
                remaining.removeAll(region2)
                areas += region2
            }

            return areas.map { Area2d(it.toSet()) }
        }
    }

    fun area() = points.size
    fun perimeter() = sides().sum()

    fun sides(): List<Int> {
        val borders: Map<Point2d, MutableSet<Direction>> = points.associateWith { point -> Direction.ORTHOGONAL.filter { !points.contains(point + it) }.toMutableSet() }

        return points.flatMap { point ->
            borders[point]!!.toList().map { direction ->
                val visited = point.neighbours(ORTHOGONAL_ADJACENT) { points.contains(it) && borders[it]!!.contains(direction) }
                visited.forEach { borders[it]!!.remove(direction) }
                visited.size
            }
        }
    }

}

data class Rectangle2d(
    val x: IntRange,
    val y: IntRange
) {
    fun area() = x.length() * y.length()
    fun perimeter() = 2 * (x.length() + y.length())
    fun points() = x.flatMap { a -> y.map { b -> Point2d(a, b) } }
    fun contains(p: Point2d) = p.x in x && p.y in y
}

data class Point2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Point2d(0, 0)

        fun regionsFor(points: Collection<Point2d>): List<Rectangle2d> {
            val remaining = points.sortedWith(compareBy({ it.x }, { it.y })).toMutableSet()
            val regions = mutableListOf<Rectangle2d>()

            while (remaining.isNotEmpty()) {
                val point = remaining.first()

                val columns = (0..Int.MAX_VALUE).asSequence()
                    .map { i -> point + (Vector2d.EAST * i) }
                    .takeWhile { remaining.contains(it) }
                    .toList()
                val rows = (0..Int.MAX_VALUE).asSequence()
                    .map { i -> columns.map { it + Vector2d.SOUTH * i } }
                    .takeWhile { it.all { remaining.contains(it) } }
                    .toList()

                remaining -= rows.flatMap { it }
                regions += Rectangle2d(point.x..<(point.x + columns.size), point.y..<(point.y + rows.size))
            }

            return regions
        }
    }

    constructor(coordinates: List<Int>) : this(coordinates[0], coordinates[1])

    operator fun plus(p: Point2d) = Point2d(x + p.x, y + p.y)

    operator fun minus(p: Point2d) = Vector2d(x - p.x, y - p.y)

    operator fun times(p: Point2d) = Point2d(x * p.x, y * p.y)

    fun manhattan(p: Point2d) = abs(x - p.x) + abs(y - p.y)

    operator fun plus(v: Vector2d) = Point2d(x + v.x, y + v.y)

    operator fun minus(v: Vector2d) = Point2d(x - v.x, y - v.y)

    operator fun plus(direction: Direction) = this + Vector2d.forDirection(direction)

    operator fun plus(directions: Collection<Direction>) = directions.map { this + it }

    operator fun minus(direction: Direction) = this - Vector2d.forDirection(direction)

    infix fun mod(d: Dimension) = Point2d(x.mod(d.width), y.mod(d.height))

    fun neighbours(directions: List<Vector2d>) = directions.map { this + it }

    fun neighbours(directions: List<Vector2d>, predicate: (Point2d) -> Boolean): List<Point2d> {
        val nextPoints = mutableListOf(this)
        val visited = mutableListOf<Point2d>()

        while (nextPoints.isNotEmpty()) {
            val nextPoint = nextPoints.removeFirst()
            visited += nextPoint
            nextPoints += directions.map { nextPoint + it }
                .filter { !visited.contains(it) && predicate(it) }
        }

        return visited
    }

    fun towards(p: Point2d): Vector2d = Vector2d(p.x - x, p.y - y)

    infix fun to(p: Point2d) = Rectangle2d(x..p.x, y..p.y)

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> throw IllegalArgumentException("Unsupported axis $axis")
    }

}

data class Line2d(
    val p1: Point2d,
    val p2: Point2d
) {
    fun slope() = (p2.y - p1.y) / (p2.x - p1.x)
    fun yIntercept() = p1.y - slope() * p1.x

    fun toFunction() = LinearFunction2d(slope(), yIntercept())

    fun intersection(other: Line2d) = (toFunction() intersect other.toFunction())
        .let { (x, y) -> Point2d(x.roundToInt(), y.roundToInt()) }
}

data class Circle(
    private val center: Point2d = Point2d.ZERO,
    private val radius: Double
) {
    fun diameter(): Double = radius * 2

    fun area(): Double = PI * radius * radius
}