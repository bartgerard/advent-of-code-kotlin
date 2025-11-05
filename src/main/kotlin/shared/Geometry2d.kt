package shared

import shared.Vector2d.Companion.ORTHOGONAL_ADJACENT
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign
import kotlin.math.sqrt

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

    constructor(coordinates: List<Int>) : this(coordinates[0], coordinates[1])

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

    fun sign() = Vector2d(x.sign, y.sign)

    fun slope() = this.y.toDouble() / this.x.toDouble()

    fun side() = when {
        this.x < 0 -> "left"
        this.x > 0 -> "right"
        else -> when {
            this.y < 0 -> "top"
            this.y > 0 -> "bottom"
            else -> "center"
        }
    }

    fun length() = sqrt((this.x * this.x + this.y * this.y).toDouble())

    infix fun dot(v: Vector2d) = x * v.x + y * v.y

    infix fun cross(v: Vector2d) = x * v.y - y * v.x // fictional z

    fun angleTo(v: Vector2d): Angle {
        val lengths = this.length() * v.length()

        if (lengths == 0.0) {
            return Angle(0.0)
        }

        val cosTheta = (this.dot(v) / lengths).coerceIn(-1.0, 1.0)

        return Angle(acos(cosTheta))
    }

    fun signedAnleTo(v: Vector2d): Angle {
        val lengths = this.length() * v.length()

        if (lengths == 0.0) {
            return Angle(0.0)
        }

        val cosTheta = (this.dot(v) / lengths).coerceIn(-1.0, 1.0)
        val sinTheta = this.cross(v) / lengths

        return Angle(atan2(sinTheta, cosTheta))
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
                val region2 = remaining.first().allDirectAndIndirectNeighbours(ORTHOGONAL_ADJACENT) { remaining.contains(it) && remaining.remove(it) }
                remaining.removeAll(region2)
                areas += region2
            }

            return areas.map { Area2d(it.toSet()) }
        }
    }

    val area get() = points.size
    val perimeter get() = sides().sum()

    fun sides(): List<Int> {
        val borders: Map<Point2d, MutableSet<Direction>> = points.associateWith { point -> Direction.ORTHOGONAL.filter { !points.contains(point + it) }.toMutableSet() }

        return points.flatMap { point ->
            borders[point]!!.toList().map { direction ->
                val visited = point.allDirectAndIndirectNeighbours(ORTHOGONAL_ADJACENT) { points.contains(it) && borders[it]!!.contains(direction) }
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
    companion object {
        fun of(p1: Point2d, p2: Point2d) = Rectangle2d(
            min(p1.x, p2.x)..max(p1.x, p2.x),
            min(p1.y, p2.y)..max(p1.y, p2.y)
        )

        fun surrounding(points: Collection<Point2d>): Rectangle2d {
            val (minX, maxX) = points.map { it.x }.minMaxOrNull()!!
            val (minY, maxY) = points.map { it.y }.minMaxOrNull()!!

            return Rectangle2d(
                minX..maxX,
                minY..maxY,
            )
        }
    }

    val area get() = x.length() * y.length()
    val perimeter get() = 2 * (x.length() + y.length())

    fun points() = x.asSequence().flatMap { a -> y.map { b -> Point2d(a, b) } }
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

                remaining -= rows.flatMap { it }.toSet()
                regions += Rectangle2d(point.x..<(point.x + columns.size), point.y..<(point.y + rows.size))
            }

            return regions
        }
    }

    constructor(coordinates: List<Int>) : this(coordinates[0], coordinates[1])

    operator fun plus(p: Point2d) = Point2d(x + p.x, y + p.y)

    operator fun minus(p: Point2d) = Vector2d(x - p.x, y - p.y)

    operator fun times(p: Point2d) = Point2d(x * p.x, y * p.y)

    fun manhattan(p: Point2d) = (x - p.x).absoluteValue + (y - p.y).absoluteValue

    operator fun plus(v: Vector2d) = Point2d(x + v.x, y + v.y)

    operator fun minus(v: Vector2d) = Point2d(x - v.x, y - v.y)

    operator fun plus(direction: Direction) = this + Vector2d.forDirection(direction)

    operator fun plus(directions: Collection<Direction>) = directions.map { this + it }

    operator fun minus(direction: Direction) = this - Vector2d.forDirection(direction)

    infix fun mod(d: Dimension) = Point2d(x.mod(d.width), y.mod(d.height))

    infix fun modWidth(width: Int) = Point2d(x.mod(width), y)
    infix fun modX(d: Dimension) = modWidth(d.width)

    infix fun modHeight(height: Int) = Point2d(x, y.mod(height))
    infix fun modY(d: Dimension) = modHeight(d.height)

    fun neighbours(
        directions: List<Vector2d> = ORTHOGONAL_ADJACENT
    ) = directions.map { this + it }

    fun allDirectAndIndirectNeighbours(
        directions: List<Vector2d> = ORTHOGONAL_ADJACENT,
        predicate: (Point2d) -> Boolean
    ): List<Point2d> {
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

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> error("Unsupported axis $axis")
    }

    operator fun rangeTo(direction: Direction): Sequence<Point2d> = rangeTo(Vector2d.forDirection(direction))
    operator fun rangeTo(v: Vector2d): Sequence<Point2d> = generateSequence(0) { it + 1 }
        .map { this + v * it }

}

