package aock2024

import shared.frequencies
import shared.sanitize
import shared.table
import shared.transpose
import kotlin.math.absoluteValue

data class Year2024Day01(
    private val firstList: List<Long>,
    private val secondList: List<Long>
) {
    companion object {
        private const val SEPARATOR = "   "
    }

    constructor(input: String) : this(
        input.sanitize()
            .table(SEPARATOR)
            .transpose()
            .map { list -> list.map { it.toLong() }.sorted() }
    )

    constructor(lists: List<List<Long>>) : this(lists[0], lists[1])

    fun distanceBetweenLists(): Long = firstList.indices.sumOf { (firstList[it] - secondList[it]).absoluteValue }

    fun similarityScore(): Long = secondList.frequencies()
        .let { frequenciesByNumber ->
            firstList.indices.sumOf {
                firstList[it] * (frequenciesByNumber[firstList[it]] ?: 0)
            }
        }
}