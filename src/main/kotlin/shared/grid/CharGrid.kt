package shared.grid

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2d
import shared.sanitize

data class CharGrid(
    val grid: List<MutableList<Char>>
) : MutableGrid<Char> {
    constructor(
        dimension: Dimension2d,
        defaultValue: Char
    ) : this(MutableList(dimension.height) { MutableList(dimension.width) { defaultValue } })

    constructor(input: String) : this(input.sanitize().lines().map { it.toMutableList() }.toMutableList())

    fun copy() = CharGrid(grid.map { it.toMutableList() }.toMutableList())

    override fun grid(): List<List<Char>> = grid

    override fun set(point: Point2dInt, value: Char) {
        grid[point.y][point.x] = value
    }

    fun frequenciesExcluding(blacklist: Set<Char>): Map<Char, List<Point2dInt>> = points()
        .map { point -> at(point) to point }
        .filter { !blacklist.contains(it.first) }
        .groupBy({ it.first }, { it.second })

    fun countOccurrences(word: String, directions: Collection<Vector2d<Int>>) = findAll(word[0]).sumOf { point ->
        directions.count { vector ->
            word.indices.map { index -> point + vector * index to word[index] }
                .all { (point, character) -> contains(point) && character == at(point) }
        }
    }

    override fun toString() = grid.joinToString("\n") { it.joinToString("") }
}