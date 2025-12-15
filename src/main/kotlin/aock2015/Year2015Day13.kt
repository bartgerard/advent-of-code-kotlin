package aock2015

import shared.permutations
import shared.sanitize
import shared.zipWithNextCyclical

data class Year2015Day13(
    private val names: Set<String>,
    private val rules: List<HappinessRule>
) {
    companion object {
        val regex =
            "(?<name1>[^ ]+) would (?<sign>gain|lose) (?<happiness>\\d+) happiness units by sitting next to (?<name2>[^ ]+).".toRegex()
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .map {
                val (name1, signIndication, happiness, name2) = regex.matchEntire(it)!!.destructured
                val sign = when (signIndication) {
                    "gain" -> 1
                    else -> -1
                }
                HappinessRule(name1, name2, happiness.toInt() * sign)
            }
    )

    constructor(rules: List<HappinessRule>) : this(
        (rules.map { it.name1 } + rules.map { it.name2 }).toSet(),
        rules
    )

    fun partOne() = names.permutations().maxOf { totalHappiness(it) }

    fun partTwo() = (names + "me").permutations().maxOf { totalHappiness(it) }

    private fun totalHappiness(seatingOrder: List<String>) = seatingOrder.zipWithNextCyclical()
        .sumOf { totalHappinessFor(it) }

    private fun totalHappinessFor(pair: Pair<String, String>) = rules
        .filter { it.isApplicable(pair) }
        .sumOf { it.happiness }
}

data class HappinessRule(
    val name1: String,
    val name2: String,
    val happiness: Int
) {
    fun isApplicable(pair: Pair<String, String>) =
        (name1 == pair.first && name2 == pair.second) || (name2 == pair.first && name1 == pair.second)
}