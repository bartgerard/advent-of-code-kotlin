package aock2020

import shared.sanitize

data class Year2020Day07(
    private val rules: Map<String, List<Pair<Int, String>>>
) {
    companion object {
        val BAG_REGEX = "(?:^|contain |,\\s)(?:(\\d+)\\s+)?((?:[a-z]+\\s)*?[a-z]+)\\sbags?".toRegex()
        const val SHINY_GOLD = "shiny gold"
        const val NO_OTHER = "no other"
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .filterNot { line -> line.contains(NO_OTHER) }
            .associate { line ->
                val rules = BAG_REGEX.findAll(line).map { it.destructured }
                    .map { (count, colour) -> (count.toIntOrNull() ?: 1) to colour }
                    .toList()

                rules.first().second to rules.drop(1)
            }
    )

    fun partOne() = findAllBagsContaining(SHINY_GOLD).size
    fun partTwo() = countContainedBags(SHINY_GOLD) - 1L // subtract the SHINY_GOLD bag itself

    fun findAllBagsContaining(containedBag: String): Set<String> {
        val result = mutableSetOf<String>()
        val next = mutableSetOf(containedBag)

        while (next.isNotEmpty()) {
            val bag = next.first()
            next -= bag
            val origins = findOrigins(bag)
                .filterNot { result.contains(it) }
            result += origins
            next += origins
        }

        return result
    }

    fun findOrigins(containedBag: String): Set<String> = rules.entries
        .filter { (_, content) -> content.any { it.second == containedBag } }
        .map { (bag, _) -> bag }
        .toSet()

    private fun countContainedBags(
        bag: String,
        memory: MutableMap<String, Long> = mutableMapOf()
    ): Long = memory.getOrPut(bag) {
        1L + (rules[bag]
            ?.sumOf { rule ->
                rule.first * countContainedBags(rule.second, memory)
            }
            ?: 0L)
    }
}