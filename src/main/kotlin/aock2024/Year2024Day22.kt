package aock2024

import shared.sanitize
import shared.toLongs

data class Year2024Day22(
    private val initialSecretNumbers: List<Long>
) {
    companion object {
        fun mix(value: Long, secretNumber: Long) = value.xor(secretNumber)
        fun prune(value: Long) = value.mod(16777216L)

        fun generate(secretNumber: Long): Long {
            val step1 = prune(mix(secretNumber * 64L, secretNumber))
            val step2 = prune(mix(step1 / 32, step1))
            return prune(mix(step2 * 2048, step2))
        }

        fun generate(initialSecretNumber: Long, times: Int) = generateSequence(initialSecretNumber, ::generate)
            .take(times)

        fun generateSecrets(initialSecretNumber: Long, n: Int) = buildList {
            add(initialSecretNumber)
            repeat(n) {
                add(generate(last()))
            }
        }

        fun price(value: Long) = value % 10

        fun toPrices(values: List<Long>) = values.map { price(it) }
        fun toPriceChanges(values: List<Long>) = values.zipWithNext { a, b -> b - a }
    }

    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = initialSecretNumbers.sumOf { generate(it, 2001).last() }

    fun partTwo(): Long = initialSecretNumbers.flatMap { bananasBySequence(it) }
        .groupingBy { (sequence, _) -> sequence }
        .fold(0L) { acc, (_, bananas) -> acc + bananas }
        .maxBy { (_, bananas) -> bananas }
        .also { (sequence, bananas) -> println("sequence: $sequence, bananas: $bananas") }
        .value

    fun bananasBySequence(initialSecretNumber: Long): List<Pair<List<Long>, Long>> {
        val secrets = generateSecrets(initialSecretNumber, 2000)
        val prices = toPrices(secrets)
        val priceChanges = toPriceChanges(prices)

        return priceChanges.windowed(4)
            .mapIndexed { i, sequence -> sequence to prices[i + 4] }
            .distinctBy { (sequence, _) -> sequence } // only first sequence is used
    }

}

fun main() {
    println("mix -> ${42.xor(15)} should return 37")
    println("prune -> ${100000000.mod(16777216)} should return 16113920")
}