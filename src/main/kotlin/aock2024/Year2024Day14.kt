package aock2024

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Rectangle2dInt
import shared.geometry2d.Vector2dInt
import shared.product
import shared.sanitize
import shared.toIntegers

data class Year2024Day14(
    private val dimension: Dimension2d,
    private val quadrants: List<Rectangle2dInt>,
    private val robots: List<Robot>
) {
    companion object {
        val CHRISTMAS_TREE_DETECTOR = "#".repeat(10)
    }

    constructor(width: Int, height: Int, input: String) : this(
        Dimension2d(width, height),
        input.sanitize().lines()
            .map { it.toIntegers() }
            .map { Robot(Point2dInt(it[0], it[1]), Vector2dInt(it[2], it[3])) })

    constructor(dimension: Dimension2d, robots: List<Robot>) : this(
        dimension,
        dimension.quadrants().values.toList(),
        robots
    )

    fun partOne(t: Long): Int = robotsByQuadrantAfter(t).filter { it.key >= 0 }
        .map { it.value.size }
        .product()

    fun partTwo(): Long = (0L..10_000L)
        .firstOrNull { t -> positionsAfter(t).let { dimension.display(it) }.contains(CHRISTMAS_TREE_DETECTOR) }
        ?.also { positionsAfter(it).let { dimension.display(it) }.run { println(this) } }
        ?: -1L

    private fun positionsAfter(t: Long): List<Point2dInt> = robots.map { it.after(t, dimension) }

    private fun robotsByQuadrant(points: List<Point2dInt>): Map<Int, List<Point2dInt>> =
        points.groupBy({ p -> (quadrants.firstOrNull { it.contains(p) }?.let { quadrants.indexOf(it) } ?: -1) }, { it })

    private fun robotsByQuadrantAfter(t: Long) = robotsByQuadrant(positionsAfter(t))

}

data class Robot(
    val p: Point2dInt,
    val v: Vector2dInt
) {
    fun after(t: Long, d: Dimension2d) = Point2dInt(
        (p.x + v.x * t).mod(d.width),
        (p.y + v.y * t).mod(d.height)
    )
}