package shared

import shared.CornerState.IN
import shared.CornerState.OUT

enum class CornerState {
    IN,
    OUT;

    fun inverse() = when (this) {
        IN -> OUT
        OUT -> IN
    }
}

// OUT     IN  | OUT     OUT | OUT     IN
//      X      |      X      |      X
// OUT     IN  | OUT     IN  | OUT     OUT

// IN      OUT | IN      OUT | IN      IN
//      X      |      X      |      X
// IN      OUT | IN      IN  | IN      OUT

data class Corners(
    val upperLeft: CornerState,
    val upperRight: CornerState,
    val lowerLeft: CornerState,
    val lowerRight: CornerState,
) {
    companion object {
        val OUTSIDE = Corners(OUT, OUT, OUT, OUT)
        val INSIDE = Corners(IN, IN, IN, IN)
    }
}