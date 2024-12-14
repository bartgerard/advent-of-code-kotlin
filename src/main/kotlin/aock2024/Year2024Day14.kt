package aock2024

import shared.*

data class Year2024Day14(
    private val dimension: Dimension,
    private val quadrants: List<Rectangle2d>,
    private val robots: List<Robot>
) {
    companion object {
        val CHRISTMAS_TREE_DETECTOR = "#".repeat(10)
    }

    constructor(width: Int, height: Int, input: String) : this(
        Dimension(width, height),
        input.sanitize().lines()
            .map { it.toIntegers() }
            .map { Robot(Point2d(it[0], it[1]), Vector2d(it[2], it[3])) })

    constructor(dimension: Dimension, robots: List<Robot>) : this(dimension, dimension.quadrants().values.toList(), robots)

    fun partOne(t: Long): Int = robotsByQuadrantAfter(t).filter { it.key >= 0 }
        .map { it.value.size }
        .fold(1, Int::times)

    fun partTwo(): Long = (0L..10_000L).asSequence()
        .firstOrNull { t -> positionsAfter(t).let { dimension.display(it) }.contains(CHRISTMAS_TREE_DETECTOR) }
        ?.also { positionsAfter(it).let { dimension.display(it) }.run { println(this) } }
        ?: -1L

    private fun positionsAfter(t: Long): List<Point2d> = robots.map { it.after(t, dimension) }

    private fun robotsByQuadrant(points: List<Point2d>): Map<Int, List<Point2d>> = points.groupBy({ p -> (quadrants.firstOrNull { it.contains(p) }?.let { quadrants.indexOf(it) } ?: -1) }, { it })

    private fun robotsByQuadrantAfter(t: Long) = robotsByQuadrant(positionsAfter(t))

}

data class Robot(
    val p: Point2d,
    val v: Vector2d
) {
    fun after(t: Long, d: Dimension) = Point2d(
        (p.x + v.x * t).mod(d.width).toInt(),
        (p.y + v.y * t).mod(d.height).toInt()
    )
}