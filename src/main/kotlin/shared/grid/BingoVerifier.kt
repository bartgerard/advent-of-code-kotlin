package shared.grid

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.spatial.Direction

data class BingoVerifier(
    val winningCombinations: Set<Set<Point2dInt>>
) {
    companion object {
        fun forDimension(dimension: Dimension2d, includeDiagonal: Boolean = false): BingoVerifier =
            BingoVerifier(buildSet {
                addAll(dimension.pointsInDirection(Direction.EAST).map { it.toSet() })
                addAll(dimension.pointsInDirection(Direction.SOUTH).map { it.toSet() })
                if (includeDiagonal) {
                    add(
                        dimension.pointsInDirection(
                            Point2dInt.ZERO, Vector2dInt.forDirection(
                                Direction.SOUTH_EAST
                            )
                        ).toSet()
                    )
                    add(
                        dimension.pointsInDirection(
                            Point2dInt(0, dimension.height - 1),
                            Vector2dInt.forDirection(Direction.NORTH_EAST)
                        )
                            .toSet()
                    )
                }
            })
    }

    fun containsBingo(grid: BingoGrid): Boolean = winningCombinations.any { grid.marked.containsAll(it) }
}