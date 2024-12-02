package aock2024

import shared.frequencies
import shared.table
import shared.transpose
import kotlin.math.abs

fun parse2024Day01(input: String): Year2024Day01 {
    return Year2024Day01.parse(input)
}

data class Year2024Day01(
    private val firstList: List<Long>,
    private val secondList: List<Long>
) {
    companion object {
        private const val SEPARATOR = "   "
        fun parse(input: String): Year2024Day01 {
            val lists = input.table(SEPARATOR)
                .transpose()
                .map { list -> list.map { it.toLong() }.sorted() }
            return Year2024Day01(lists)
        }
    }

    constructor(lists: List<List<Long>>) : this(lists[0], lists[1])

    fun distanceBetweenLists(): Long = firstList.indices.sumOf { abs(firstList[it] - secondList[it]) }

    fun similarityScore(): Long {
        val frequenciesByNumber: Map<Long, Int> = secondList.frequencies()

        return firstList.indices.sumOf { firstList[it] * (frequenciesByNumber[firstList[it]] ?: 0) }
    }
}