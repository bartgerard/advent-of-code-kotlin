package shared.geometry2d

import shared.spatial.Turn
import kotlin.math.PI

data class Angle(
    val radians: Double
) {
    companion object {
        const val TAU = 2 * PI
        val ZERO = Angle(0.0)
    }

    val degrees get() = Math.toDegrees(radians)

    fun toDegrees360(turn: Turn = Turn.COUNTER_CLOCKWISE) = degrees
        .let { degrees -> ((degrees % 360) + 360) % 360 }
        .let { normalized ->
            when (turn) {
                Turn.COUNTER_CLOCKWISE -> normalized
                Turn.CLOCKWISE -> (360 - normalized) % 360
            }
        }

    fun toRadians2Pi(turn: Turn = Turn.COUNTER_CLOCKWISE) = (((radians % TAU) + TAU) % TAU)
        .let { normalized ->
            when (turn) {
                Turn.COUNTER_CLOCKWISE -> normalized
                Turn.CLOCKWISE -> (TAU - normalized) % TAU
            }
        }

    operator fun plus(other: Angle) = Angle(radians + other.radians)
    operator fun minus(other: Angle) = Angle(radians - other.radians)
    operator fun times(factor: Double) = Angle(radians * factor)
    operator fun div(divisor: Double) = Angle(radians / divisor)
}