package shared.spatial

enum class Axis {
    X,
    Y,
    Z;

    companion object {
        val TWO_DIMENSIONAL = listOf(X, Y)
        val THREE_DIMENSIONAL = listOf(X, Y, Z)

        fun parse(value: Char) = when (value) {
            'X', 'x' -> X
            'Y', 'y' -> Y
            'Z', 'z' -> Z
            else -> error("Unknown axis $value")
        }
    }
}