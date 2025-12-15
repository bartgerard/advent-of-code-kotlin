package aock2022

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Rectangle2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.CharGrid
import shared.grid.OffsetCharGrid
import shared.grid.fill
import shared.sanitize
import shared.toIntegers
import kotlin.math.max
import kotlin.math.min

data class Year2022Day14(
    private val lines: List<Rectangle2dInt>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .flatMap { line ->
                line.toIntegers().chunked(2)
                    .map { Point2dInt(it) }
                    .zipWithNext { p1, p2 -> Rectangle2dInt.of(p1, p2) }
            }
    )

    fun partOne() = RegolithReservoir.of(lines).countUnitsOfSand()

    fun partTwo() = RegolithReservoir.of(lines, true).countUnitsOfSandUntilSourceBlocked()
}

data class RegolithReservoir(
    val grid: OffsetCharGrid
) {
    companion object {
        const val AIR = '.'
        const val ROCK = '#'
        const val SAND = 'o'
        const val SOURCE = '+'

        val SUPPORTING_FOUNDATION = setOf(ROCK, SAND)

        val SAND_SOURCE = Point2dInt(500, 0)
        val GRAVITY = Vector2dInt.SOUTH

        val FALL_DIRECTIONS = listOf(
            Vector2dInt.SOUTH_WEST,
            Vector2dInt.SOUTH_EAST,
            Vector2dInt.ZERO
        )
        val BELOW_DIRECTIONS = listOf(
            Vector2dInt.SOUTH_WEST,
            Vector2dInt.SOUTH,
            Vector2dInt.SOUTH_EAST
        )

        fun of(
            lines: List<Rectangle2dInt>,
            simulateInfiniteFloor: Boolean = false
        ): RegolithReservoir {
            val minX = lines.minOf { it.x.min() }
            val maxX = lines.maxOf { it.x.max() }
            val maxY = lines.maxOf { it.y.max() }

            if (simulateInfiniteFloor) {
                val floorY = maxY + 2
                val floorMinX = min(
                    SAND_SOURCE.x - floorY,
                    lines.minOf { it.x.min() - floorY + it.y.min() }
                )
                val floorMaxX = max(
                    SAND_SOURCE.x + floorY,
                    lines.maxOf { it.x.max() + floorY - it.y.min() }
                )
                val simulatedInfiniteFloor = Rectangle2dInt.of(
                    Point2dInt(floorMinX, floorY),
                    Point2dInt(floorMaxX, floorY)
                )
                return of(lines + simulatedInfiniteFloor)
            }

            val offset = Vector2dInt(minX, 0)

            val dimension = Dimension2d(maxX - minX + 1, maxY + 1)
            val grid = CharGrid(dimension, AIR)
            val offsetCharGrid = OffsetCharGrid(grid, offset)

            offsetCharGrid.fill(lines, ROCK)
            offsetCharGrid.set(SAND_SOURCE, SOURCE)

            return RegolithReservoir(offsetCharGrid)
        }
    }

    fun countUnitsOfSand() = countUnitsOfSandWhile { true }

    fun countUnitsOfSandUntilSourceBlocked() =
        countUnitsOfSandWhile { !isSandSourceBlocked() } + 1 /* include source itself */

    fun countUnitsOfSandWhile(endCondition: (Point2dInt) -> Boolean): Int {
        val sediments = generateSequence {
            dropSand(SAND_SOURCE)
                ?.also { grid.set(it, SAND) }
        }
            .takeWhile(endCondition)
            .toList()

        //println(grid)
        //println()

        return sediments.count()
    }

    fun dropSand(source: Point2dInt): Point2dInt? = (source..GRAVITY)
        .takeWhile { grid.contains(it) && grid.at(it) !in SUPPORTING_FOUNDATION }
        .lastOrNull()
        ?.let { findRestingSpot(it) }
        ?.let {
            if (isFinalRestingSpot(it)) {
                it
            } else {
                dropSand(it)
            }
        }

    fun findRestingSpot(point: Point2dInt) = FALL_DIRECTIONS
        .map { point + it }
        .takeWhile { grid.contains(it) }
        .firstOrNull { grid.at(it) !in SUPPORTING_FOUNDATION }

    fun isFinalRestingSpot(point: Point2dInt) = BELOW_DIRECTIONS
        .map { point + it }
        .all { grid.contains(it) && grid.at(it) in SUPPORTING_FOUNDATION }

    fun isSandSourceBlocked() = grid.at(SAND_SOURCE) != SOURCE
}