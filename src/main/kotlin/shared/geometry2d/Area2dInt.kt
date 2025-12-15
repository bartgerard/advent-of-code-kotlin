package shared.geometry2d

import shared.spatial.Direction

data class Area2dInt(
    val points: Set<Point2dInt>
) {
    companion object {
        fun areas(points: List<Point2dInt>): List<Area2dInt> {
            val remaining = points.toMutableList()
            val areas = mutableSetOf<List<Point2dInt>>()

            while (remaining.isNotEmpty()) {
                val region2 = remaining.first()
                    .allDirectAndIndirectNeighbours(Vector2dInt.ORTHOGONAL_ADJACENT) {
                        remaining.contains(it) && remaining.remove(
                            it
                        )
                    }
                remaining.removeAll(region2)
                areas += region2
            }

            return areas.map { Area2dInt(it.toSet()) }
        }
    }

    val area get() = points.size
    val perimeter get() = sides().sum()

    fun sides(): List<Int> {
        val borders: Map<Point2dInt, MutableSet<Direction>> = points.associateWith { point ->
            Direction.ORTHOGONAL.filter { !points.contains(point + it) }.toMutableSet()
        }

        return points.flatMap { point ->
            borders[point]!!.toList().map { direction ->
                val visited = point.allDirectAndIndirectNeighbours(Vector2dInt.ORTHOGONAL_ADJACENT) {
                    points.contains(it) && borders[it]!!.contains(direction)
                }
                visited.forEach { borders[it]!!.remove(direction) }
                visited.size
            }
        }
    }

}