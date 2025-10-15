package aock2022

import shared.CharGrid
import shared.Direction
import shared.Vector2d
import shared.at
import shared.product
import shared.takeOnlyIncreasingBy
import shared.takeVisibleFromHeightBy

data class Year2022Day08(
    private val grid: CharGrid
) {
    constructor(input: String) : this(CharGrid(input))

    fun partOne() = Direction.CARDINAL_DIRECTIONS
        .flatMap { direction ->
            grid.dimension().pointsInDirection(direction)
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
                    dimension.pointsInDirection(point, direction)
                        .drop(1)
                        .toList()
                        .takeVisibleFromHeightBy(treeHeight) { grid.at(it) }
                        .count()
                }
                    .product()

                result
            }
    }
}