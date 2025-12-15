package aock2019

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.CharGrid

data class Year2019Day10(
    private val input: CharGrid,
    private val asteroids: List<Point2dInt>
) {
    constructor(input: String) : this(CharGrid(input))
    constructor(grid: CharGrid) : this(grid, grid.findAll('#'))

    fun partOne() = asteroids.maxOf { countVisibleAsteroidsFrom(it) }

    fun partTwo() = vaporizationOrder(getStationLocation())
        .drop(199)
        .first()
        .let { it.x * 100 + it.y }

    private fun getStationLocation(): Point2dInt = asteroids.maxBy { countVisibleAsteroidsFrom(it) }

    fun countVisibleAsteroidsFrom(point: Point2dInt): Int = asteroidsByAngle(point).size

    private fun asteroidsByAngle(point: Point2dInt): Map<Double, List<Point2dInt>> = asteroids
        .filter { it != point }
        .groupBy {
            with(it - point) {
                Vector2dInt.NORTH.signedAnleTo(this).toDegrees360()
                //side() to slope()
            }
        }

    private fun vaporizationOrder(point: Point2dInt): Sequence<Point2dInt> = sequence {
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