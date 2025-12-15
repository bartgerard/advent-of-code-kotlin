package aock2016

import shared.geometry2d.Point2dInt
import shared.grid.CharGrid
import shared.sanitize
import shared.spatial.Direction

data class Year2016Day02(
    private val input: List<List<Direction>>
) {
    companion object {
        val NUMERIC_KEYPAD = CharGrid("123\n456\n789")
        val SPECIAL_KEYPAD = CharGrid("  1  \n 234 \n56789\n ABC \n  D  ")
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .map { line -> line.map { Direction.parse(it) } })

    fun partOne() = traverseKeyPad(
        NUMERIC_KEYPAD.findAll('5').first(),
        NUMERIC_KEYPAD
    )

    fun partTwo() = traverseKeyPad(
        SPECIAL_KEYPAD.findAll('5').first(),
        SPECIAL_KEYPAD
    )

    private fun traverseKeyPad(
        start: Point2dInt,
        keypad: CharGrid
    ): String = input.scan(start) { point, directions ->
        directions.fold(point) { oldPoint, direction ->
            val newPoint = oldPoint + direction
            if (keypad.contains(newPoint) && keypad.at(newPoint) != ' ') {
                newPoint
            } else {
                oldPoint
            }
        }
    }
        .drop(1)
        .joinToString("") { keypad.at(it).toString() }
}