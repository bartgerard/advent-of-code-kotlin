package shared.grid

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Rectangle2dInt

class ToggleGrid(
    private val grid: List<MutableList<Boolean>>
) {
    constructor(dimension: Dimension2d) : this(List(dimension.height) { MutableList(dimension.width) { false } })

    fun execute(rectangle: Rectangle2dInt, instruction: Boolean.() -> Boolean) =
        rectangle.points().forEach { execute(it, instruction) }

    private fun execute(point: Point2dInt, instruction: Boolean.() -> Boolean) {
        grid[point.y][point.x] = grid[point.y][point.x].instruction()
    }

    fun count(): Int = grid.sumOf { row -> row.count { it } }
}