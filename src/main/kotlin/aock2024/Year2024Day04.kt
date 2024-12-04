package aock2024

import shared.Point2d
import shared.Vector2d
import shared.byLine

data class Year2024Day04(
    private val lines: List<String>
) {
    companion object {
        const val WORD: String = "XMAS"
    }

    constructor(input: String) : this(input.byLine())

    fun partOne(): Int {
        val points = lines.flatMapIndexed { row, line ->
            line.indices
                .filter { line[it] == WORD[0] }
                .map { Point2d(it, row) }
        }

        return points.sumOf { point ->
            Vector2d.SURROUNDING.count { vector ->
                WORD.indices.map { index -> index to point + vector * index }
                    .all { (index, point) -> isInBound(point) && WORD[index] == lines[point.y][point.x] }
            }
        }
    }

    private fun isInBound(point: Point2d): Boolean = point.y in lines.indices && point.x in 0..<lines[point.y].length

    private fun neighboursWithCharacter(point: Point2d, character: Char): List<Point2d> = point.neighbours()
        .filter { it.x >= 0 && it.y >= 0 && it.y < lines.size && it.x < lines[it.y].length }
        .filter { lines[it.y][it.x] == character }

    fun partTwo(): Int {

        val points = lines.flatMapIndexed { row, line ->
            line.indices
                .filter { line[it] == 'X' }
                .map { Point2d(it, row) }
        }

        return points.sumOf { point ->
            neighboursWithCharacter(point, 'M')
                .flatMap { neighboursWithCharacter(it, 'A') }
                .flatMap { neighboursWithCharacter(it, 'S') }
                .count()
        }
    }
}