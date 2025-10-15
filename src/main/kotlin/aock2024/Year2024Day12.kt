package aock2024

import shared.Area2d
import shared.CharGrid
import shared.at
import shared.sanitize

data class Year2024Day12(
    private val grid: CharGrid
) {
    constructor(input: String) : this(CharGrid(input.sanitize()))

    fun partOne() = grid.dimension().points()
        .groupBy { grid.at(it) }
        .values.flatMap { Area2d.areas(it) }
        .sumOf { it.area() * it.perimeter() }

    fun partTwo() = grid.dimension().points()
        .groupBy { grid.at(it) }
        .values.flatMap { Area2d.areas(it) }
        .sumOf { it.area() * it.sides().count() }

}