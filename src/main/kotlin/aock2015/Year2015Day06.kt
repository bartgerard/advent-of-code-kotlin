package aock2015

import shared.*
import kotlin.math.max

data class Year2015Day06(
    private val instructions: List<String>
) {
    companion object {
        val DIMENSION = Dimension(1000, 1000)
    }

    constructor(input: String) : this(input.byLine())

    fun partOne(): Int {
        val grid = ToggleGrid(DIMENSION)
        instructions.forEach { execute(grid, it) }
        return grid.count()
    }

    fun partTwo(): Int {
        val grid = IntensityGrid(DIMENSION)
        instructions.forEach { execute(grid, it) }
        return grid.intensity()
    }

    private fun rectangle(instruction: String): Rectangle {
        val coordinates = instruction.asIntegers()
        val rectangle = Rectangle(coordinates[0]..coordinates[2], coordinates[1]..coordinates[3])
        return rectangle
    }

    private fun execute(grid: ToggleGrid, instruction: String) {
        val rectangle = rectangle(instruction)

        if (instruction.startsWith("turn on")) {
            grid.execute(rectangle) { true }
        } else if (instruction.startsWith("turn off")) {
            grid.execute(rectangle) { false }
        } else if (instruction.startsWith("toggle")) {
            grid.execute(rectangle, ::toggle)
        }
    }

    private fun toggle(previous: Boolean): Boolean {
        return !previous
    }

    private fun execute(grid: IntensityGrid, instruction: String) {
        val rectangle = rectangle(instruction)

        if (instruction.startsWith("turn on")) {
            grid.execute(rectangle) { increase(it, 1) }
        } else if (instruction.startsWith("turn off")) {
            grid.execute(rectangle, ::decrease)
        } else if (instruction.startsWith("toggle")) {
            grid.execute(rectangle) { increase(it, 2) }
        }
    }

    private fun increase(previous: Int, amount: Int): Int {
        return previous + amount
    }

    private fun decrease(previous: Int): Int {
        return max(0, previous - 1)
    }
}