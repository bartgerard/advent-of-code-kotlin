package shared

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

        fun parse(value: Char): Direction = when (value) {
            '^' -> UP
            '>' -> RIGHT
            'v' -> DOWN
            '<' -> LEFT
            else -> throw IllegalArgumentException("$value is not a direction")
        }

    }

    fun inverse(): Direction = when (this) {
        NORTH -> SOUTH
        EAST -> WEST
        SOUTH -> NORTH
        WEST -> EAST
        NORTH_EAST -> SOUTH_WEST
        SOUTH_EAST -> NORTH_WEST
        SOUTH_WEST -> NORTH_EAST
        NORTH_WEST -> SOUTH_EAST
    }

    fun rotateRight(): Direction = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
        NORTH_EAST -> SOUTH_EAST
        SOUTH_EAST -> SOUTH_WEST
        SOUTH_WEST -> NORTH_WEST
        NORTH_WEST -> NORTH_EAST
    }

    fun rotateLeft(): Direction = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
        NORTH_EAST -> NORTH_WEST
        SOUTH_EAST -> NORTH_EAST
        SOUTH_WEST -> SOUTH_EAST
        NORTH_WEST -> SOUTH_WEST
    }

    fun flipVertical(): Direction = when (this) {
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