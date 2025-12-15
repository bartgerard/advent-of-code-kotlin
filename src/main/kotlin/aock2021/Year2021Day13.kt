package aock2021

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.grid.CharGrid
import shared.sanitize
import shared.spatial.Axis
import shared.splitByEmptyLine
import shared.toIntegers

data class Year2021Day13(
    private val dots: List<Point2dInt>,
    private val instructions: List<Instruction>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].lines().map { Point2dInt(it.toIntegers()) },
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
        val dimension = Dimension2d(
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

private fun Collection<Point2dInt>.foldAlong(instruction: Instruction): Set<Point2dInt> {
    val newSet = mutableSetOf<Point2dInt>()
    for (point in this) {
        val coordinate = when (instruction.axis) {
            Axis.X -> point.x
            else -> point.y
        }
        if (coordinate > instruction.line) {
            val newPoint = when (instruction.axis) {
                Axis.X -> Point2dInt(2 * instruction.line - point.x, point.y)
                else -> Point2dInt(point.x, 2 * instruction.line - point.y)
            }
            newSet.add(newPoint)
        } else {
            newSet.add(point)
        }
    }
    return newSet
}