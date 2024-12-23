package aock2019

import shared.*

data class Year2019Day03(
    private val wires: List<Wire>
) {
    constructor(input: String) : this(input.sanitize().lines().map { Wire.parse(it) })

    fun partOne() = 0L
    fun partTwo() = 0L
}

data class Wire(
    val lines: List<Line2d>
) {
    companion object {
        fun parse(input: String): Wire {
            val vectors = input.split(',')
                .map { Vector2d.forDirection(Direction.parse(it[0])) * it.drop(1).toInt() }

            val points = vectors.fold(listOf(Point2d.ZERO)) { acc, vector -> acc + (acc.last() + vector) }

            return Wire(points.zipWithNext { p1, p2 -> Line2d(p1, p2) })
        }
    }
}