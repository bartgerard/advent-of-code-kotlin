package shared

import shared.Direction.NORTH_EAST
import shared.Direction.NORTH_WEST
import shared.Direction.SOUTH_EAST
import shared.Direction.SOUTH_WEST

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

    fun execute(rectangle: Rectangle2d, instruction: Boolean.() -> Boolean) =
        rectangle.points().forEach { execute(it, instruction) }

    private fun execute(point: Point2d, instruction: Boolean.() -> Boolean) {
        grid[point.y][point.x] = grid[point.y][point.x].instruction()
    }

    fun count(): Int = grid.sumOf { row -> row.count { it } }
}

class IntensityGrid(
    private val grid: List<MutableList<Int>>
) {
    constructor(dimension: Dimension) : this(List(dimension.height) { MutableList(dimension.width) { 0 } })

    fun execute(rectangle: Rectangle2d, instruction: (Int) -> Int) =
        rectangle.points().forEach { execute(it, instruction) }

    private fun execute(point: Point2d, instruction: Int.() -> Int) {
        grid[point.y][point.x] = grid[point.y][point.x].instruction()
    }

    fun intensity() = grid.sumOf { row -> row.sumOf { it } }
}

interface Grid<T> {
    fun dimension(): Dimension
    fun rowIndices(): IntRange
    fun columnIndices(): IntRange
    fun points(): Sequence<Point2d>
    fun contains(point: Point2d): Boolean
    fun at(row: Int, column: Int): T
    fun at(point: Point2d): T
    fun firstRow(): List<T>
    fun lastRow(): List<T>
    fun columns(): List<List<T>>
    fun set(point: Point2d, value: T)
    fun setInDirection(point: Point2d, direction: Vector2d, values: List<T>)
    fun findAll(value: T): List<Point2d>
    fun values(): Set<T> = points().map { at(it) }.toSet()
}

data class CharGrid(
    val grid: MutableList<MutableList<Char>>
) : Grid<Char> {
    constructor(
        dimension: Dimension,
        defaultValue: Char
    ) : this(MutableList(dimension.height) { MutableList(dimension.width) { defaultValue } })

    constructor(input: String) : this(input.sanitize().lines().map { it.toMutableList() }.toMutableList())

    fun copy() = CharGrid(grid.map { it.toMutableList() }.toMutableList())

    override fun dimension() = Dimension(grid[0].size, grid.size)
    override fun rowIndices() = 0 until grid.size
    override fun columnIndices() = 0 until grid[0].size
    override fun points() = rowIndices().asSequence().flatMap { row -> columnIndices().map { column -> Point2d(column, row) } }

    override fun contains(point: Point2d) = point.y in grid.indices && point.x in 0..<grid[point.y].size

    override fun at(row: Int, column: Int) = grid[row][column]
    override fun at(point: Point2d) = grid[point.y][point.x]

    override fun firstRow() = grid.first()
    override fun lastRow() = grid.last()

    override fun columns() = columnIndices().map { column -> rowIndices().map { row -> at(row, column) } }

    override fun set(point: Point2d, value: Char) {
        grid[point.y][point.x] = value
    }

    override fun setInDirection(point: Point2d, direction: Vector2d, values: List<Char>) {
        values.forEachIndexed { i, value -> set(point + direction * i, value) }
    }

    override fun findAll(value: Char) = grid.flatMapIndexed { row, line ->
        line.indices.filter { line[it] == value }
            .map { column -> Point2d(column, row) }
    }

    fun sequenceInDirection(point: Point2d, direction: Vector2d) = generateSequence(0) { it + 1 }
        .map { point + direction * it }
        .takeWhile { contains(it) }
        .map { at(it) }

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

data class PushableGrid(
    val grid: CharGrid,
    val walls: Set<Char>,
    val empty: Set<Char>
) : Grid<Char> by grid {

    constructor(input: String, walls: Set<Char>, empty: Set<Char>) : this(CharGrid(input), walls, empty)

    fun push(p: Point2d, d: Direction): Boolean {
        val v = Vector2d.forDirection(d)
        val sequence = grid.sequenceInDirection(p, v)
            .takeWhile { !walls.contains(it) }
            .joinToString("")
        val firstEmptySpace = sequence.indexOfFirst { empty.contains(it) }

        if (firstEmptySpace < 0) {
            return false
        }
        val emptySpace = "${sequence[firstEmptySpace]}"
        val newSequence = emptySpace + sequence.replaceFirst(emptySpace, "")

        if (sequence == newSequence) {
            return false
        }

        grid.setInDirection(p, v, newSequence.toList())

        return true
    }

}