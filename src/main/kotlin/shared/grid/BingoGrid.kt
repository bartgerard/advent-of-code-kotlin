package shared.grid

import shared.geometry2d.Point2dInt
import shared.sanitize
import shared.toIntegers

data class BingoGrid(
    val grid: IntGrid,
    val marked: MutableList<Point2dInt> = mutableListOf()
) : Grid<Int> {
    constructor(input: String) : this(IntGrid(input.sanitize().lines().map { it.toIntegers().toMutableList() }
        .toMutableList()))

    override fun grid(): List<List<Int>> = grid.grid()

    fun mark(number: Int): Point2dInt? {
        val point = grid.findAll(number).firstOrNull()
        return point?.also { marked.add(it) }
    }

    val markedNumbers get() = marked.map { at(it) }

    val lastMarked get() = at(marked.last())
}