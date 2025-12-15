package shared.grid

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.spatial.Direction

data class PushableGrid(
    val grid: CharGrid,
    val walls: Set<Char>,
    val empty: Set<Char>
) : MutableGrid<Char> by grid {

    constructor(input: String, walls: Set<Char>, empty: Set<Char>) : this(CharGrid(input), walls, empty)

    fun push(p: Point2dInt, d: Direction): Boolean {
        val v = Vector2dInt.forDirection(d)
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