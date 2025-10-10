package aock2022

import shared.Direction
import shared.Point2d
import shared.sanitize
import kotlin.math.abs

data class Year2022Day09(
    private val motions: List<Pair<Direction, Int>>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split(' ') }
            .map { Direction.parse(it[0].first()) to it[1].toInt() }
    )

    fun partOne(): Int {
        val rope = Rope()
        return moves().map {
            rope.move(it)
            rope.tail
        }
            .toSet()
            .size
    }

    fun partTwo() = 0L

    private fun moves() = motions.asSequence()
        .flatMap { (direction, length) -> generateSequence { direction }.take(length) }
}

data class Rope(
    var head: Point2d = Point2d.ZERO,
    var tail: Point2d = Point2d.ZERO,
) {
    fun move(direction: Direction) {
        head += direction
        val difference = head - tail

        if (abs(difference.x) > 1 || abs(difference.y) > 1) {
            tail += difference.sign()
        }
    }
}