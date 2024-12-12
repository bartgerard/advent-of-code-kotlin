package shared

import kotlin.math.PI
import kotlin.math.abs

data class Vector2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Vector2d(0, 0)

        val NORTH_WEST = Vector2d(-1, -1)
        val NORTH = Vector2d(0, -1)
        val NORTH_EAST = Vector2d(1, -1)
        val EAST = Vector2d(1, 0)
        val SOUTH_EAST = Vector2d(1, 1)
        val SOUTH = Vector2d(0, 1)
        val SOUTH_WEST = Vector2d(-1, 1)
        val WEST = Vector2d(-1, 0)

        val HORIZONTAL_ADJACENT = Direction.HORIZONTAL.map { forDirection(it) }

        val VERTICAL_ADJACENT = Direction.VERTICAL.map { forDirection(it) }

        val ORTHOGONAL_ADJACENT = Direction.ORTHOGONAL.map { forDirection(it) }

        val DIAGONAL_ADJACENT = Direction.DIAGONAL.map { forDirection(it) }

        val SURROUNDING = ORTHOGONAL_ADJACENT + DIAGONAL_ADJACENT

        fun forDirection(direction: Direction): Vector2d = when (direction) {
            ///            x-axis
            ///        O------>
            ///        |
            ///        |
            /// y-axis v
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

}

data class Area2d(
    val points: List<Point2d>
) {
    companion object {
        fun areas(points: List<Point2d>): List<Area2d> {
            val remaining = points.toMutableList()
            val areas = mutableListOf<List<Point2d>>()

            while (remaining.isNotEmpty()) {
                val region = mutableListOf(remaining.removeFirst())
                var i = 0
                while (i < region.size) {
                    val point = region[i]
                    val neighbours = point.orthogonalNeighbours().filter { !region.contains(it) }
                    val sameRegion = neighbours.filter { remaining.contains(it) }
                    remaining.removeAll(sameRegion)
                    region.addAll(sameRegion)
                    i++
                }
                areas += region
            }

            return areas.map { Area2d(it) }
        }
    }

    fun walkPerimeter() = points.flatMap { it.orthogonalNeighbours() }.filter { !points.contains(it) }

    fun sides(): List<Int> {
        if (points.isEmpty()) {
            return emptyList()
        } else if (points.size == 1) {
            return listOf(1, 1, 1, 1)
        }

        val borders: Map<Point2d, MutableSet<Direction>> = points.associateWith { point -> Direction.ORTHOGONAL.filter { !points.contains(point + it) }.toMutableSet() }

        val sides = mutableListOf<Int>()
        val remaining = points.toMutableList()

        while (remaining.isNotEmpty()) {
            val point = remaining.removeFirst()
            val directions = borders[point] ?: continue

            if (directions.isEmpty()) {
                continue
            } else if (directions.size == 4) {
                sides += listOf(1, 1, 1, 1)
                continue
            }

            for (direction in directions.toList()) {
                val nextPoints = mutableListOf(point)
                val visited = mutableListOf<Point2d>()

                while (nextPoints.isNotEmpty()) {
                    val nextPoint = nextPoints.removeFirst()
                    visited += nextPoint
                    nextPoints += nextPoint.orthogonalNeighbours().filter { !visited.contains(it) && remaining.contains(it) && borders[it]!!.contains(direction) }
                }

                visited.forEach { borders[it]!!.remove(direction) }

                sides += visited.size
            }
        }

        return sides
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

    operator fun minus(direction: Direction) = this - Vector2d.forDirection(direction)

    fun horizontalNeighbours() = Vector2d.HORIZONTAL_ADJACENT.map { this + it }

    fun verticalNeighbours() = Vector2d.VERTICAL_ADJACENT.map { this + it }

    fun orthogonalNeighbours() = Vector2d.ORTHOGONAL_ADJACENT.map { this + it }

    fun diagonalNeighbours() = Vector2d.DIAGONAL_ADJACENT.map { this + it }

    fun neighbours() = Vector2d.SURROUNDING.map { this + it }

    fun towards(p: Point2d): Vector2d = Vector2d(p.x - x, p.y - y)

    infix fun to(p: Point2d) = Rectangle2d(
        x..p.x,
        y..p.y
    )

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> throw IllegalArgumentException("Unsupported axis $axis")
    }

}

data class Line2d(
    private val p1: Point2d,
    private val p2: Point2d
)

data class Circle(
    private val center: Point2d = Point2d.ZERO,
    private val radius: Double
) {
    fun diameter(): Double = radius * 2

    fun area(): Double = PI * radius * radius
}