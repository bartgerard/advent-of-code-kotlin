package shared.grid

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Rectangle2dInt

class IntensityGrid(
    private val grid: List<MutableList<Int>>
) {
    constructor(dimension: Dimension2d) : this(List(dimension.height) { MutableList(dimension.width) { 0 } })

    fun execute(rectangle: Rectangle2dInt, instruction: (Int) -> Int) =
        rectangle.points().forEach { execute(it, instruction) }

    private fun execute(point: Point2dInt, instruction: Int.() -> Int) {
        grid[point.y][point.x] = grid[point.y][point.x].instruction()
    }

    fun intensity() = grid.sumOf { row -> row.sumOf { it } }
}