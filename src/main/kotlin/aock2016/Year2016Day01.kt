package aock2016

import shared.firstRepeated
import shared.geometry2d.OrientedPoint
import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.sanitize
import shared.spatial.Direction
import shared.spatial.Turn

data class Year2016Day01(
    private val input: List<Pair<Turn, Int>>
) {
    constructor(input: String) : this(
        input.sanitize().split(", ")
            .map { Turn.parse(it[0]) to it.substring(1).toInt() }
    )

    fun partOne(): Int = blocksAway(steps().last())

    fun partTwo() = allSteps().firstRepeated()?.let { blocksAway(it) } ?: 0

    private fun blocksAway(point: Point2dInt): Int = Point2dInt.ZERO.manhattan(point)

    private fun steps() = input.scan(OrientedPoint(direction = Direction.NORTH)) { (point, direction), (turn, blocks) ->
        val newDirection = direction.rotate(turn)
        val newPoint = point + Vector2dInt.forDirection(newDirection) * blocks
        OrientedPoint(newPoint, newDirection)
    }
        .map { it.point }

    private fun allSteps() = sequence {
        var point = Point2dInt.ZERO
        var direction = Direction.NORTH

        input.forEach { (turn, blocks) ->
            direction = direction.rotate(turn)

            repeat(blocks) {
                point += direction
                yield(point)
            }
        }
    }

}