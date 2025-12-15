package aock2019

import aock2019.common.LongCodeExecution
import shared.permutations
import shared.sanitize
import shared.toLongs
import java.util.concurrent.CompletableFuture
import java.util.concurrent.LinkedBlockingQueue

data class Year2019Day07(
    private val program: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = (0..4).toSet().permutations().map { permutation ->
        permutation.fold(0L) { previous, phaseSetting -> amplify(phaseSetting, previous) }
    }
        .max()

    fun partTwo() = (5L..9L).toSet().permutations().map { permutation ->
        runPermutation(permutation, 0L)
    }
        .max()

    fun amplify(permutation: Int, input: Long): Long =
        LongCodeExecution(program, listOf(permutation.toLong(), input)).run().output.first()

    fun runPermutation(permutation: List<Long>, input: Long): Long {
        val queues = (0..4).map { i -> LinkedBlockingQueue(listOf(permutation[i])) }
        queues[0].add(input)

        val programs = buildList {
            addAll(queues.zipWithNext { q1, q2 -> LongCodeExecution(program.toMutableList(), q1, q2) })
            add(LongCodeExecution(program.toMutableList(), queues[4], queues[0]))
        }

        val futures = programs.map { program -> CompletableFuture.runAsync { program.run() } }
        CompletableFuture.allOf(*futures.toTypedArray()).get()

        return programs.last().output.first()
    }
}