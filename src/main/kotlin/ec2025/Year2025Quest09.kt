package ec2025

import shared.distinctPairs
import shared.product
import shared.sanitize
import shared.zip
import java.util.*

data class Year2025Quest09(
    private val input: Map<Int, List<Char>>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .associate { line ->
                val (id, sequence) = line.split(":")
                id.toInt() to sequence.asSequence().toList()
            }
    )

    fun partOne() = degreeOfSimilarity(1, 3) * degreeOfSimilarity(2, 3)

    fun partTwo() = familyTree().entries
        .sumOf { (child, parents) ->
            parents.map { parent -> degreeOfSimilarity(parent, child) }
                .product()
        }

    fun partThree(): Int {
        val familyTree = familyTree()
        val families: MutableSet<Set<Int>> = mutableSetOf()

        familyTree.entries.forEach { (child, parents) ->
            val group: Set<Int> = buildSet {
                add(child)
                addAll(parents)
            }
            val connected = families.filter { family -> !Collections.disjoint(group, family) }

            families.removeAll(connected.toSet())

            val newGroup = buildSet {
                connected.forEach { addAll(it) }
                addAll(group)
            }

            families.add(newGroup)
        }

        return families.maxBy { it.count() }
            .sum()
    }

    private fun degreeOfSimilarity(
        parentId: Int,
        childId: Int
    ): Int = degreeOfSimilarity(sequenceFor(parentId), sequenceFor(childId))

    private fun degreeOfSimilarity(
        parentSequence: List<Char>,
        childSequence: List<Char>
    ): Int = parentSequence.zip(childSequence).count { (symbol1, symbol2) -> symbol1 == symbol2 }

    private fun sequenceFor(id: Int): List<Char> = input[id]!!

    private fun familyTree(): Map<Int, Set<Int>> = input.entries.distinctPairs()
        .flatMap { (parent1, parent2) ->
            input.entries
                .filter { child -> child.key != parent1.key && child.key != parent2.key }
                .filter { child ->
                    zip(parent1.value, parent2.value, child.value)
                        .all { (sp1, sp2, sc) -> sc == sp1 || sc == sp2 }
                }
                .map { child -> child.key to setOf(parent1.key, parent2.key) }
        }
        .associate { it }
}