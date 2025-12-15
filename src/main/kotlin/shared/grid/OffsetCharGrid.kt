package shared.grid

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt

data class OffsetCharGrid(
    val grid: CharGrid,
    val offset: Vector2dInt
) : MutableGrid<Char> {
    override fun grid(): List<List<Char>> = grid.grid()

    override fun contains(point: Point2dInt): Boolean = grid.contains(point - offset)
    override fun at(row: Int, column: Int): Char = grid.at(row - offset.y, column - offset.x)

    override fun set(point: Point2dInt, value: Char) {
        grid.set(point - offset, value)
    }

    override fun findAll(value: Char): List<Point2dInt> = grid.findAll(value).map { it + offset }

    override fun points(): Sequence<Point2dInt> = grid.points().map { it - offset }
    override fun values(): Set<Char> = grid.values()

    override fun toString(): String = grid.toString()
}