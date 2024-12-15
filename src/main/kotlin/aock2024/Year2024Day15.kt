package aock2024

import shared.*

data class Year2024Day15(
    private val grid: PushableGrid,
    private val directions: List<List<Direction>>
) {
    companion object {
        const val EMPTY = '.'
        const val WALL = '#'
        const val ROBOT = '@'
        const val BOX = 'O'
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        PushableGrid(input[0], setOf(WALL), setOf(EMPTY)),
        input[1].lines().map { it.toList().map { Direction.parse(it) } }
    )

    fun partOne(): Long {
        var p = grid.findAll(ROBOT).first()
        val allDirections = directions.flatMap { it }

        for (direction in allDirections) {
            val hasMoved = grid.push(p, direction)

            if (hasMoved) {
                p = p + direction
            }
        }

        println(grid.grid)

        return gps()
    }

    fun partTwo() = 0

    fun gps() = grid.findAll(BOX).sumOf { gps(it) }

    private fun gps(d: Point2d) = 100L * d.y + d.x
}