data class Ray2d(
    val point: Point2d = Point2d.ZERO,
    val direction: Vector2d
)

// Pose?
data class OrientedPoint(
    val point: Point2d = Point2d.ZERO,
    val direction: Direction
)

data class LineSegment2d(
    val p1: Point2d,
    val p2: Point2d
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

    fun intersect(other: LineSegment2d): Point2d? = toLinearEquation().intersect(other.toLinearEquation())
        ?.let { (x, y) -> Point2d(x.toInt(), y.toInt()) }

    fun contains(p: Point2d) = p.x in minOf(p1.x, p2.x)..maxOf(p1.x, p2.x)
            && p.y in minOf(p1.y, p2.y)..maxOf(p1.y, p2.y)

    fun intersectWithinSegment(other: LineSegment2d): Point2d? = intersect(other)
        ?.let { if (contains(it) && other.contains(it)) it else null }

    fun isVertical() = p1.x == p2.x && p1.y != p2.y

    fun intersectDiscrete(other: LineSegment2d): List<Point2d> {
        if (!isVertical() && !other.isVertical()
            && slope() == other.slope()
            && yIntercept() == other.yIntercept()
        ) {
            // TODO
            return (p1.x..p2.x).intersect(other.p1.x..other.p2.x)
                .map { Point2d(it, yIntercept()) }
        } else if (isVertical() && other.isVertical() && p1.x == other.p1.x) {
            // TODO
            return (p1.y..p2.y).intersect(other.p1.y..p2.y).map { Point2d(p1.x, it) }
        }

        return intersect(other)?.let { listOf(it) } ?: emptyList()
    }
}

data class Circle(
    private val center: Point2d = Point2d.ZERO,
    private val radius: Double
) {
    val diameter get(): Double = radius * 2

    val area get(): Double = PI * radius * radius
}

data class Angle(
    val radians: Double
) {
    companion object {
        const val TAU = 2 * PI
        val ZERO = Angle(0.0)
    }

    val degrees get() = Math.toDegrees(radians)

    fun toDegrees360(turn: Turn = Turn.COUNTER_CLOCKWISE) = degrees
        .let { degrees -> ((degrees % 360) + 360) % 360 }
        .let { normalized ->
            when (turn) {
                Turn.COUNTER_CLOCKWISE -> normalized
                Turn.CLOCKWISE -> (360 - normalized) % 360
            }
        }

    fun toRadians2Pi(turn: Turn = Turn.COUNTER_CLOCKWISE) = (((radians % TAU) + TAU) % TAU)
        .let { normalized ->
            when (turn) {
                Turn.COUNTER_CLOCKWISE -> normalized
                Turn.CLOCKWISE -> (TAU - normalized) % TAU
            }
        }

    operator fun plus(other: Angle) = Angle(radians + other.radians)
    operator fun minus(other: Angle) = Angle(radians - other.radians)
    operator fun times(factor: Double) = Angle(radians * factor)
    operator fun div(divisor: Double) = Angle(radians / divisor)
}