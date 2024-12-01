package aock2024

import shared.frequencies
import shared.table
import shared.transpose
import kotlin.math.abs

fun parse2024Day01(text: String): Year2024Day01 {
    return Year2024Day01.parse(text)
}

data class Year2024Day01(
    private val firstList: List<Long>,
    private val secondList: List<Long>
) {
    companion object {
        fun parse(text: String): Year2024Day01 {
            val lists = text.table("   ")
                .transpose()
                .map { list -> list.map { it.toLong() }.sorted() }
            return Year2024Day01(lists)
        }
    }

    constructor(lists: List<List<Long>>) : this(lists[0], lists[1])

    fun distanceBetweenLists(): Long {
        return firstList.indices.sumOf { abs(firstList[it] - secondList[it]) }
    }

    fun similarityScore(): Long {
        val frequenciesByNumber: Map<Long, Int> = secondList.frequencies()

        return firstList.indices.sumOf { firstList[it] * (frequenciesByNumber[firstList[it]] ?: 0) }
    }
}