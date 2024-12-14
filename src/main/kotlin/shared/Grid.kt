package shared

import shared.Direction.*

data class Dimension(
    val width: Int,
    val height: Int
) {
    fun contains(p: Point2d): Boolean = p.x in 0..<width && p.y in 0..<height

    fun quadrants() = buildMap {
        val midX = width / 2
        val midY = height / 2

        put(NORTH_WEST, Rectangle2d(0..<midX, 0..<midY))
        put(NORTH_EAST, Rectangle2d((midX + 1)..<width, 0..<midY))
        put(SOUTH_EAST, Rectangle2d((midX + 1)..<width, (midY + 1)..<height))
        put(SOUTH_WEST, Rectangle2d(0..<midX, (midY + 1)..<height))
    }

    fun display(points: Collection<Point2d>) = List(height) { y -> List(width) { x -> points.contains(Point2d(x, y)) } }
        .joinToString("\n", "\n") { row -> row.joinToString("") { if (it) "#" else " " } }
}

class ToggleGrid(
    private val grid: List<MutableList<Boolean>>
) {
    constructor(dimension: Dimension) : this(List(dimension.height) { MutableList(dimension.width) { false } })

    fun execute(rectangle: Rectangle2d, instruction: Boolean.() -> Boolean) = rectangle.points().forEach { execute(it, instruction) }

    private fun execute(point: Point2d, instruction: Boolean.() -> Boolean) {
        grid[point.y][point.x] = grid[point.y][point.x].instruction()
    }

    fun count(): Int = grid.sumOf { row -> row.count { it } }
}

class IntensityGrid(
    private val grid: List<MutableList<Int>>
) {
    constructor(dimension: Dimension) : this(List(dimension.height) { MutableList(dimension.width) { 0 } })

    fun execute(rectangle: Rectangle2d, instruction: (Int) -> Int) = rectangle.points().forEach { execute(it, instruction) }

    private fun execute(point: Point2d, instruction: Int.() -> Int) {
        grid[point.y][point.x] = grid[point.y][point.x].instruction()
    }

    fun intensity() = grid.sumOf { row -> row.sumOf { it } }
}

data class CharGrid(
    val grid: MutableList<MutableList<Char>>
) {
    constructor(input: String) : this(input.sanitize().lines().map { it.toMutableList() }.toMutableList())

    fun rows() = 0 until grid.size
    fun columns() = 0 until grid[0].size

    fun copy() = CharGrid(grid.map { it.toMutableList() }.toMutableList())

    fun findAll(c: Char) = grid.flatMapIndexed { row, line ->
        line.indices.filter { line[it] == c }
            .map { column -> Point2d(column, row) }
    }

    fun contains(p: Point2d) = p.y in grid.indices && p.x in 0..<grid[p.y].size

    fun at(p: Point2d) = grid[p.y][p.x]

    fun set(p: Point2d, value: Char) = grid[p.y].set(p.x, value)

    fun points() = rows().asSequence().flatMap { row -> columns().map { column -> Point2d(column, row) } }

    fun frequenciesExcluding(blacklist: Set<Char>): Map<Char, List<Point2d>> = points()
        .map { point -> at(point) to point }
        .filter { !blacklist.contains(it.first) }
        .groupBy({ it.first }, { it.second })

    fun countOccurrences(word: String, directions: Collection<Vector2d>) = findAll(word[0]).sumOf { point ->
        directions.count { vector ->
            word.indices.map { index -> point + vector * index to word[index] }
                .all { (point, character) -> contains(point) && character == at(point) }
        }
    }

    override fun toString() = grid.joinToString("\n") { it.joinToString("") }

}