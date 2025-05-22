package aock2019

import aock2019.common.LongCodeExecution
import shared.permutations
import shared.sanitize
import shared.toLongs

data class Year2019Day07(
    private val program: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = (0..4).toSet().permutations().map { permutation ->
        permutation.fold(0L) { previous, phaseSetting -> amplify(phaseSetting, previous) }
    }
        .max()

    fun partTwo() = (5..9).toSet().permutations().map { permutation ->
        permutation.fold(0L) { previous, phaseSetting -> amplify(phaseSetting, previous) }
    }
        .max()

    fun amplify(permutation: Int, input: Long) = LongCodeExecution(program, listOf(permutation.toLong(), input)).run().output.first()
}