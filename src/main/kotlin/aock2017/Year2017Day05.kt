package aock2017

import shared.Predicate
import shared.sanitize
import shared.toIntegers

data class Year2017Day05(
    private val input: List<Int>
) {
    constructor(input: String) : this(input.sanitize().toIntegers())

    fun partOne() = escape { false }

    fun partTwo() = escape { it >= 3 }

    private fun escape(decreaseCondition: Predicate<Int>): Long {
        val maze = input.toMutableList()
        var offset = 0
        var steps = 0L

        while (offset in maze.indices) {
            val jump = maze[offset]
            if (decreaseCondition.invoke(jump)) {
                maze[offset] = jump - 1
            } else {
                maze[offset] = jump + 1
            }
            offset += jump
            steps++
        }

        return steps
    }

}