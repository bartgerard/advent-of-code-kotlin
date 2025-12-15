package shared.geometry2d

import shared.spatial.Direction
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sign
import kotlin.math.sqrt

data class Vector2dInt(
    override val x: Int,
    override val y: Int
) : Vector2d<Int> {
    companion object {
        val ZERO = Vector2dInt(0, 0)

        ///            x-axis
        ///        O------>
        ///        |
        ///        |
        /// y-axis v
        val NORTH_WEST = Vector2dInt(-1, -1)
        val NORTH = Vector2dInt(0, -1)
        val NORTH_EAST = Vector2dInt(1, -1)
        val EAST = Vector2dInt(1, 0)
        val SOUTH_EAST = Vector2dInt(1, 1)
        val SOUTH = Vector2dInt(0, 1)
        val SOUTH_WEST = Vector2dInt(-1, 1)
        val WEST = Vector2dInt(-1, 0)

        val HORIZONTAL_ADJACENT = listOf(WEST, EAST)
        val VERTICAL_ADJACENT = listOf(NORTH, SOUTH)

        val ORTHOGONAL_ADJACENT = listOf(NORTH, EAST, SOUTH, WEST)
        val DIAGONAL_ADJACENT = listOf(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST)

        val SURROUNDING = ORTHOGONAL_ADJACENT + DIAGONAL_ADJACENT

        val KNIGHT_MOVES = listOf(
            Vector2dInt(-2, -1), Vector2dInt(-1, -2), // NW combinations
            Vector2dInt(1, -2), Vector2dInt(2, -1),   // NE combinations
            Vector2dInt(2, 1), Vector2dInt(1, 2),     // SE combinations
            Vector2dInt(-1, 2), Vector2dInt(-2, 1)    // SW combinations
        )

        fun forDirection(direction: Direction): Vector2dInt = when (direction) {
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

    override operator fun times(scalar: Int) = Vector2dInt(x * scalar, y * scalar)

    fun orthogonal() = buildSet {
        if (x != 0) add(Vector2dInt(x, 0))
        if (y != 0) add(Vector2dInt(0, y))
        if (x == 0 && y == 0) add(ZERO)
    }

    fun orthogonalOptions() = buildList {
        when {
            x != 0 && y != 0 -> {
                add(buildList {
                    add(Vector2dInt(x, 0))
                    add(Vector2dInt(0, y))
                })
                add(buildList {
                    add(Vector2dInt(0, y))
                    add(Vector2dInt(x, 0))
                })
            }

            x != 0 -> add(listOf(Vector2dInt(x, 0)))
            y != 0 -> add(listOf(Vector2dInt(0, y)))
            else -> add(listOf(ZERO))
        }
    }

    fun sign() = Vector2dInt(x.sign, y.sign)

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

    override fun length() = sqrt((this.x * this.x + this.y * this.y).toDouble())

    override fun dot(v: Vector2d<Int>) = x * v.x + y * v.y
    override fun cross(v: Vector2d<Int>) = x * v.y - y * v.x // fictional z

    fun angleTo(v: Vector2dInt): Angle {
        val lengths = this.length() * v.length()

        if (lengths == 0.0) {
            return Angle(0.0)
        }

        val cosTheta = (this.dot(v) / lengths).coerceIn(-1.0, 1.0)

        return Angle(acos(cosTheta))
    }

    fun signedAnleTo(v: Vector2d<Int>): Angle {
        val lengths = this.length() * v.length()

        if (lengths == 0.0) {
            return Angle(0.0)
        }

        val cosTheta = (this.dot(v) / lengths).coerceIn(-1.0, 1.0)
        val sinTheta = this.cross(v) / lengths

        return Angle(atan2(sinTheta, cosTheta))
    }
}