package shared

import shared.Direction.*
import kotlin.math.abs

data class Box(
    private val length: Long,
    private val width: Long,
    private val height: Long
) {
    fun volume(): Long {
        return length * width * height
    }

    fun surfaceArea(): Long {
        return 2 * (length * width + width * height + height * length)
    }

    fun areaOfSmallestSide(): Long {
        return sequenceOf(
            length * width,
            width * height,
            height * length
        )
            .min()
    }

    fun smallestPerimeter(): Long {
        return 2 * sequenceOf(
            length + width,
            width + height,
            height + length
        )
            .min()
    }
}

enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    companion object {
        val NORTH = UP
        val EAST = RIGHT
        val SOUTH = DOWN
        val WEST = LEFT

        fun parse(value: Char): Direction {
            return when (value) {
                '^' -> UP
                '>' -> RIGHT
                'v' -> DOWN
                '<' -> LEFT
                else -> throw IllegalArgumentException("$value is not a direction")
            }
        }
    }
}

data class Vector2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Vector2d(0, 0)

        fun forDirection(direction: Direction): Vector2d {
            return when (direction) {
                UP -> Vector2d(1, 0)
                RIGHT -> Vector2d(0, 1)
                DOWN -> Vector2d(-1, 0)
                LEFT -> Vector2d(0, -1)
            }
        }
    }

    operator fun times(scalar: Int) = Vector3d(x * scalar, y * scalar)

}

data class Vector3d(
    val x: Int,
    val y: Int,
    val z: Int = 0
) {
    companion object {
        val ZERO = Vector3d(0, 0)
    }

    operator fun times(scalar: Int) = Vector3d(x * scalar, y * scalar, z * scalar)
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

    operator fun minus(p: Point2d) = Point2d(x - p.x, y - p.y)

    operator fun times(p: Point2d) = Point2d(x * p.x, y * p.y)

    fun manhattan(p: Point2d) = abs(x - p.x) + abs(y - p.y)

    operator fun plus(v: Vector2d) = Point2d(x + v.x, y + v.y)

    operator fun minus(v: Vector2d) = Point2d(x - v.x, y - v.y)

    operator fun plus(direction: Direction) = this + Vector2d.forDirection(direction)

    operator fun minus(direction: Direction) = this - Vector2d.forDirection(direction)

    fun orthogonalNeighbours() = listOf(UP, DOWN, LEFT, RIGHT).map { this + it }

    fun neighbours() = sequenceOf(Vector2d(0, -1), Vector2d(-1, 0), Vector2d(-1, -1), Vector2d(1, -1), Vector2d(1, 0), Vector2d(1, 1), Vector2d(0, 1), Vector2d(-1, 1))
        .map { this + it }
        .filter { it.x >= 0 && it.y >= 0 }
}

data class Point3d(
    private val x: Int,
    private val y: Int,
    private val z: Int = 0
) {
    companion object {
        val ZERO = Point3d(0, 0, 0)
    }

    constructor(coordinates: List<Int>) : this(coordinates[0], coordinates[1], coordinates[2])

    operator fun plus(p: Point3d) = Point3d(x + p.x, y + p.y, z + p.z)

    operator fun minus(p: Point3d) = Point3d(x - p.x, y - p.y, z - p.z)

    operator fun times(p: Point3d) = Point3d(x * p.x, y * p.y, z * p.z)

    fun manhattan(p: Point3d) = abs(x - p.x) + abs(y - p.y) + abs(z - p.z)

    operator fun plus(v: Vector3d) = Point3d(x + v.x, y + v.y, z + v.z)

    operator fun minus(v: Vector3d) = Point3d(x - v.x, y - v.y, z - v.z)
}

data class Line2d(
    private val p1: Point2d,
    private val p2: Point2d
)