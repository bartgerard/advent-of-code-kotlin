package shared

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
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    NORTH_WEST;

    companion object {
        val UP = NORTH
        val RIGHT = EAST
        val DOWN = SOUTH
        val LEFT = WEST

        val CARDINAL_DIRECTIONS = listOf(NORTH, EAST, SOUTH, WEST)
        val ORDINAL_DIRECTIONS = listOf(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST)

        val ORTHOGONAL = CARDINAL_DIRECTIONS
        val DIAGONAL = ORDINAL_DIRECTIONS

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

    fun inverse(): Direction {
        return when (this) {
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
            NORTH_EAST -> SOUTH_WEST
            SOUTH_EAST -> NORTH_WEST
            SOUTH_WEST -> NORTH_EAST
            NORTH_WEST -> SOUTH_EAST
        }
    }

    fun rotateRight(): Direction = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
        NORTH_EAST -> TODO()
        SOUTH_EAST -> TODO()
        SOUTH_WEST -> TODO()
        NORTH_WEST -> TODO()
    }

    fun rotateLeft(): Direction = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
        NORTH_EAST -> TODO()
        SOUTH_EAST -> TODO()
        SOUTH_WEST -> TODO()
        NORTH_WEST -> TODO()
    }

    fun flipVertical(): Direction = when (this) {
        NORTH -> SOUTH
        EAST -> EAST
        SOUTH -> NORTH
        WEST -> WEST
        NORTH_EAST -> TODO()
        SOUTH_EAST -> TODO()
        SOUTH_WEST -> TODO()
        NORTH_WEST -> TODO()
    }
}

data class Vector2d(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Vector2d(0, 0)

        val ORTHOGONAL_ADJACENT = Direction.ORTHOGONAL.map { forDirection(it) }

        val DIAGONAL_ADJACENT = Direction.DIAGONAL.map { forDirection(it) }

        val SURROUNDING = ORTHOGONAL_ADJACENT + DIAGONAL_ADJACENT

        fun forDirection(direction: Direction): Vector2d {
            return when (direction) {
                Direction.NORTH -> Vector2d(0, 1)
                Direction.NORTH_EAST -> Vector2d(1, 1)
                Direction.EAST -> Vector2d(1, 0)
                Direction.SOUTH_EAST -> Vector2d(1, -1)
                Direction.SOUTH -> Vector2d(0, -1)
                Direction.SOUTH_WEST -> Vector2d(-1, -1)
                Direction.WEST -> Vector2d(-1, 0)
                Direction.NORTH_WEST -> Vector2d(-1, 1)
            }
        }
    }

    operator fun times(scalar: Int) = Vector2d(x * scalar, y * scalar)

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

    fun orthogonalNeighbours() = Vector2d.ORTHOGONAL_ADJACENT.map { this + it }

    fun diagonalNeighbours() = Vector2d.DIAGONAL_ADJACENT.map { this + it }

    fun neighbours() = Vector2d.SURROUNDING.map { this + it }

    fun towards(p: Point2d): Vector2d = Vector2d(p.x - x, p.y - y)

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