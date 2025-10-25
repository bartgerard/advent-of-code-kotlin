package aock2016

import shared.Direction
import shared.OrientedPoint
import shared.Point2d
import shared.Turn
import shared.Vector2d
import shared.firstRepeated
import shared.sanitize

data class Year2016Day01(
    private val input: List<Pair<Turn, Int>>
) {
    constructor(input: String) : this(
        input.sanitize().split(", ")
            .map { Turn.parse(it[0]) to it.substring(1).toInt() }
    )

    fun partOne(): Int = blocksAway(steps().last())

    fun partTwo() = allSteps().firstRepeated()?.let { blocksAway(it) } ?: 0

    private fun blocksAway(point: Point2d): Int = Point2d.ZERO.manhattan(point)

    private fun steps() = input.scan(OrientedPoint(direction = Direction.NORTH)) { (point, direction), (turn, blocks) ->
        val newDirection = direction.rotate(turn)
        val newPoint = point + Vector2d.forDirection(newDirection) * blocks
        OrientedPoint(newPoint, newDirection)
    }
        .map { it.point }

    private fun allSteps() = sequence {
        var point = Point2d.ZERO
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