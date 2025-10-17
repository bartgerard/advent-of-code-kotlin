package aock2017

import shared.Point2d
import shared.Spiral
import shared.Vector2d
import shared.length
import shared.sanitize
import kotlin.math.absoluteValue

data class Year2017Day03(
    private val value: Int
) {
    constructor(input: String) : this(input.sanitize().toInt())

    // 1, 9, 25, 49, 81..
    // 1^2, 3^2, 5^2, 7^2, 9^2..
    fun partOne(): Int {
        if (value == 1) {
            return 0
        }

        val ring = Ring.findRingFor(value)
        val sideLength = ring.sideLength()
        val side = ring.sides()
            .filter { value in it }
            .first()
        val middleOfSide = side.last - sideLength / 2
        val distanceToMiddleOfSide = (value - middleOfSide).absoluteValue

        return ring.manhattanRadius + distanceToMiddleOfSide
    }

    fun partTwo(): Long {
        val valueByPoint = mutableMapOf(Point2d.ZERO to 1L)

        return Spiral.generatePoints()
            .drop(1)
            .map { point ->
                (point to point.neighbours(Vector2d.SURROUNDING)
                    .sumOf { neighbour -> valueByPoint[neighbour] ?: 0L })
                    .also { valueByPoint += it }
            }
            .dropWhile { it.second <= value }
            .first()
            .second
    }

}

data class Ring(
    val manhattanRadius: Int,
    val range: IntRange
) {
    companion object {
        fun findRingFor(value: Int) = generateSequence(1) { it + 2 }
            .map { it * it }
            .zipWithNext { a, b -> a + 1..b }
            .mapIndexed { index, range -> Ring(index + 1, range) }
            .filter { value in it.range }
            .first()
    }

    fun sideLength() = range.length() / 4

    fun sides(): Sequence<IntRange> = generateSequence(range.first) { it + sideLength() }
        .take(5)
        .zipWithNext { a, b -> a..<b }
}