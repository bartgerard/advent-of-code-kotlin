package shared.spatial

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