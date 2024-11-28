package shared

import kotlin.math.abs

data class Box3d(
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

data class Vector3d(
    val x: Int,
    val y: Int,
    val z: Int = 0
)

enum class Direction(
    val vector: Vector3d
) {
    UP(
        Vector3d(1, 0)
    ),
    RIGHT(
        Vector3d(0, 1)
    ),
    DOWN(
        Vector3d(-1, 0)
    ),
    LEFT(
        Vector3d(0, -1)
    );

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

data class Point3d(
    private val x: Int,
    private val y: Int,
    private val z: Int
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