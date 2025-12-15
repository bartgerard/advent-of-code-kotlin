package shared.geometry2d

import shared.spatial.Direction
import shared.toIntegers

data class Dimension2d(
    val width: Int,
    val height: Int
) {
    companion object {
        fun parse(value: String) = value.toIntegers()
            .let { (width, height) -> Dimension2d(width, height) }
    }

    fun area() = width * height

    fun rowIndices() = 0 until height
    fun columnIndices() = 0 until width

    fun contains(p: Point2d<Int>): Boolean = p.x in 0..<width && p.y in 0..<height

    fun points() = (0..<height).asSequence().flatMap { row -> (0..<width).map { column -> Point2dInt(column, row) } }

    fun quadrants() = buildMap {
        val midX = width / 2
        val midY = height / 2

        put(Direction.NORTH_WEST, Rectangle2dInt(0..<midX, 0..<midY))
        put(Direction.NORTH_EAST, Rectangle2dInt((midX + 1)..<width, 0..<midY))
        put(Direction.SOUTH_EAST, Rectangle2dInt((midX + 1)..<width, (midY + 1)..<height))
        put(Direction.SOUTH_WEST, Rectangle2dInt(0..<midX, (midY + 1)..<height))
    }

    fun display(points: Collection<Point2dInt>) = List(height) { y ->
        List(width) { x ->
            points.contains(
                Point2dInt(
                    x,
                    y
                )
            )
        }
    }
        .joinToString("\n", "\n") { row -> row.joinToString("") { if (it) "#" else " " } }

    fun outerPointsInDirection(direction: Direction) = when (direction) {
        Direction.NORTH -> (0..<width).map { Point2dInt(it, 0) }
        Direction.EAST -> (0..<height).map { Point2dInt(width - 1, it) }
        Direction.SOUTH -> (0..<width).map { Point2dInt(it, height - 1) }
        Direction.WEST -> (0..<height).map { Point2dInt(0, it) }
        Direction.NORTH_EAST -> listOf(Point2dInt(width - 1, 0))
        Direction.SOUTH_EAST -> listOf(Point2dInt(width - 1, height - 1))
        Direction.SOUTH_WEST -> listOf(Point2dInt(0, height - 1))
        Direction.NORTH_WEST -> listOf(Point2dInt(0, 0))
    }

    fun allOuterPointsInDirection(direction: Direction): List<Point2dInt> = when (direction) {
        Direction.NORTH_EAST -> (0..<width).map { Point2dInt(it, 0) } + (1..<height).map { Point2dInt(width - 1, it) }
        Direction.SOUTH_EAST -> (0..<width).map { Point2dInt(it, height - 1) } + (0..<height - 1).reversed()
            .map { Point2dInt(width - 1, it) }

        Direction.SOUTH_WEST -> (0..<height).map { Point2dInt(0, it) } + (1..<width).map { Point2dInt(it, height - 1) }
        Direction.NORTH_WEST -> (1..<height).reversed().map { Point2dInt(0, it) } + (0..<width).map {
            Point2dInt(
                it,
                0
            )
        }

        else -> outerPointsInDirection(direction)
    }

    fun pointsInDirection(point: Point2dInt, direction: Vector2dInt) = generateSequence(0) { it + 1 }
        .map { point + direction * it }
        .takeWhile { contains(it) }

    fun pointsInDirection(direction: Direction): List<List<Point2dInt>> {
        val outerPoints = allOuterPointsInDirection(direction.inverse())
        val vector = Vector2dInt.forDirection(direction)

        return outerPoints.map { pointsInDirection(it, vector).toList() }
    }

    operator fun times(factor: Int) = Dimension2d(width * factor, height * factor)

    fun bottomRight(): Point2dInt = Point2dInt(width - 1, height - 1)
}