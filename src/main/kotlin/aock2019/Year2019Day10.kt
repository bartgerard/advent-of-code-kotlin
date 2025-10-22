package aock2019

import shared.CharGrid
import shared.Point2d
import shared.Vector2d

data class Year2019Day10(
    private val input: CharGrid,
    private val asteroids: List<Point2d>
) {
    constructor(input: String) : this(CharGrid(input))
    constructor(grid: CharGrid) : this(grid, grid.findAll('#'))

    fun partOne() = asteroids.maxOf { countVisibleAsteroidsFrom(it) }

    fun partTwo() = vaporizationOrder(getStationLocation())
        .drop(199)
        .first()
        .let { it.x * 100 + it.y }

    private fun getStationLocation(): Point2d = asteroids.maxBy { countVisibleAsteroidsFrom(it) }

    fun countVisibleAsteroidsFrom(point: Point2d): Int = asteroidsByAngle(point).size

    private fun asteroidsByAngle(point: Point2d): Map<Double, List<Point2d>> = asteroids
        .filter { it != point }
        .groupBy {
            with(it - point) {
                Vector2d.NORTH.signedAnleTo(this).toDegrees360()
                //side() to slope()
            }
        }

    private fun vaporizationOrder(point: Point2d): Sequence<Point2d> = sequence {
        val asteroidsByAngle = asteroidsByAngle(point)
        val angles = asteroidsByAngle.keys.sorted()
        val maxAsteroidsForAngle = asteroidsByAngle.values.maxOf { it.size }

        (0..maxAsteroidsForAngle).forEach { layer ->
            angles.forEach { angle ->
                val asteroids = asteroidsByAngle[angle]!!

                if (layer < asteroids.size) {
                    yield(asteroids[layer])
                }
            }
        }
    }
}