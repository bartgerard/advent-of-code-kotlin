package shared

data class Dimension(
    val width: Int,
    val height: Int
)

data class Rectangle(
    val x: IntRange,
    val y: IntRange
) {
    fun coordinates(): List<Point2d> {
        return x.flatMap { a -> y.map { b -> Point2d(a, b) } }
    }
}

class ToggleGrid(
    private val grid: Array<BooleanArray>
) {
    constructor(
        dimension: Dimension
    ) : this(Array(dimension.height) { BooleanArray(dimension.width) { false } })

    fun execute(rectangle: Rectangle, instruction: (previous: Boolean) -> Boolean) {
        rectangle.coordinates().forEach { execute(it, instruction) }
    }

    private fun execute(point: Point2d, instruction: (previous: Boolean) -> Boolean) {
        grid[point.y][point.x] = instruction(grid[point.y][point.x])
    }

    fun count(): Int = grid.sumOf { row -> row.count { it } }
}

class IntensityGrid(
    private val grid: Array<IntArray>
) {
    constructor(
        dimension: Dimension
    ) : this(Array(dimension.height) { IntArray(dimension.width) { 0 } })

    fun execute(rectangle: Rectangle, instruction: (previous: Int) -> Int) {
        rectangle.coordinates().forEach { execute(it, instruction) }
    }

    private fun execute(point: Point2d, instruction: (previous: Int) -> Int) {
        grid[point.y][point.x] = instruction(grid[point.y][point.x])
    }

    fun intensity(): Int = grid.sumOf { row -> row.sumOf { it } }
}