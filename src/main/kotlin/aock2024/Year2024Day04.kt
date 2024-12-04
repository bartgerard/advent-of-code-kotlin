package aock2024

import shared.Point2d
import shared.byLine

data class Year2024Day04(
    private val chars: List<CharArray>
) {
    constructor(input: String) : this(input.byLine().map { it.toCharArray() })

    fun partOne(): Int {
        val points = chars.flatMapIndexed { row, line ->
            line.indices
                .filter { line[it] == 'X' }
                .map { Point2d(it, row) }
        }

        return points.sumOf { point ->
            neighboursWithCharacter(point, 'M')
                .distinct()
                .flatMap { neighboursWithCharacter(it, 'A') }
                .distinct()
                .flatMap { neighboursWithCharacter(it, 'S') }
                .distinct()
                .count()
        }
    }

    private fun neighboursWithCharacter(point: Point2d, character: Char): Sequence<Point2d> = point.neighbours()
        .filter { it.x >= 0 && it.y >= 0 && it.y < chars.size && it.x < chars[it.y].size }
        .distinct()
        .filter { chars[it.y][it.x] == character }

    fun partTwo(): Int = 0
}