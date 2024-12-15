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

interface Grid<T> {
    fun rows(): IntRange
    fun columns(): IntRange
    fun points(): Sequence<Point2d>
    fun contains(point: Point2d): Boolean
    fun at(point: Point2d): T
    fun set(point: Point2d, value: T)
    fun setInDirection(point: Point2d, direction: Vector2d, values: List<T>)
    fun findAll(value: T): List<Point2d>
}

data class CharGrid(
    val grid: MutableList<MutableList<Char>>
) : Grid<Char> {
    constructor(input: String) : this(input.sanitize().lines().map { it.toMutableList() }.toMutableList())

    fun copy() = CharGrid(grid.map { it.toMutableList() }.toMutableList())

    override fun rows() = 0 until grid.size
    override fun columns() = 0 until grid[0].size
    override fun points() = rows().asSequence().flatMap { row -> columns().map { column -> Point2d(column, row) } }

    override fun contains(p: Point2d) = p.y in grid.indices && p.x in 0..<grid[p.y].size

    override fun at(p: Point2d) = grid[p.y][p.x]

    override fun set(p: Point2d, value: Char) {
        grid[p.y][p.x] = value
    }

    override fun setInDirection(p: Point2d, d: Vector2d, values: List<Char>) {
        values.forEachIndexed { i, value -> set(p + d * i, value) }
    }

    override fun findAll(c: Char) = grid.flatMapIndexed { row, line ->
        line.indices.filter { line[it] == c }
            .map { column -> Point2d(column, row) }
    }

    fun sequenceInDirection(p: Point2d, d: Vector2d) = generateSequence(0) { it + 1 }
        .map { p + d * it }
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