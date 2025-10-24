package aock2019

import shared.ceilDiv
import shared.sanitize
import kotlin.math.max

data class Year2019Day14(
    private val reactions: List<Reaction>
) {
    companion object {
        const val ORE = "ORE"
        const val FUEL = "FUEL"
    }

    constructor(input: String) : this(input.sanitize().lines().map { Reaction.parse(it) })

    fun partOne(targetAmountOfFuel: Long) = findRequiredAmountOfOreFor(targetAmountOfFuel)

    fun partTwo(unitsOfOre: Long): Long {
        val lowerBound = findRequiredAmountOfOreFor(1L)

        val estimatedAmountOfFuel = unitsOfOre / lowerBound
        val increment = max((estimatedAmountOfFuel - lowerBound) / lowerBound, 1L)

        val upperBound = generateSequence(estimatedAmountOfFuel) { it + increment }
            .dropWhile { findRequiredAmountOfOreFor(it) < unitsOfOre }
            .first()

        return generateSequence(upperBound) { it - 1L }
            .dropWhile { findRequiredAmountOfOreFor(it) > unitsOfOre }
            .first()
    }

    private fun findRequiredAmountOfOreFor(targetAmountOfFuel: Long): Long {
        val unusedReactions = reactions.toMutableList()
        val remaining = mutableMapOf(FUEL to targetAmountOfFuel)

        while (remaining.keys != setOf(ORE)) {
            // the first chemical that is not needed by any other reactions
            val requiredChemical = remaining.keys.first { chemical -> unusedReactions.none { reaction -> reaction.inputs.any { it.chemical == chemical } } }
            val requiredQuantity = remaining.remove(requiredChemical)!!

            val reaction = unusedReactions.first { it.output.chemical == requiredChemical }
                .also { unusedReactions.remove(it) }

            val batches = ceilDiv(requiredQuantity, reaction.output.quantity)

            reaction.inputs.forEach { (quantity, chemical) ->
                remaining[chemical] = remaining.getOrDefault(chemical, 0L) + quantity * batches
            }
        }

        return remaining[ORE] ?: 0L
    }
}

data class Reaction(
    val inputs: List<Reactant>,
    val output: Reactant
) {
    companion object {
        fun parse(input: String): Reaction {
            val (inputs, output) = input.split(" => ")
            return Reaction(
                inputs.split(", ").map { Reactant.parse(it) },
                Reactant.parse(output)
            )
        }
    }
}

data class Reactant(
    val quantity: Long,
    val chemical: String
) {
    companion object {
        fun parse(input: String): Reactant {
            val (quantity, chemical) = input.split(" ")
            return Reactant(quantity.toLong(), chemical)
        }
    }
}