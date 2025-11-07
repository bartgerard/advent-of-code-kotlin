package aock2021

import shared.Axis
import shared.CharGrid
import shared.Dimension
import shared.Point2d
import shared.sanitize
import shared.splitByEmptyLine
import shared.toIntegers

data class Year2021Day13(
    private val dots: List<Point2d>,
    private val instructions: List<Instruction>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].lines().map { Point2d(it.toIntegers()) },
        input[1].lines()
            .map { line ->
                line.removePrefix("fold along ")
                    .split("=")
                    .let { Instruction(Axis.parse(it[0][0]), it[1].toInt()) }
            }
    )

    fun partOne() = dots.foldAlong(instructions.first()).size

    fun partTwo(): Long {
        val remainingDots = instructions.fold(dots.toSet()) { dots, instruction -> dots.foldAlong(instruction) }
        val dimension = Dimension(
            remainingDots.maxOf { it.x } + 1,
            remainingDots.maxOf { it.y } + 1
        )
        val grid = CharGrid(dimension, '.')
            .also { grid -> remainingDots.forEach { grid.set(it, '#') } }
        println(grid)
        return 0L
    }

}

data class Instruction(
    val axis: Axis,
    val line: Int
)

private fun Collection<Point2d>.foldAlong(instruction: Instruction): Set<Point2d> {
    val newSet = mutableSetOf<Point2d>()
    for (point in this) {
        val coordinate = when (instruction.axis) {
            Axis.X -> point.x
            else -> point.y
        }
        if (coordinate > instruction.line) {
            val newPoint = when (instruction.axis) {
                Axis.X -> Point2d(2 * instruction.line - point.x, point.y)
                else -> Point2d(point.x, 2 * instruction.line - point.y)
            }
            newSet.add(newPoint)
        } else {
            newSet.add(point)
        }
    }
    return newSet
}