package aock2021

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.sanitize
import kotlin.math.absoluteValue

data class Year2021Day02(
    private val input: List<Vector2dInt>
) {
    companion object {
        fun toVector(direction: String, distance: Int) = when (direction) {
            "forward" -> Vector2dInt(distance, 0)
            "up" -> Vector2dInt(0, -distance)
            "down" -> Vector2dInt(0, distance)
            else -> error("invalid direction $direction")
        }
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .map { (direction, distance) -> toVector(direction, distance) }
    )

    fun partOne() = input
        .fold(Point2dInt.ZERO) { point, vector -> point + vector }
        .let { (x, y) -> x.absoluteValue * y.absoluteValue }

    fun partTwo() = input
        .fold(Point2dInt.ZERO to 0) { (point, aim), vector ->
            when {
                vector.y == 0 -> point + Vector2dInt(vector.x, vector.x * aim) to aim
                else -> point to (aim + vector.y)
            }
        }
        .let { (point, _) -> point }
        .let { (x, y) -> x.absoluteValue * y.absoluteValue }
}