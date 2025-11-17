package shared

import shared.Direction.EAST
import shared.Direction.NORTH
import shared.Direction.NORTH_EAST
import shared.Direction.NORTH_WEST
import shared.Direction.SOUTH
import shared.Direction.SOUTH_EAST
import shared.Direction.SOUTH_WEST
import shared.Direction.WEST

data class Dimension(
    val width: Int,
    val height: Int
) {
    fun rowIndices() = 0 until height
    fun columnIndices() = 0 until width

    fun contains(p: Point2d): Boolean = p.x in 0..<width && p.y in 0..<height

    fun points() = (0..<height).asSequence().flatMap { row -> (0..<width).map { column -> Point2d(column, row) } }

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

    fun outerPointsInDirection(direction: Direction) = when (direction) {
        NORTH -> (0..<width).map { Point2d(it, 0) }
        EAST -> (0..<height).map { Point2d(width - 1, it) }
        SOUTH -> (0..<width).map { Point2d(it, height - 1) }
        WEST -> (0..<height).map { Point2d(0, it) }
        NORTH_EAST -> listOf(Point2d(width - 1, 0))
        SOUTH_EAST -> listOf(Point2d(width - 1, height - 1))
        SOUTH_WEST -> listOf(Point2d(0, height - 1))
        NORTH_WEST -> listOf(Point2d(0, 0))
    }

    fun allOuterPointsInDirection(direction: Direction): List<Point2d> = when (direction) {
        NORTH_EAST -> (0..<width).map { Point2d(it, 0) } + (1..<height).map { Point2d(width - 1, it) }
        SOUTH_EAST -> (0..<width).map { Point2d(it, height - 1) } + (0..<height - 1).reversed().map { Point2d(width - 1, it) }
        SOUTH_WEST -> (0..<height).map { Point2d(0, it) } + (1..<width).map { Point2d(it, height - 1) }
        NORTH_WEST -> (1..<height).reversed().map { Point2d(0, it) } + (0..<width).map { Point2d(it, 0) }
        else -> outerPointsInDirection(direction)
    }

    fun pointsInDirection(point: Point2d, direction: Vector2d) = generateSequence(0) { it + 1 }
        .map { point + direction * it }
        .takeWhile { contains(it) }

    fun pointsInDirection(direction: Direction): List<List<Point2d>> {
        val outerPoints = allOuterPointsInDirection(direction.inverse())
        val vector = Vector2d.forDirection(direction)

        return outerPoints.map { pointsInDirection(it, vector).toList() }
    }

    operator fun times(factor: Int) = Dimension(width * factor, height * factor)

    fun bottomRight(): Point2d = Point2d(width - 1, height - 1)
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
    fun grid(): List<List<T>>
    fun dimension(): Dimension = Dimension(grid()[0].size, grid().size)

    fun contains(point: Point2d): Boolean = dimension().contains(point)

    fun at(row: Int, column: Int): T = grid()[row][column]
    fun at(point: Point2d): T = at(point.y, point.x)

    fun firstRow(): List<T> = grid().first()
    fun lastRow(): List<T> = grid().last()

    fun findAll(predicate: Predicate<T>): List<Point2d> = grid().flatMapIndexed { row, line ->
        line.indices.filter { predicate.invoke(line[it]) }
            .map { column -> Point2d(column, row) }
    }

    fun findAll(value: T): List<Point2d> = findAll { it == value }

    fun points(): Sequence<Point2d> = dimension().points()
    fun values(): Set<T> = points().map { at(it) }.toSet()
}

interface MutableGrid<T> : Grid<T> {

    fun set(point: Point2d, value: T)
}

data class IntGrid(
    val grid: List<MutableList<Int>>
) : MutableGrid<Int> {

    constructor(input: String) : this(
        input.sanitize().lines()
            .map { line -> line.asSequence().map { it.digitToInt() }.toMutableList() }
            .toMutableList()
    )

    override fun grid(): List<List<Int>> = grid

    override fun set(point: Point2d, value: Int) {
        grid[point.y][point.x] = value
    }

    operator fun plus(increment: Int) = points().forEach { point ->
        grid[point.y][point.x] += increment
    }

    override fun toString() = grid.joinToString("\n") { it.joinToString("\t") }
}

