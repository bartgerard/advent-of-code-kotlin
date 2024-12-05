package aock2024

import shared.sanitize
import shared.splitByLine
import shared.splitByNewLine
import shared.toIntegers

fun parseYear2024Day05(input: String) = Year2024Day05.parse(input)

data class Year2024Day05(
    private val rules: List<Pair<Int, Int>>,
    private val orders: List<List<Int>>
) {
    companion object {
        fun parse(input: String): Year2024Day05 {
            val (rules, order) = input.sanitize().splitByNewLine()
            val rules2: List<Pair<Int, Int>> = rules.sanitize().splitByLine()
                .map { it.toIntegers() }
                .map { it[0] to it[1] }
            val order2 = order.sanitize().splitByLine()
                .map { it.toIntegers() }

            return Year2024Day05(
                rules2,
                order2
            )
        }
    }

    fun partOne(): Int = sumOfMiddlePages(orders.filter { isCorrect(it) })

    fun partTwo(): Int = sumOfMiddlePages(
        orders.filter { !isCorrect(it) }.map { correctOrdering(it) }
    )

    private fun sumOfMiddlePages(items: List<List<Int>>) = items.sumOf { it[it.size / 2] }

    private fun isCorrect(order: List<Int>): Boolean = order.mapIndexed { index, item -> item to index }
        .toMap()
        .let { orderMap ->
            rules.filter { orderMap.contains(it.first) && orderMap.contains(it.second) }
                .all { orderMap[it.first]!! < orderMap[it.second]!! }
        }

    private fun correctOrdering(incorrect: List<Int>): List<Int> {
        val remainingPages = incorrect.toMutableList()
        val applicableRules = rules.filter { incorrect.contains(it.first) && incorrect.contains(it.second) }
            .groupBy { it.second }
            .mapValues { (_, value) -> value.map { it.first } }

        val result = incorrect.filter { !applicableRules.containsKey(it) }.toMutableList()
        remainingPages.removeAll(result)

        while (remainingPages.isNotEmpty()) {
            for (page in remainingPages) {
                val pagesBefore: List<Int> = applicableRules[page]!!

                if (result.containsAll(pagesBefore)) {
                    result += page
                }
            }
            remainingPages.removeAll(result)
        }

        return result.toList()
    }

}