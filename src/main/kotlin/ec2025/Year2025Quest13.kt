package ec2025

import shared.length
import shared.sanitize
import shared.toLongs

data class Year2025Quest13(
    private val input: List<LongRange>
) {
    companion object {
        fun parseRange(value: String): LongRange = value.replace("-", "_")
            .toLongs()
            .let { longs ->
                when (longs.size) {
                    0 -> LongRange.EMPTY
                    1 -> longs[0]..longs[0]
                    else -> {
                        val (from, to) = longs
                        from..to
                    }
                }
            }
    }

    constructor(input: String) : this(input.sanitize().lines().map { parseRange(it) })

    fun partOne() = rotate(2025L)
    fun partTwo() = rotate(20252025L)
    fun partThree() = rotate(202520252025L)

    private fun rotate(position: Long): Long {
        val dial: List<LongProgression> = buildList {
            add(1L..1L)
            addAll(input.filterIndexed { index, _ -> index % 2 == 0 })
            addAll(input.filterIndexed { index, _ -> index % 2 != 0 }.map { it.last downTo it.first step 1 }.reversed())
        }

        val runningTotals = dial.scan(0L) { acc, range -> acc + range.length() }.drop(1)

        val total = runningTotals.last()
        val index = position % total

        val rangeIndex = runningTotals.indexOfFirst { it > index }
        val previousTotal = if (rangeIndex == 0) 0L else runningTotals[rangeIndex - 1]
        val offsetInRange = index - previousTotal
        val targetRange = dial[rangeIndex]

        return targetRange.drop(offsetInRange.toInt()).first()
    }
}

fun main() {
    println((1..4 step 2).toList())
    println((1 until 4 step 1).toList())
    println((4 downTo 1 step 2).toList())
}