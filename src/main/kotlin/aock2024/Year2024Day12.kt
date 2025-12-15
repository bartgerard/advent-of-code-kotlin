package aock2024

import shared.geometry2d.Area2dInt
import shared.grid.CharGrid
import shared.sanitize

data class Year2024Day12(
    private val grid: CharGrid
) {
    constructor(input: String) : this(CharGrid(input.sanitize()))

    fun partOne() = grid.dimension().points()
        .groupBy { grid.at(it) }
        .values.flatMap { Area2dInt.areas(it) }
        .sumOf { it.area * it.perimeter }

    fun partTwo() = grid.dimension().points()
        .groupBy { grid.at(it) }
        .values.flatMap { Area2dInt.areas(it) }
        .sumOf { it.area * it.sides().count() }

}