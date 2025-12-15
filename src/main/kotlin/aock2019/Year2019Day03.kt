package aock2019

import shared.geometry2d.LineSegment2dInt
import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.sanitize
import shared.spatial.Direction

data class Year2019Day03(
    private val wires: List<Wire>
) {
    constructor(input: String) : this(input.sanitize().lines().map { Wire.parse(it) })

    fun partOne() = wires.let { (w1, w2) ->
        w1.intersect(w2)
            .filterNot { it == Point2dInt.ZERO }
            .minOfOrNull { Point2dInt.ZERO.manhattan(it) }
    }

    fun partTwo() = wires.let { (w1, w2) ->
        w1.intersect(w2)
            .filterNot { it == Point2dInt.ZERO }
            .minOfOrNull { intersection -> wires.sumOf { it.lengthTo(intersection) } }
    }

}

data class Wire(
    val lines: List<LineSegment2dInt>
) {
    companion object {
        fun parse(input: String): Wire {
            val vectors = input.split(',')
                .map { Vector2dInt.forDirection(Direction.parse(it[0])) * it.drop(1).toInt() }

            val points = vectors.fold(listOf(Point2dInt.ZERO)) { acc, vector -> acc + (acc.last() + vector) }

            return Wire(points.zipWithNext { p1, p2 -> LineSegment2dInt(p1, p2) })
        }
    }

    fun intersect(other: Wire): Set<Point2dInt> =
        lines.map { line -> other.lines.mapNotNull { line.intersectWithinSegment(it) } }
            .flatten()
            .toSet()

    fun lengthTo(p: Point2dInt): Int = lines.dropLastWhile { !it.contains(p) }
        .let { l -> l.dropLast(1).sumOf { it.length() } + l.last().p1.manhattan(p) }

}