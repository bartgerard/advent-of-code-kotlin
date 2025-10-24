package aock2019

import aock2019.common.LongCodeExecution
import shared.Point2d
import shared.sanitize
import shared.toLongs

data class Year2019Day13(
    private val program: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne(): Int {
        val execution = LongCodeExecution(program.toMutableList()).run()
        return execution.output.chunked(3)
            .associate { Point2d(it[0].toInt(), it[1].toInt()) to it[2] }
            .values
            .count { it == 2L }
    }

    fun partTwo(): Long {
        val freeProgram = program.toMutableList().also { it[0] = 2 }
        val execution = LongCodeExecution(freeProgram)
        return 0L
    }
}