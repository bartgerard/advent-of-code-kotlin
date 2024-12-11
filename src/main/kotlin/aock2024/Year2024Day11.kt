package aock2024

import shared.sanitize
import shared.toLongs

data class Year2024Day11(
    private val stones: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs().toMutableList())

    fun partOne(blinks: Int): Long = stones.sumOf { sizeAfter(it, blinks) }

    fun partTwo(): Long {
        return 0
    }

    private fun sizeAfter(value: Long, blinks: Int): Long {
        val previousStones = mutableListOf<Long>(value)
        val nextStones = mutableListOf<Long>()

        for (i in 1..blinks) {
            for (stone in previousStones) {
                val text = stone.toString()

                when {
                    stone == 0L -> nextStones.add(1)
                    text.length % 2 == 0 -> stone.let {
                        val base = (1..(text.length / 2)).fold(1L) { acc, i -> acc * 10 }

                        nextStones.add(it / base)
                        nextStones.add(it % base)
                    }

                    else -> nextStones.add(stone * 2024)
                }
            }

            previousStones.clear()
            previousStones.addAll(nextStones)
            nextStones.clear()
        }

        return previousStones.size.toLong()
    }

}