data class CharGrid(
    val grid: List<MutableList<Char>>
) : MutableGrid<Char> {
    constructor(
        dimension: Dimension,
        defaultValue: Char
    ) : this(MutableList(dimension.height) { MutableList(dimension.width) { defaultValue } })

    constructor(input: String) : this(input.sanitize().lines().map { it.toMutableList() }.toMutableList())

    fun copy() = CharGrid(grid.map { it.toMutableList() }.toMutableList())

    override fun grid(): List<List<Char>> = grid

    override fun set(point: Point2d, value: Char) {
        grid[point.y][point.x] = value
    }

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

data class OffsetCharGrid(
    val grid: CharGrid,
    val offset: Vector2d
) : MutableGrid<Char> {
    override fun grid(): List<List<Char>> = grid.grid()

    override fun contains(point: Point2d): Boolean = grid.contains(point - offset)
    override fun at(row: Int, column: Int): Char = grid.at(row - offset.y, column - offset.x)

    override fun set(point: Point2d, value: Char) {
        grid.set(point - offset, value)
    }

    override fun findAll(value: Char): List<Point2d> = grid.findAll(value).map { it + offset }

    override fun points(): Sequence<Point2d> = grid.points().map { it - offset }
    override fun values(): Set<Char> = grid.values()

    override fun toString(): String = grid.toString()
}

data class BingoGrid(
    val grid: IntGrid,
    val marked: MutableList<Point2d> = mutableListOf()
) : Grid<Int> {
    constructor(input: String) : this(IntGrid(input.sanitize().lines().map { it.toIntegers().toMutableList() }.toMutableList()))

    override fun grid(): List<List<Int>> = grid.grid()

    fun mark(number: Int): Point2d? {
        val point = grid.findAll(number).firstOrNull()
        return point?.also { marked.add(it) }
    }

    val markedNumbers get() = marked.map { at(it) }

    val lastMarked get() = at(marked.last())
}

data class BingoVerifier(
    val winningCombinations: Set<Set<Point2d>>
) {
    companion object {
        fun forDimension(dimension: Dimension, includeDiagonal: Boolean = false): BingoVerifier = BingoVerifier(buildSet {
            addAll(dimension.pointsInDirection(EAST).map { it.toSet() })
            addAll(dimension.pointsInDirection(SOUTH).map { it.toSet() })
            if (includeDiagonal) {
                add(dimension.pointsInDirection(Point2d.ZERO, Vector2d.forDirection(SOUTH_EAST)).toSet())
                add(dimension.pointsInDirection(Point2d(0, dimension.height - 1), Vector2d.forDirection(NORTH_EAST)).toSet())
            }
        })
    }

    fun containsBingo(grid: BingoGrid): Boolean = winningCombinations.any { grid.marked.containsAll(it) }
}

data class PushableGrid(
    val grid: CharGrid,
    val walls: Set<Char>,
    val empty: Set<Char>
) : MutableGrid<Char> by grid {

    constructor(input: String, walls: Set<Char>, empty: Set<Char>) : this(CharGrid(input), walls, empty)

    fun push(p: Point2d, d: Direction): Boolean {
        val v = Vector2d.forDirection(d)
        val sequence = grid.valuesInDirection(p, v)
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

fun <T> Grid<T>.rowIndices(): IntRange = dimension().rowIndices()
fun <T> Grid<T>.columnIndices(): IntRange = dimension().columnIndices()
fun <T> Grid<T>.columns(): List<List<T>> = columnIndices().map { column -> rowIndices().map { row -> at(row, column) } }

fun <T> Grid<T>.valuesInDirection(point: Point2d, direction: Vector2d): Sequence<T> = dimension()
    .pointsInDirection(point, direction)
    .map { at(it) }

fun <T> Grid<T>.valuesInDirection(direction: Direction): List<List<T>> = dimension()
    .pointsInDirection(direction)
    .map { points -> points.map { at(it) } }

fun <T> MutableGrid<T>.setInDirection(point: Point2d, direction: Vector2d, values: List<T>) {
    values.forEachIndexed { i, value -> set(point + direction * i, value) }
}

fun <T> MutableGrid<T>.fill(rectangle: Rectangle2d, value: T) {
    rectangle.points().forEach { set(it, value) }
}

fun <T> MutableGrid<T>.fill(rectangles: Collection<Rectangle2d>, value: T) {
    rectangles.forEach { fill(it, value) }
}

data class DifferenceGrid(
    val grid: List<MutableList<Long>>
) : Grid<Long> {
    constructor(size: Int) : this(MutableList(size) { MutableList(size) { 0L } })

    override fun grid(): List<List<Long>> = grid
}