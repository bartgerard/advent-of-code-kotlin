package aock2019

import shared.sanitize
import kotlin.math.ceil

data class Year2019Day14(
    private val reactions: List<Reaction>
) {
    companion object {
        const val ORE = "ORE"
        const val FUEL = "FUEL"
    }

    constructor(input: String) : this(input.sanitize().lines().map { Reaction.parse(it) })

    fun partOne(): Long {
        val remainingReactions = reactions.toMutableList()
        val remaining = mutableMapOf(FUEL to 1L)

        while (remaining.size != 1 || !remaining.contains(ORE)) {
            // the first chemical that is not needed by any other reactions
            val requiredChemical = remaining.keys.first { chemical -> remainingReactions.none { reaction -> reaction.inputs.any { it.chemical == chemical } } }
            val requireQuantity = remaining.remove(requiredChemical)!!

            val reaction = remainingReactions.first { it.output.chemical == requiredChemical }
            remainingReactions -= reaction

            val requiredQuantity = ceil(requireQuantity / reaction.output.quantity.toDouble()).toLong()
            val requirements = reaction.inputs.map { it * requiredQuantity }

            requirements.forEach { (quantity, chemical) ->
                if (remaining.containsKey(chemical)) {
                    remaining[chemical] = remaining[chemical]!! + quantity
                } else {
                    remaining[chemical] = quantity
                }
            }
        }

        return remaining[ORE]!!
    }

    fun partTwo(unitsOfOre: Long): Long {
        return 0L
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

    operator fun plus(operand: Long) = Reactant(quantity + operand, chemical)
    operator fun times(factor: Long) = Reactant(quantity * factor, chemical)
}