package aock2021

import shared.BinaryNumber
import shared.firstRepeated
import shared.sanitize
import java.util.Comparator.naturalOrder

data class Year2021Day03(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne(): Int {
        val middle = input.size / 2 + 1

        return (0..<input.first().length)
            .map { i -> input.map { it[i] }.firstRepeated(middle)!! }
            .joinToString("")
            .let { BinaryNumber.parse(it) }
            .let { it.intValue * it.inverse().intValue }
    }

    fun partTwo(): Int {
        val oxygenGeneratorRating = BinaryNumber(select(input))
        val coTwoScrubberRating = BinaryNumber(select(input, comparator = reverseOrder()))
        return oxygenGeneratorRating.intValue * coTwoScrubberRating.intValue
    }

    private tailrec fun select(
        lines: List<String>,
        index: Int = 0,
        comparator: Comparator<Int> = naturalOrder()
    ): String {
        if (lines.size == 1) {
            return lines.first()
        } else {
            val groups = lines.groupBy { it[index] }
            val zeros = groups['0']?.size ?: 0
            val ones = groups['1']?.size ?: 0

            val selected = when (comparator.compare(zeros, ones)) {
                1 -> '0'
                -1 -> '1'
                else -> maxOf(0, 1, comparator).let {
                    when (it) {
                        1 -> '1'
                        else -> '0'
                    }
                }
            }

            return select(groups[selected]!!, index + 1, comparator)
        }
    }
}