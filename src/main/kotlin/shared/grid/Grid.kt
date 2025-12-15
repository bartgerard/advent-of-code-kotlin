package shared.grid

import shared.Predicate
import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.spatial.Direction

interface Grid<T> {
    fun grid(): List<List<T>>
    fun dimension(): Dimension2d = Dimension2d(grid()[0].size, grid().size)

    fun contains(point: Point2dInt): Boolean = dimension().contains(point)

    fun at(row: Int, column: Int): T = grid()[row][column]
    fun at(point: Point2dInt): T = at(point.y, point.x)

    fun firstRow(): List<T> = grid().first()
    fun lastRow(): List<T> = grid().last()

    fun findAll(predicate: Predicate<T>): List<Point2dInt> = grid().flatMapIndexed { row, line ->
        line.indices.filter { predicate.invoke(line[it]) }
            .map { column -> Point2dInt(column, row) }
    }

    fun findAll(value: T): List<Point2dInt> = findAll { it == value }

    fun points(): Sequence<Point2dInt> = dimension().points()
    fun values(): Set<T> = points().map { at(it) }.toSet()

    fun matches(other: Grid<T>, offset: Point2dInt): Boolean = other.points()
        .all { point -> contains(offset + point) && at(offset + point) == other.at(point) }
}

fun <T> Grid<T>.rowIndices(): IntRange = dimension().rowIndices()
fun <T> Grid<T>.columnIndices(): IntRange = dimension().columnIndices()
fun <T> Grid<T>.columns(): List<List<T>> = columnIndices().map { column -> rowIndices().map { row -> at(row, column) } }

fun <T> Grid<T>.valuesInDirection(point: Point2dInt, direction: Vector2dInt): Sequence<T> = dimension()
    .pointsInDirection(point, direction)
    .map { at(it) }

fun <T> Grid<T>.valuesInDirection(direction: Direction): List<List<T>> = dimension()
    .pointsInDirection(direction)
    .map { points -> points.map { at(it) } }

