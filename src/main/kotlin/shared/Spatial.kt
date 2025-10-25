package shared

enum class Axis {
    X,
    Y,
    Z;

    companion object {
        val TWO_DIMENSIONAL = listOf(X, Y)
        val THREE_DIMENSIONAL = listOf(X, Y, Z)
    }
}

enum class Turn {
    CLOCKWISE,
    COUNTER_CLOCKWISE;

    companion object {
        val LEFT = COUNTER_CLOCKWISE
        val RIGHT = CLOCKWISE

        fun parse(value: Char) = when (value) {
            'L' -> LEFT
            'R' -> RIGHT
            else -> error("Unknown turn $value")
        }
    }

    fun inverse() = when (this) {
        CLOCKWISE -> COUNTER_CLOCKWISE
        COUNTER_CLOCKWISE -> CLOCKWISE
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

        val VERTICAL = listOf(NORTH, SOUTH)
        val HORIZONTAL = listOf(WEST, EAST)
        val ORTHOGONAL = CARDINAL_DIRECTIONS
        val DIAGONAL = ORDINAL_DIRECTIONS

        fun parse(value: Char): Direction = when (value) {
            '^' -> UP
            '>' -> RIGHT
            'v' -> DOWN
            '<' -> LEFT

            'U' -> UP
            'R' -> RIGHT
            'D' -> DOWN
            'L' -> LEFT

            else -> error("$value is not a direction")
        }

    }

    fun inverse() = when (this) {
        NORTH -> SOUTH
        EAST -> WEST
        SOUTH -> NORTH
        WEST -> EAST
        NORTH_EAST -> SOUTH_WEST
        SOUTH_EAST -> NORTH_WEST
        SOUTH_WEST -> NORTH_EAST
        NORTH_WEST -> SOUTH_EAST
    }

    fun rotate(turn: Turn) = when (turn) {
        Turn.COUNTER_CLOCKWISE -> rotateLeft()
        Turn.CLOCKWISE -> rotateRight()
    }

    fun rotateLeft() = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
        NORTH_EAST -> NORTH_WEST
        SOUTH_EAST -> NORTH_EAST
        SOUTH_WEST -> SOUTH_EAST
        NORTH_WEST -> SOUTH_WEST
    }

    fun rotateRight() = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
        NORTH_EAST -> SOUTH_EAST
        SOUTH_EAST -> SOUTH_WEST
        SOUTH_WEST -> NORTH_WEST
        NORTH_WEST -> NORTH_EAST
    }

    fun flipVertical() = when (this) {
        NORTH -> SOUTH
        EAST -> EAST
        SOUTH -> NORTH
        WEST -> WEST
        NORTH_EAST -> SOUTH_EAST
        SOUTH_EAST -> NORTH_EAST
        SOUTH_WEST -> NORTH_WEST
        NORTH_WEST -> SOUTH_WEST
    }
}