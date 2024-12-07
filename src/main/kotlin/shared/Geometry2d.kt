package shared

import kotlin.math.PI
import kotlin.math.abs

data class Vector2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Vector2d(0, 0)

        val ORTHOGONAL_ADJACENT = Direction.ORTHOGONAL.map { forDirection(it) }

        val DIAGONAL_ADJACENT = Direction.DIAGONAL.map { forDirection(it) }

        val SURROUNDING = ORTHOGONAL_ADJACENT + DIAGONAL_ADJACENT

        fun forDirection(direction: Direction): Vector2d = when (direction) {
            ///            x-axis
            ///        O------>
            ///        |
            ///        |
            /// y-axis v
            Direction.NORTH_WEST -> Vector2d(-1, -1)
            Direction.NORTH -> Vector2d(0, -1)
            Direction.NORTH_EAST -> Vector2d(1, -1)
            Direction.EAST -> Vector2d(1, 0)
            Direction.SOUTH_EAST -> Vector2d(1, 1)
            Direction.SOUTH -> Vector2d(0, 1)
            Direction.SOUTH_WEST -> Vector2d(-1, 1)
            Direction.WEST -> Vector2d(-1, 0)
        }
    }

    operator fun times(scalar: Int) = Vector2d(x * scalar, y * scalar)

}

data class Point2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Point2d(0, 0)
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

    fun orthogonalNeighbours() = Vector2d.ORTHOGONAL_ADJACENT.map { this + it }

    fun diagonalNeighbours() = Vector2d.DIAGONAL_ADJACENT.map { this + it }

    fun neighbours() = Vector2d.SURROUNDING.map { this + it }

    fun towards(p: Point2d): Vector2d = Vector2d(p.x - x, p.y - y)

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