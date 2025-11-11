package aock2021

import shared.frequencies
import shared.minMaxOrNull
import shared.sanitize
import shared.splitByEmptyLine

data class Year2021Day14(
    private val polymerTemplate: String,
    private val insertionRules: Map<String, List<String>>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0],
        input[1].lines()
            .associate {
                val (a, b) = it.split(" -> ")
                a to listOf("${a[0]}$b", "$b${a[1]}")
            }
    )

    fun partOne() = polymerization2()
        .drop(10)
        .first()
        .let { scorePolymer2(it) }

    fun partTwo(): Long = polymerization2()
        .drop(40)
        .first()
        .let { scorePolymer2(it) }

    private fun polymerization(): Sequence<String> = generateSequence(polymerTemplate) { polymer ->
        polymer.windowed(2).joinToString("") { pair ->
            insertionRules[pair]?.first() ?: ""
        } + polymer.last()
    }

    private fun scorePolymer(finalPolymer: String): Long = finalPolymer.frequencies()
        .values
        .minMaxOrNull()
        ?.let { (min, max) -> (max - min).toLong() }
        ?: 0L

    private fun polymerization2(): Sequence<Map<String, Long>> {
        val polymerSeed = polymerTemplate.zipWithNext { a, b -> "$a$b" }
            .frequencies()
            .mapValues { it.value.toLong() }

        return generateSequence(polymerSeed) { polymer ->
            polymer.entries
                .flatMap { (pair, count) ->
                    insertionRules[pair]!!.map { newPair -> newPair to count }
                }
                .groupingBy { it.first }
                .fold(0L) { acc, entry -> acc + entry.second }
        }
    }

    private fun scorePolymer2(finalPolymer: Map<String, Long>): Long {
        val frequencies = finalPolymer.entries
            .groupingBy { it.key[0] }
            .fold(0L) { acc, entry -> acc + entry.value }
            .toMutableMap()
            .also { counts ->
                val lastChar = polymerTemplate.last()
                counts[lastChar] = (counts[lastChar] ?: 0L) + 1L
            }
        return frequencies.values.minMaxOrNull()
            ?.let { (min, max) -> max - min }
            ?: 0L
    }
}