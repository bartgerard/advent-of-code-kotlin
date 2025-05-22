package aock2019

import aock2019.common.LongCodeExecution
import shared.sanitize
import shared.toLongs

data class Year2019Day09(
    private val program: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne(input: List<Long>) = LongCodeExecution(program, input).run()
    fun partTwo(input: List<Long>) = LongCodeExecution(program, input).run()
}