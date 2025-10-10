package aock2022

import shared.CharGrid
import shared.Direction
import shared.Vector2d
import shared.takeOnlyIncreasingBy
import shared.takeVisibleFromHeightBy

data class Year2022Day08(
    private val grid: CharGrid
) {
    constructor(input: String) : this(CharGrid(input))

    fun partOne() = Direction.CARDINAL_DIRECTIONS
        .flatMap { direction ->
            grid.dimension().traverseInDirection(direction)
                .flatMap { path -> path.takeOnlyIncreasingBy { grid.at(it) } }
        }
        .toSet()
        .size

    fun partTwo(): Int {
        val directions = Direction.CARDINAL_DIRECTIONS.map { Vector2d.forDirection(it) }
        val dimension = grid.dimension()
        return dimension.points()
            .maxOf { point ->
                val treeHeight = grid.at(point)
                val result = directions.map { direction ->
                    dimension.traverseInDirection(point, direction)
                        .drop(1)
                        .toList()
                        .takeVisibleFromHeightBy(treeHeight) { grid.at(it) }
                        .count()
                }
                    .fold(1, Int::times)

                result
            }
    }
}