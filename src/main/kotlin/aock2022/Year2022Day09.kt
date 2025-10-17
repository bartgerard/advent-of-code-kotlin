package aock2022

import shared.Direction
import shared.Point2d
import shared.sanitize
import kotlin.math.absoluteValue

data class Year2022Day09(
    private val motions: List<Pair<Direction, Int>>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split(' ') }
            .map { Direction.parse(it[0].first()) to it[1].toInt() }
    )

    fun partOne() = uniqueTailPositionsAfterMotion()

    fun partTwo() = uniqueTailPositionsAfterMotion(10)

    private fun moves() = motions.asSequence()
        .flatMap { (direction, length) -> generateSequence { direction }.take(length) }

    private fun uniqueTailPositionsAfterMotion(ropeLength: Int = 2): Int {
        val rope = Rope(length = ropeLength)
        return moves().map {
            rope.move(it)
            rope.tail()
        }
            .toSet()
            .size
    }
}

data class Rope(
    val knots: MutableList<Point2d>,
) {
    constructor(length: Int) : this(
        (0..<length)
            .map { Point2d.ZERO }
            .toMutableList()
    )

    fun head() = knots.first()
    fun tail() = knots.last()

    fun move(direction: Direction) {
        knots[0] = knots[0] + direction
        followHead()
    }

    private fun followHead() {
        // TODO clean up / make immutable
        //val res = knots.zipWithNext { head, tail -> nextTail(head, tail) }
        (1..<knots.size).forEach { i ->
            val difference = knots[i - 1] - knots[i]

            if (difference.x.absoluteValue > 1 || difference.y.absoluteValue > 1) {
                knots[i] = knots[i] + difference.sign()
            }
        }
    }

    private fun nextTail(
        head: Point2d,
        tail: Point2d
    ): Point2d {
        val difference = head - tail

        return if (difference.x.absoluteValue > 1 || difference.y.absoluteValue > 1) {
            tail + difference.sign()
        } else {
            tail
        }
    }
}