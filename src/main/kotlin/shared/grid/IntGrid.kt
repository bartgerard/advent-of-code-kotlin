package shared.grid

import shared.geometry2d.Point2dInt
import shared.sanitize

data class IntGrid(
    val grid: List<MutableList<Int>>
) : MutableGrid<Int> {

    constructor(input: String) : this(
        input.sanitize().lines()
            .map { line -> line.asSequence().map { it.digitToInt() }.toMutableList() }
            .toMutableList()
    )

    override fun grid(): List<List<Int>> = grid

    override fun set(point: Point2dInt, value: Int) {
        grid[point.y][point.x] = value
    }

    operator fun plus(increment: Int) = points().forEach { point ->
        grid[point.y][point.x] += increment
    }

    override fun toString() = grid.joinToString("\n") { it.joinToString("\t") }
}