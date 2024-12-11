package aock2024

import shared.sanitize
import shared.toLongs
import kotlin.math.log10
import kotlin.math.pow

data class Year2024Day11(
    private val stones: List<Long>
) {
    companion object {
        val MEMORY = mutableMapOf<Pair<Long, Int>, Long>()

        // memoization
        fun expand(stone: Long, blinks: Int): Long {
            if (blinks == 0) {
                return 1L
            }

            val key = stone to blinks
            if (MEMORY.containsKey(key)) {
                return MEMORY[key]!!
            }

            val count = blink(stone).sumOf { expand(it, blinks - 1) }

            MEMORY[key] = count
            return count
        }

        fun blink(stone: Long): List<Long> {
            val length = log10(stone.toDouble()).toInt() + 1

            return when {
                stone == 0L -> listOf(1L)
                length % 2 == 0 -> stone.let {
                    val base = 10.0.pow(length / 2).toLong()

                    listOf(it / base, it % base)
                }

                else -> listOf(stone * 2024)
            }
        }
    }

    constructor(input: String) : this(input.sanitize().toLongs())

    fun stonesAfter(blinks: Int): Long = stones.sumOf { expand(it, blinks) }

}