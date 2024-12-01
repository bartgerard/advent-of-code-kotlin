package aock2024

import shared.asLongs
import shared.byLine
import kotlin.math.abs

data class Year2024Day01(
    private val pairs: List<Pair<Long, Long>>
) {
    constructor(text: String) : this(
        text.byLine()
            .map { it.asLongs() }
            .map { Pair(it[0], it[1]) }
    )

    fun distanceBetweenLists(): Long {
        val firstList = pairs.map { it.first }.sorted()
        val secondList = pairs.map { it.second }.sorted()

        return firstList.indices.sumOf { abs(firstList[it] - secondList[it]) }
    }

    fun similarityScore(): Long {
        val firstList = pairs.map { it.first }.sorted()
        val frequenciesByNumber: Map<Long, Int> = pairs.map { it.second }
            .groupBy { it }
            .mapValues { it.value.size }

        return firstList.indices.sumOf { firstList[it] * (frequenciesByNumber[firstList[it]] ?: 0) }
    }
}