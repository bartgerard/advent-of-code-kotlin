package shared

data class Dimension(
    val width: Int,
    val height: Int
) {
    fun contains(p: Point2d): Boolean = p.x in 0..<width && p.y in 0..<height
}

data class Rectangle(
    val x: IntRange,
    val y: IntRange
) {
    fun coordinates(): List<Point2d> {
        return x.flatMap { a -> y.map { b -> Point2d(a, b) } }
    }
}

class ToggleGrid(
    private val grid: List<BooleanArray>
) {
    constructor(
        dimension: Dimension
    ) : this(List(dimension.height) { BooleanArray(dimension.width) { false } })

    fun execute(rectangle: Rectangle, instruction: (previous: Boolean) -> Boolean) {
        rectangle.coordinates().forEach { execute(it, instruction) }
    }

    private fun execute(point: Point2d, instruction: (previous: Boolean) -> Boolean) {
        grid[point.y][point.x] = instruction(grid[point.y][point.x])
    }

    fun count(): Int = grid.sumOf { row -> row.count { it } }
}

class IntensityGrid(
    private val grid: List<IntArray>
) {
    constructor(
        dimension: Dimension
    ) : this(List(dimension.height) { IntArray(dimension.width) { 0 } })

    fun execute(rectangle: Rectangle, instruction: (previous: Int) -> Int) {
        rectangle.coordinates().forEach { execute(it, instruction) }
    }

    private fun execute(point: Point2d, instruction: (previous: Int) -> Int) {
        grid[point.y][point.x] = instruction(grid[point.y][point.x])
    }

    fun intensity(): Int = grid.sumOf { row -> row.sumOf { it } }
}

data class MutableGrid(
    val grid: MutableList<MutableList<Char>>
) {

    fun copy(): MutableGrid = MutableGrid(grid.map { it.toMutableList() }.toMutableList())

    constructor(input: String) : this(
        input.sanitize()
            .lines()
            .map { it.toCharArray().toMutableList().toList().toMutableList() }
            .toMutableList()
    )

    fun findAll(c: Char) = grid.flatMapIndexed { row, line ->
        line.indices.filter { line[it] == c }
            .map { column -> Point2d(column, row) }
    }

    fun contains(p: Point2d): Boolean = p.y in grid.indices && p.x in 0..<grid[p.y].size

    fun at(p: Point2d): Char = grid[p.y][p.x]

    fun set(p: Point2d, value: Char) = grid[p.y].set(p.x, value)

    override fun toString(): String = grid.joinToString("\n") { it.joinToString("") }

}