package aock2021

import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.IntGrid

data class Year2021Day11(
    private val grid: IntGrid
) {
    constructor(input: String) : this(IntGrid(input))

    fun partOne() = (0 until 100)
        .map { _ -> step() }
        .sumOf { it.size }

    fun partTwo() = generateSequence(1) { it + 1 }
        .onEach { step() }
        .first { grid.findAll { it > 0 }.isEmpty() }

    fun step(): Set<Point2dInt> {
        grid + 1
        val flashed = mutableSetOf<Point2dInt>()
        val flashes = grid.findAll { it > 9 }.toMutableList()

        while (flashes.isNotEmpty()) {
            val flash = flashes.removeFirst()
            flashed += flash

            flash.neighbours(Vector2dInt.SURROUNDING)
                .filter { grid.contains(it) }
                .forEach { neighbour ->
                    val newValue = grid.at(neighbour) + 1

                    grid.set(neighbour, newValue)

                    if (newValue == 10) {
                        flashes += neighbour
                    }
                }
        }

        grid.findAll { it > 9 }.forEach { grid.set(it, 0) }

        return flashed
    }
}