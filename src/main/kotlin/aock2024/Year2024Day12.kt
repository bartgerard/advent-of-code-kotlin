package aock2024

import shared.Area2d
import shared.MutableGrid
import shared.sanitize

data class Year2024Day12(
    private val grid: MutableGrid
) {
    constructor(input: String) : this(MutableGrid(input.sanitize()))

    fun partOne(): Int = grid.points()
        .groupBy { grid.at(it) }
        .values.flatMap { Area2d.areas(it) }
        .sumOf { it.area() * it.perimeter() }

    fun partTwo(): Int = grid.points()
        .groupBy { grid.at(it) }
        .values.flatMap { Area2d.areas(it) }
        .sumOf { it.area() * it.sides().count() }

}