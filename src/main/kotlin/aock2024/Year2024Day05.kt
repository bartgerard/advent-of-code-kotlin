package aock2024

import shared.byLine
import shared.toIntegers

fun parseYear2024Day05(input: String) = Year2024Day05.parse(input)

data class Year2024Day05(
    private val rules: List<Pair<Int, Int>>,
    private val orders: List<List<Int>>
) {
    companion object {
        fun parse(input: String): Year2024Day05 {
            val (rules, order) = input.replace("\r", "").split("\n\n")
            val rules2: List<Pair<Int, Int>> = rules.byLine()
                .map { it.toIntegers() }
                .map { it[0] to it[1] }
            val order2 = order.byLine()
                .map { it.toIntegers() }

            return Year2024Day05(
                rules2,
                order2
            )
        }
    }

    fun partOne(): Int {
        val correct = orders.filter { isCorrect(it) }
        return sumOfMiddlePages(correct)
    }

    private fun sumOfMiddlePages(items: List<List<Int>>) = items.sumOf { it[it.size / 2] }

    private fun isCorrect(order: List<Int>): Boolean {
        val orderMap: Map<Int, Int> = order.mapIndexed { index, item -> item to index }
            .toMap()
        return rules
            .filter {
                orderMap.contains(it.first)
                        && orderMap.contains(it.second)
            }
            .all {
                (orderMap[it.first] ?: 0) < (orderMap[it.second] ?: 0)
            }
    }

    fun partTwo(): Int {
        val incorrect = orders.filter { !isCorrect(it) }
        val corrected = incorrect.map { correct(it) }
        return sumOfMiddlePages(corrected)
    }

    private fun correct(incorrect: List<Int>): List<Int> {
        val remaining = incorrect.toMutableList()
        val applicableRules = rules.filter { incorrect.contains(it.first) && incorrect.contains(it.second) }
            .groupBy { it.second }
            .mapValues { (_, value) -> value.map { it.first } }

        val result = incorrect.filter { !applicableRules.containsKey(it) }
            .toMutableList()

        remaining.removeAll(result)

        while (remaining.isNotEmpty()) {
            for (r in remaining) {
                val befores: List<Int> = applicableRules[r]!!

                if (result.containsAll(befores)) {
                    result += r
                }
            }
            remaining.removeAll(result)
        }


        return result
    }

    /*

        private fun correct(incorrect: List<Int>): List<Int> {
            return generatePermutations(incorrect).first { isCorrect(it) }
        }

        private fun <T> generatePermutations(list: List<T>): List<List<T>> {
            if (list.isEmpty()) return listOf(emptyList())

            val result = mutableListOf<List<T>>()

            for (i in list.indices) {
                // Pick the element at index i and get the remaining elements
                val remaining = list.slice(0 until i) + list.slice(i + 1 until list.size)

                // Recursively get the permutations of the remaining elements
                val subPermutations = generatePermutations(remaining)

                // Add the current element to each permutation of the remaining elements
                for (perm in subPermutations) {
                    result.add(listOf(list[i]) + perm)
                }
            }

            return result
        }

     */

}