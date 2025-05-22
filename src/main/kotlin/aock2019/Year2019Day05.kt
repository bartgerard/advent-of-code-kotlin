package aock2019

import aock2019.common.IntCodeExecution
import shared.sanitize
import shared.toIntegers

data class Year2019Day05(
    private val program: List<Int>
) {
    constructor(input: String) : this(input.sanitize().toIntegers())

    fun partOne(input: List<Int>) = IntCodeExecution(program, input).run()
    fun partTwo(input: List<Int>) = IntCodeExecution(program, input).run()
}


