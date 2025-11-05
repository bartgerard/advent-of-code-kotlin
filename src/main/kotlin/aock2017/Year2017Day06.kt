package aock2017

import shared.sanitize
import shared.toIntegers

data class Year2017Day06(
    private val input: List<Int>
) {
    constructor(input: String) : this(input.sanitize().toIntegers())

    fun partOne() = cycles().history.size

    fun partTwo() = cycles().cycleLength()

    private fun cycles(): Cycles {
        val memoryBanks = input.toMutableList()
        val seenConfigurations = mutableListOf<List<Int>>()

        while (true) {
            val currentConfig = memoryBanks.toList()
            if (currentConfig in seenConfigurations) {
                break
            }
            seenConfigurations += currentConfig
            memoryBanks.redistribute()
        }

        return Cycles(seenConfigurations, memoryBanks.toList())
    }

    private fun MutableList<Int>.redistribute() {
        val maxIndex = indices.maxBy { this[it] }
        val blocksToDistribute = this[maxIndex]
        this[maxIndex] = 0

        repeat(blocksToDistribute) { i ->
            this[(maxIndex + 1 + i) % size]++
        }
    }
}

data class Cycles(
    val history: List<List<Int>>,
    val repeatedConfiguration: List<Int>
) {
    fun cycleLength(): Int {
        val firstOccurrence = history.indexOf(repeatedConfiguration)
        return history.size - firstOccurrence
    }
}